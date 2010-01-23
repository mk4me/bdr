package motion.c3dFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;

public class ParameterTable extends AbstractBlockContent {
	
	byte buffer[];
	byte processorType;
	HashMap<Byte, Group> groups = new HashMap<Byte, Group>();
	
	public ParameterTable(byte buffer[], byte processorType ) throws Exception
	{
		this.buffer = buffer;
		this.processorType = processorType;
		
		// for Intel and Dec we use LittleEndian 
		// for Mips we use BigEndian
		if (processorType == 3)
			DataType.isBigEndian = true;
		else
			DataType.isBigEndian = false;

		int pos = 0;
		boolean finished = false;
		
		try {
			while (!finished) {
				byte id = buffer[pos + 1];
				if (id < 0)
					pos = readGroup(pos);
				else if (id > 0)
					pos = readParameter(pos);
				else
					finished = true;
				if (pos > buffer.length)
					finished = true;
			}
		} 
		catch (ParametersEndException e) {
			//end of parameters section. quit normally
		}
	}

	public String toString()
	{
		StringBuffer output = new StringBuffer();
	
		for ( Group g : groups.values() )
		{
			output.append("\n");
			output.append( g.toString() );
			output.append( "\n" );
			for ( Parameter p : g.parameters.values() )
				output.append("=>").append( p ).append("\n");
		}
		return output.toString();
	}

	/**
	 * This class keeps information about a group of parameters.
	 * @author kk
	 *
	 */
	class Group
	{
		String name;
		String description;
		byte id;
		/**
		 * Name of the parameter is a key in the hash map.
		 */
		HashMap<String, Parameter> parameters;
		
		public Group(String name, byte id, String description)
		{
			this.name = name;
			this.id = id;
			this.description = description;
			parameters = new HashMap<String, Parameter>();
		}
		
		public void addParameter(Parameter param) throws Exception
		{
			if ( parameters.put(param.name, param) != null )
				throw new Exception("Parameter '" + param.name + "' already exists in group '" + name + "'.");
		}
		
		public String toString()
		{
			StringBuffer output = new StringBuffer();
			output.append("[").append(this.id).append(":").append( this.name ).append(":").append( this.description ).append("]");
			return output.toString();
		}
	}
	
	class Parameter
	{
		String name;
		
		/**
		 * Dimensions of the data array.
		 */
		int[] dimSize;
		
		/** 
		 * Array with parameter data. This should be interpreted together with dimSize.
		 */
		Object data;
		
		/** 
		 * Number of elements in data array.
		 */
		int dataSize;
		
		/**
		 * Type of parameter data. 
		 */
		DataType dataType;
		
		public Parameter( String name, Object data, int[] dimSize, int dataSize, DataType dataType )
		{
			this.name = name;
			this.data = data;
			this.dimSize = dimSize;
			this.dataSize = dataSize;
			this.dataType = dataType;
		}
		
		public String toString()
		{
			StringBuffer output = new StringBuffer();
			Class<?> reflect = data.getClass();
			if (reflect.getComponentType() != null)
				for ( int i=0; i<this.dataSize; i++ )
				{
					Object value = Array.get( data, i ); 
					output.append( value ).append("  ");
				}
			else
				output.append( data );
			return name + " (" + dimSize.length +") " + "[" + dataType.toString() + "]" + " = " + output.toString();
		}
	}

	/*
	 * ************************************************************************
	 */
	
	private int readParameter(int pos) throws Exception {
		
		// to read nameLen properly if parameter was locked we use abs()
		int nameLen = Math.abs( buffer[pos] );
		char[] nameBuffer = new char[ nameLen ];
		
		byte groupId = (byte) Math.abs( buffer[pos + 1] );
		
		Object data;
		
		copyByteToCharArray( buffer, nameBuffer, pos+2, nameLen );

		String paramName = new String(nameBuffer);

		int dimNo = buffer[pos+nameLen+5];
		int dimSize[];
		int dataSize = 1;

		DataType dataType = DataType.getTypeEnum( buffer[pos+nameLen+4] );

		if (dimNo>0)
		{
			dimSize = new int[dimNo];
			for (int i=0; i<dimSize.length; i++)
			{
				dimSize[i] = (int)buffer[pos+nameLen+6+i];
				if (dimSize[i] < 0)
					dimSize[i] += 256;
				dataSize *= dimSize[i];
			}
			data = dataType.readMatrix( buffer, pos+nameLen+6+dimNo, dataSize );
		}
		else
		{
			dimSize = new int[0];
			data = dataType.readScalar( buffer, pos+nameLen+6+dimNo+1 );
		}

		if ( groups.get( groupId ) == null )
			groups.put( groupId, new Group( "", groupId, "" ) );
		groups.get( groupId ).addParameter( new Parameter( paramName, data, dimSize, dataSize, dataType ) ); 

		int offset = (Integer)DataType.INTEGER.readScalar(buffer, pos+nameLen+2);
		if (offset == 0)
			throw new ParametersEndException();
		
		return pos + dimNo + nameLen + dataSize*dataType.dataLen + 7;	
	}


