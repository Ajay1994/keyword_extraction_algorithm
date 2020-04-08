import java.util.*;

public class KeywordSortingComparator implements Comparator<Keyword>{

	public int compareTo(int a, int b) {
		//if s1 > s2, it returns positive number  
		//if s1 < s2, it returns negative number  
		//if s1 == s2, it returns 0  
		if(a > b) return -1;
		else if(a < b) return 1;
		else return 0;
	}
	@Override
	public int compare(Keyword o1, Keyword o2) {
		// TODO Auto-generated method stub
		// all comparison
        int compareFrequency = compareTo(o1.getFrequency(), o2.getFrequency());
        int compareTag = compareTo(o1.getTagPriority(), o2.getTagPriority());
        int compareLength = compareTo( o1.getLengthPriority(), o2.getLengthPriority());
 
        // 3-level comparison using if-else block
        if(compareFrequency == 0) {
            return ((compareTag == 0) ? compareLength : compareTag);
        }
        else {
            return compareFrequency;
        }
	}

}
