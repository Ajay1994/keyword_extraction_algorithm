import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.util.Triple;

public class NERDetection {
	public String text;
	NERDetection(String filename) throws IOException{
		File file = new File(filename); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		String st; 
		String text = "";
		
		while ((st = br.readLine()) != null)
		   text += st;
		this.text = text;
	}
	public Map<String, Keyword> detect() throws Exception {
		String serializedClassifier = "english.muc.7class.distsim.crf.ser.gz";
		
		Map<String, Keyword> ner_keywords = new HashMap<String, Keyword>();
		
		AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
		List<Triple<String,Integer,Integer>> triples = classifier.classifyToCharacterOffsets(this.text);
        for (Triple<String,Integer,Integer> trip : triples) {
          System.out.printf("%s : %s over character offsets [%d, %d)", this.text.substring(trip.second(), trip.third()),
                  trip.first(), trip.second(), trip.third());
          
          String keyword = this.text.substring(trip.second(), trip.third()).toLowerCase();
          if(ner_keywords.containsKey(keyword)) {
        	  Keyword k = ner_keywords.get(keyword);
        	  k.setFrequency(k.getFrequency() + 1);
        	  ner_keywords.put(keyword, k);
          }else {
        	  Keyword k = new Keyword();
        	  k.setPhrase(keyword);
        	  k.setFrequency(1);
        	  k.setTag(trip.first());
        	  ner_keywords.put(keyword, k);
          }
          System.out.println();
        }
		return ner_keywords;
	}
}
