package corpus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

public class Parser {
    // 待处理的字符串
    protected List<String> text;
    // 内部词典
    protected Map<String, Word> dict;
    // 符合条件的候选词
    protected List<String> candidates;
    
    protected int N;
    
    protected long wordCount;
    
    protected Map<String, Integer> frequecyTable = new HashMap<String, Integer>();

	public Parser(List<String> _text, long _wordCount, int _N) {
		this.text = _text;
		this.dict = new HashMap<>();
        this.candidates = new ArrayList<>();
        this.wordCount = _wordCount;
        this.N = _N;
        this.initialise();
	}
    
    public Parser(List<String> _text, List<String> rubbish, long _wordCount, int _N) {
    	this.text = clean(_text, rubbish);
        this.dict = new HashMap<>();
        this.candidates = new ArrayList<>();
        this.wordCount = _wordCount;
        this.N= _N;
        this.initialise();
    }
    
    void initialise() {
    	for (String str : this.text) {
    		if (this.frequecyTable.containsKey(str)) {
    			this.frequecyTable.replace(str, this.frequecyTable.get(str) + 1);
    		} else {
    			this.frequecyTable.put(str, 1);
    		}
    	}
    }
    
    public List<String> getCandidates() throws Exception {
    	this.makedict();
    	long wc = this.filter();
    	System.out.println("共增加新词: " + wc + "个");
    	return this.candidates;
    }
    
    long makedict() throws Exception {
		long count = 0;
		if (this.N <= 2) {
			return count;
		}
		String leftWord = null;
		String rightWord = null;
		Iterator<String> itr1 = this.text.iterator();
		Iterator<String> itr2 = this.text.iterator();
		Iterator<String> itr3 = this.text.iterator();
		for (int i = 2; i < N + 2; i++) {
			if (itr3.hasNext()) {
				rightWord = itr3.next();
			} else {
				rightWord = null;
				break;
			}
		}
		
		ArrayList<String> middle = new ArrayList<String>(); 
		
		
		for (int i = 0; i < N - 2; i++) {
			if (itr2.hasNext()) {
				middle.add(itr2.next());
			} else {
				return count;
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
    			
    			String middleString = "";
    			for (String str : middle) {
    				middleString += str;
    			}
    			
				String key = tmp[0] + middleString + tmp[1];
				middle.remove(0);
    			middle.add(tmp[1]);
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
    
    long filter() {
    	long privateCounter = 0;
    	for (String wordKey : this.dict.keySet()) {
            Word word = this.dict.get(wordKey);
            float mi = this.mi(wordKey);
            float entropy = word.getEntropyLevel();
            
            //System.out.println(wordKey + "   mi: " + mi + "   entropy: " + entropy);
            if (/*mi > 300 &&*/ entropy > 0.5) {  // 筛选条件
                this.candidates.add(wordKey);
                privateCounter++;
            }
        }
    	return privateCounter;
    }
    
    private List<String> clean(List<String> target, List<String> rubbish) {
    	Iterator<String> itr = target.iterator();
    	while(itr.hasNext()){
            String str = itr.next();
            if(rubbish.contains(str)){
                itr.remove();
            }
        }
    	return target;
    }
    
    private float mi(String key) {
    	float tmp = 1.0f;
        tmp = this.frequecyTable.get(this.dict.get(key).getLeftPart()) * this.frequecyTable.get(this.dict.get(key).getRightPart());
        return this.dict.get(key).getFrequency() * this.wordCount / tmp;
    }
}
