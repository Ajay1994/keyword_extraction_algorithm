import java.io.*;

public class TextCleanser {
	
	public static String clean_text(BufferedReader br) throws IOException {
		String st; 
		String text = "";
		
		while ((st = br.readLine()) != null)
		   text += st;
		// strips off all non-ASCII characters
	    text = text.replaceAll("[^\\x00-\\x7F]", "");
	 
	    // erases all the ASCII control characters
	    text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
	     
	    // removes non-printable characters from Unicode
	    text = text.replaceAll("\\p{C}", "");
	    
	    //Replace Apostphi
	    text = text.replace("'s", "");
	    
	    //Replace new line character
	    text = text.replace("\\n", "");
	    
	    text = text.replaceAll("[^\\p{Alpha}\\p{Digit}]\\%+"," ");
	    
	    //Replace extra Whitespaces
	    text = text.replaceAll("\\s{2,}"," ");
	    
		text = text.trim();
		
		String new_file = "clean_smaple.txt";
		FileWriter myWriter = new FileWriter(new_file);
	    myWriter.write(text);
	    myWriter.close();
		
		return new_file;
	}
}
