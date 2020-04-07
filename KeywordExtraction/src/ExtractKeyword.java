import java.io.*;
import java.util.*;

public class ExtractKeyword {

	public static String clean_text(String text) {
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
		return text;
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File file = new File("sample1.txt"); 
		
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		
		String cleaned_file = TextCleanser.clean_text(br);
		
		//System.out.println(clean_text(text));
		
		NERDetection nerDetector = new NERDetection(cleaned_file);
		Map<String, Keyword> ner_keywords = nerDetector.detect();
		for (Map.Entry<String, Keyword> entry : ner_keywords.entrySet()) {
		    System.out.println(entry.getKey()+" : "+entry.getValue().getFrequency() +" : "+entry.getValue().getTagPriority());
		    System.out.println("-");
		}
	}
}
