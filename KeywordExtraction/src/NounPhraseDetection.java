import java.io.*;
import java.util.*;

import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class NounPhraseDetection {
	private BufferedReader br;
	NounPhraseDetection(String filename) throws IOException{
		this.br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));
	}
	public Map<String, Keyword> addItem(Map<String, Keyword> list, String keyword){
		keyword = keyword.toLowerCase();
		Stemmer s = new Stemmer();
		s.add(keyword.toCharArray(), keyword.length());
		s.stem();
        keyword = s.toString();
        
		if(list.containsKey(keyword)) {
      	  Keyword k = list.get(keyword);
      	  k.setFrequency(k.getFrequency() + 1);
      	  list.put(keyword, k);
        }else {
      	  Keyword k = new Keyword();
      	  k.setPhrase(keyword);
      	  k.setFrequency(1);
      	  k.setTag("NOTAG");
      	  list.put(keyword, k);
        }
		return list;
	}
	public Map<String, Keyword> createNounPhraseList(Map<String, Keyword> nounPhraseKeywords, List<TaggedWord> tSentence) {
		
		int len = tSentence.size();
		int i = 0;
		while(i < len) {
			TaggedWord tw = tSentence.get(i);
			String s = "";
			if(tw.tag().startsWith("JJ")) {
				s += tw.word();
				i += 1;
				if(i >= len || tSentence.get(i).tag().startsWith("NN") == false) continue;
				while(i < len && tSentence.get(i).tag().startsWith("NN")) {
					s += " " + tSentence.get(i).word();
					i += 1;
				}
				addItem(nounPhraseKeywords, s);
			}
			if(tw.tag().startsWith("NN")) {
				s += tw.word();
				i += 1;
				while(i < len && tSentence.get(i).tag().startsWith("NN")) {
					s += " " + tSentence.get(i).word();
					i += 1;
				}
				addItem(nounPhraseKeywords, s);
			}
			i += 1;
		}
		return nounPhraseKeywords;
	}
	public Map<String, Keyword> detect() throws IOException {
		MaxentTagger tagger = new MaxentTagger("english-left3words-distsim.tagger");
	    TokenizerFactory<CoreLabel> ptbTokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(),
										   "untokenizable=noneKeep");
	    Map<String, Keyword> nounPhraseKeywords = new HashMap<String, Keyword>();
	    
	    DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor(br);
	    documentPreprocessor.setTokenizerFactory(ptbTokenizerFactory);
	    for (List<HasWord> sentence : documentPreprocessor) {
	      List<TaggedWord> tSentence = tagger.tagSentence(sentence);
	      //System.out.println(SentenceUtils.listToString(tSentence, false));
	      createNounPhraseList(nounPhraseKeywords, tSentence);
	      //System.out.println("-----");
	    }
	    //System.out.println(nounPhraseKeywords.size());
	    return nounPhraseKeywords;
	}
}
