import java.util.ArrayList;
import java.util.List;

public class Keyword {
	private String phrase;
	private String tag;
	private int frequency;
	//Location, Person, Organization, Money, Percent, Date, Time
    public List<String> getSortingRules(){
    	List<String> rulesorting = new ArrayList<String>();
    	rulesorting.add("PERSON");
		//rulesorting.add("NORP");
		rulesorting.add("ORGANIZATION");
		//rulesorting.add("GPE");
		//rulesorting.add("EVENT");
		//rulesorting.add("WORK_OF_ART");
		rulesorting.add("LOC");
		//rulesorting.add("FAC");
		//rulesorting.add("PRODUCT");
		//rulesorting.add("LANGUAGE");
		rulesorting.add("DATE");
		//rulesorting.add("LAW");
		rulesorting.add("NOTAG");
		rulesorting.add("MONEY");
		//rulesorting.add("QUANTITY");
		//rulesorting.add("ORDINAL");
		rulesorting.add("PERCENT");
		rulesorting.add("TIME");
		//rulesorting.add("CARDINAL");
    	return rulesorting;
    }
	public String getPhrase() {
		return phrase;
	}
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public int getLengthPriority() {
		return this.phrase.length();
	}
	
	public int getTagPriority() {
		List<String> rules = getSortingRules();
		return rules.size() - rules.indexOf(this.tag);
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}
