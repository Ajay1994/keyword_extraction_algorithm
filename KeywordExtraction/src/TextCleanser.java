import java.io.*;

public class TextCleanser {
	
	public static String clean_text(BufferedReader br) throws IOException {
		String st; 
		String text = "";
		
		while ((st = br.readLine()) != null)
		   text += st;
		
		
		//Replace Apostphi
	    text = text.replaceAll("'[a-z]*|’[a-z]", "");
		
		// strips off all non-ASCII characters
	    text = text.replaceAll("[^\\x00-\\x7F]", "");
	 
	    // erases all the ASCII control characters
	    text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
	     
	    // removes non-printable characters from Unicode
	    text = text.replaceAll("\\p{C}", "");
	    
	    
	    //Replace new line character
	    text = text.replace("\\n", "");
	    
	    text = text.replaceAll("[^\\p{Alpha}\\p{Digit}]\\%+"," ");
	    
	    text = text.replace(".", ". ");
	    
	    //Replace extra Whitespaces
	    text = text.replaceAll("\\s{2,}"," ");
	    
	    //text = text.replaceAll("\\!", "");
	    
		text = text.trim();
		
		String new_file = "clean_sample.txt";
		FileWriter myWriter = new FileWriter(new_file);
	    myWriter.write(text);
	    myWriter.close();
		
		return new_file;
	}
}
