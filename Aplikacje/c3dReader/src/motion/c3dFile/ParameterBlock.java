package motion.c3dFile;
import java.io.IOException;
import java.io.InputStream;


public class ParameterBlock extends ByteBlock {

	public byte processorType;
	
	@Override
	public void read( InputStream input ) throws IOException {
	
		input.skip(2);
		byte blockCount = (byte)(input.read());
		processorType = (byte)input.read();
		
		len = BLOCK_LEN * blockCount - 4;
		super.read(input);
	}

	@Override
	public AbstractBlockContent createContent()
	{
		try {
			if (content == null)
				content = new ParameterTable( buffer, processorType );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	
	
}