	private int readGroup(int pos) throws IOException, ParametersEndException {
		int nameLen = buffer[pos];
		int descrLen = buffer[pos + nameLen + 2 + 2];
		char[] nameBuffer = new char[ nameLen ];
		char[] descrBuffer = new char[ descrLen ];
		
		byte groupId = (byte) Math.abs( buffer[pos + 1] );
		
		copyByteToCharArray( buffer, nameBuffer, pos+2, nameLen );
		
		if ( descrLen > 0 )
			copyByteToCharArray( buffer, descrBuffer, pos+2+2+nameLen, descrLen );
		
		String groupName = new String(nameBuffer);
		String groupDescription = new String(descrBuffer);
	
		Group group;
		if ( (group = groups.get( groupId )) != null )
		{
			group.name = groupName;
			group.description = groupDescription; 
		}
		else
			groups.put( (byte)Math.abs( groupId ), new Group( groupName, groupId, groupDescription ) );
		
		int offset = (Integer)DataType.INTEGER.readScalar(buffer, pos+nameLen+2);

		if (offset == 0)
			throw new ParametersEndException();
		
		return pos + offset + nameLen + descrLen + 2;	
	}


	private void copyByteToCharArray(byte[] srcBuffer, char[] dstBuffer, int off, int nameLen) 
	{
		for( int i = 0; i<nameLen; i++)
			dstBuffer[i] = (char) srcBuffer[i+off];
	}


	enum DataType{
		CHAR (1){
			@Override
			public Object readScalar(byte[] buffer, int pos) {
				return (char)buffer[pos];
			}

			@Override
			public Class<?> getType() {
				return Character.TYPE;
			}
		}, BYTE (1) {
			@Override
			public Object readScalar(byte[] buffer, int pos) {
				return (byte)buffer[pos];
			}
			@Override
			public Class<?> getType() {
				return Byte.TYPE;
			}
		}, INTEGER (2) {
			@Override
			public Object readScalar(byte[] buffer, int pos) {
				int b1 = buffer[pos];
				int b2 = buffer[pos + 1];
				int result; 
				if (isBigEndian)
					result = (int)(b2 & 0xff | (b1<<8)); 
				else
					result = (int)(b1 & 0xff | (b2<<8)); 
				return result;
			}
			@Override
			public Class<?> getType() {
				return Integer.TYPE;
			}
		}, FLOAT (4){
			@Override
			public Object readScalar(byte[] buffer, int pos) 
			{
				int accum = 0;
				int i = pos-1;
				for ( int shiftBy=0; shiftBy<32; shiftBy+=8 )
					accum |= ( buffer[i++] & 0xff ) << shiftBy;
				if (isBigEndian)
					accum = Integer.reverseBytes( accum );
				return Float.intBitsToFloat( accum );
			}
			@Override
			public Class<?> getType() {
				return Float.TYPE;
			}
		};
		
		public static DataType getTypeEnum(byte b) 
		{
			switch(b)
			{
				case -1:return CHAR; 
				case 1:return BYTE;
				case 2:return INTEGER;
				case 4:return FLOAT;
			}
			return BYTE;
		}

		public Object readMatrix(byte[] buffer, int pos, int dataSize)
		{
			Object array = Array.newInstance( this.getType(), dataSize);
			for (int j=0; j<dataSize; j++)
			{	
				Object scalar = this.readScalar(buffer, pos + j*this.dataLen);
				Array.set(array, j, scalar);
			}
			return array;
		}

		public abstract Class<?> getType();

		public abstract Object readScalar(byte buffer[], int pos); 
		
		DataType(int dataLen)
		{
			this.dataLen = dataLen;
		}
		
		public int dataLen;
		static public boolean isBigEndian = false;
	}
}