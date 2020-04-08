import java.io.*;
import java.util.*;


public class ExtractKeyword {

	public static void printList(Map<String, Keyword> ner_keywords,  Map<String, Keyword> np_keywords) {
		for (Map.Entry<String, Keyword> entry : ner_keywords.entrySet()) {
		    System.out.println(entry.getKey()+" : "+entry.getValue().getFrequency() +" : "+entry.getValue().getTagPriority());
		 }
		System.out.println("-----------------------------------------------");
		
		for (Map.Entry<String, Keyword> entry : np_keywords.entrySet()) {
	    	System.out.println(entry.getKey()+" : "+entry.getValue().getFrequency() +" : "+entry.getValue().getLengthPriority());
		}
	}
	public static List<Keyword> mergeKeywords(Map<String, Keyword> ner_keywords,  Map<String, Keyword> np_keywords) {
		Map<String, Keyword> mergeMap = new HashMap<String, Keyword>();
		for (Map.Entry<String, Keyword> entry : ner_keywords.entrySet()) {
			mergeMap.put(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<String, Keyword> entry : np_keywords.entrySet()) {
			String np_key = entry.getKey();
			Keyword np_value = entry.getValue();
			if(mergeMap.containsKey(np_key)) {
				Keyword k = mergeMap.get(np_key);
		      	k.setFrequency(Math.max(k.getFrequency(), np_value.getFrequency()));
		      	mergeMap.put(np_key, k);
			}else mergeMap.put(np_key, np_value);
		}
		List<Keyword> keywords = new ArrayList<Keyword>();
		for(Keyword k: mergeMap.values()) keywords.add(k);
		return keywords;
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File file = new File("sample4.txt"); 
		
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		
		String cleaned_file = TextCleanser.clean_text(br);
		
		NERDetection nerDetector = new NERDetection(cleaned_file);
		Map<String, Keyword> ner_keywords = nerDetector.detect();
		
		NounPhraseDetection nounPhraseDetector = new NounPhraseDetection(cleaned_file);
		Map<String, Keyword> np_keywords = nounPhraseDetector.detect();
		
		//printList(ner_keywords, np_keywords);
		
		List<Keyword> mergeList = mergeKeywords(ner_keywords, np_keywords);
		
		System.out.println("-----------------------------------------------");
		
		for(Keyword k: mergeList) {
			//System.out.println(k.getPhrase() + " : " + k.getFrequency());
		}
		
		Collections.sort(mergeList, new KeywordSortingComparator());
		
		for(int i = 0; i < 20; i++) {
			System.out.println(mergeList.get(i).getPhrase() + " : " + mergeList.get(i).getFrequency() + " : "+ mergeList.get(i).getTag());
		}
	}
}
