package motion.c3dFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public interface Block
{

	public void read(InputStream input) throws IOException;

}

class ByteBlock implements Block{
	
	public final int BLOCK_LEN = 512;
	
	byte[] buffer; 
	int len = BLOCK_LEN;
	
	AbstractBlockContent content; 
	
	public void read(InputStream input) throws IOException
	{
		buffer = new byte[len]; 
		input.read( this.buffer, 0, buffer.length );
		this.content = createContent();
	}

	protected AbstractBlockContent createContent() {
		
		return new HeaderBlockContent();
	}
	
	
	public String toString()
	{
		StringWriter output =  new StringWriter();
		PrintWriter sw = new PrintWriter( output );
		for( byte b : buffer)
			sw.printf("%02x|", b);
		sw.println();
		return output.toString();

/*		StringBuffer ret = new StringBuffer();
		for( byte b : buffer)
			ret.append( Integer.toHexString(b) ).append( '|' ); 
		return ret.toString();
*/
	}
	
	public String getContentAsString()
	{
		if (content != null)
			return content.toString();
		return "no content";
	}
	

}
