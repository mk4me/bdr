package motion.c3dFile;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;


public class WordBlock implements Block {
		
		int[] buffer = new int[256];
		
		AbstractBlockContent content; 
		
		public void read(InputStream input) throws IOException
		{
			DataInputStream in = new DataInputStream(input); 
			for (int b=0; b<256; b++)
				buffer[b] = readWord(in);
			this.content = createContent();
		}
		
		public int readWord(InputStream input) throws IOException
		{
			int b1 = input.read();
			int b2 = input.read();
			
			int r = (b1<<8) + (b2 & 255);
			
			return (int)r;
			
		}

		private AbstractBlockContent createContent() {
			
			return new HeaderBlockContent();
		}
		
		
		public String toString()
		{
			StringWriter output =  new StringWriter();
			PrintWriter sw = new PrintWriter( output );
			/*for( int b : buffer)
				sw.printf("%d|%d||", b>>8, b &255);
			sw.println();
			*/for( int b : buffer)
				sw.printf("%02x|%02x||", b>>8, b &255);
			sw.println();
			/*for( int b : buffer)
				sw.printf("%d||", b);
			*/
			return output.toString();
		}
	}

