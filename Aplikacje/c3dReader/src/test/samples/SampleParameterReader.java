package test.samples;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import motion.c3dFile.Block;
import motion.c3dFile.ParameterBlock;
import motion.c3dFile.WordBlock;



public class SampleParameterReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			FileInputStream input = new FileInputStream( "data/Combo_1.c3d" );
			
			Block header = new WordBlock();
			header.read(input);
			
			ParameterBlock parameterBlock = new ParameterBlock();
			parameterBlock.read(input);
			
			System.out.println( header );
			System.out.println( parameterBlock );
			
			System.out.println( parameterBlock.getContentAsString() );
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}

}
