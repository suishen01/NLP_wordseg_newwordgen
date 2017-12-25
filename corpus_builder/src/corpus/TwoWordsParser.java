package corpus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TwoWordsParser extends Parser {

	private int N;
	
	public TwoWordsParser(List<String> _text, long _wordCount, int _N) {
		super(_text, _wordCount, _N);
		this.N = 2;
	}
	
	public TwoWordsParser(List<String> _text, List<String> rubbish, long _wordCount, int _N) {
		super(_text, rubbish, _wordCount, _N);
		this.N = 2;
	}

	@Override
    long makedict() throws Exception {
    	long count = 0;
		String leftWord = null;
		String rightWord = null;
		Iterator<String> itr1 = this.text.iterator();
		Iterator<String> itr2 = this.text.iterator();
		Iterator<String> itr3 = this.text.iterator();
		for (int i = 2; i < 4; i++) {
			if (itr3.hasNext()) {
				rightWord = itr3.next();
			} else {
				rightWord = null;
				break;
			}
		}
		
		
		if (itr2.hasNext()) {
			itr2.next();
		} else {
			return count;
		}
		
		while (true) {  
			String[] tmp = new String[2];
			if (itr1.hasNext()) {
				tmp[0] = itr1.next();
				if (itr3.hasNext()) {
    				rightWord = itr3.next();
    			} else {
    				rightWord = null;
    			}
    			if (itr2.hasNext()) {
    				tmp[1] = itr2.next();
    			} else {
    				break;
    			}
    			
				String key = tmp[0] + tmp[1];
    			if (this.dict.containsKey(key)) {
    				Word word = this.dict.get(key);
    				word.frequencyIncrement();
    				word.addLeftWord(leftWord);
    				leftWord = tmp[0];
    				word.addRightWord(rightWord);
    			} else {
    				Word word = new Word(tmp);
        			word.addLeftWord(leftWord);
        			leftWord = tmp[0];
        			word.addRightWord(rightWord);
        			this.dict.put(key, word);
    			}
    			count++;
    		}
        }
    	
        return count;
    }
}
