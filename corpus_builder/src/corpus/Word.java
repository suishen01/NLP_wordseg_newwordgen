package corpus;

import java.util.HashMap;
import java.util.Map;

public class Word {
	Map<String, Long> left = new HashMap<String, Long>();
	Map<String, Long> right = new HashMap<String, Long>();
	String[] content = {};
	long leftNum = 0;
	long rightNum = 0;
	long frequency = 0;
	
	Word(String[] _content) {
		this.content = _content;
		this.frequency++;
	}
	
	public String getLeftPart() {
		return this.content[0];
	}
	
	public String getRightPart() {
		return this.content[this.content.length - 1];
	}
	
	void addLeftWord(String _leftWord) {
		if (_leftWord == null) {
			return;
		}
		if (this.left.containsKey(_leftWord)) {
			this.left.put(_leftWord, this.left.get(_leftWord) + 1);
		} else {
			this.left.put(_leftWord, new Long(1));
		}
		this.leftNum++;
	}
	
	void addRightWord(String _rightWord) {
		if (_rightWord == null) {
			return;
		}
		if (this.right.containsKey(_rightWord)) {
			this.right.put(_rightWord, this.right.get(_rightWord) + 1);
		} else {
			this.right.put(_rightWord, new Long(1));
		}
		this.rightNum++;
	}
	
	void setFrequency(long _frequency) {
		this.frequency = _frequency;
	}
	
	long getFrequency() {
		return this.frequency;
	}
	
	void frequencyIncrement() {
		this.frequency++;
	}
	
	long getLeftNum() {
		return this.leftNum;
	}
	
	long getRightNum() {
		return this.rightNum;
	}
	
	float getWordPossibility(long N) {
		return this.frequency/N;
	}
	
	float getEntropyLevel() {
		if (this.frequency == 1
	            || this.leftNum == 0
	            || this.rightNum == 0) {
	            return 0.0f;
	        }

	        float leftEntropy = 0.0f;
	        float rightEntropy = 0.0f;
	        for (String entry : this.left.keySet()) {
	            float p = (float) this.left.get(entry) / this.leftNum;
	            leftEntropy += (-p) * Math.log(p);
	        }
	        for (String entry : this.right.keySet()) {
	        	float p = (float) this.right.get(entry) / this.rightNum;
	            rightEntropy += (-p) * Math.log(p);
	        }
	        return leftEntropy > rightEntropy ? rightEntropy : leftEntropy;
	}
}
