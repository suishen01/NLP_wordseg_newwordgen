package corpus;

import java.util.ArrayList;
import java.util.List;

public class CorpusBuilder {
	protected List<String> text;
	protected int N;
	protected List<String> rubbish;
	protected long wordCount;
	protected boolean needsClean;
	public enum TYPE{SINGLE, MULTI};
	protected TYPE type;
	
	public CorpusBuilder(List<String> _text, long _wordCount, int _N) {
		this.wordCount = _wordCount;
		this.text = _text;
        this.N = _N;
        this.needsClean = false;
        this.type = TYPE.SINGLE;
	}	
	
	public CorpusBuilder(List<String> _text, long _wordCount, int _N, TYPE _type) {
		this.wordCount = _wordCount;
		this.text = _text;
        this.N = _N;
        this.needsClean = false;
        this.type = _type;
	}
	
	public CorpusBuilder(List<String> _text, List<String> _rubbish, long _wordCount, int _N,  TYPE _type) {
		this.rubbish = _rubbish;
		this.wordCount = _wordCount;
		this.text = _text;
        this.N = _N;
        this.needsClean = true;
        this.type = _type;
	}
	
	public List<String> build() throws Exception {
		if (this.N < 2) {
			return null;
		}
		
		switch(this.type) {
			case SINGLE:
				return this.singleBuilder();
			case MULTI:
				return this.multiBuilder();
		}
		
		return null;
	}
	
	List<String> singleBuilder() throws Exception {
		Parser parser;
		
		if (this.N == 2) {
			if (!this.needsClean) {
				parser = new TwoWordsParser(this.text, this.wordCount, this.N);
			} else {
				parser = new TwoWordsParser(this.text, this.rubbish, this.wordCount, this.N);
			}
		} else {
			if (!this.needsClean) {
				parser = new Parser(this.text, this.wordCount, this.N);
			} else {
				parser = new Parser(this.text, this.rubbish, this.wordCount, this.N);
			}
		}
		
		return parser.getCandidates();
	}
	
	List<String> multiBuilder() throws Exception {
		List<String> result = new ArrayList<String>();
		Parser parser;
		if (this.N == 2) {
			if (!this.needsClean) {
				parser = new TwoWordsParser(this.text, this.wordCount, this.N);
				result = parser.getCandidates();
			} else {
				parser = new TwoWordsParser(this.text, this.rubbish, this.wordCount, this.N);
				result = parser.getCandidates();
			}
		} else {
			if (!this.needsClean) {
				for (int i = this.N; i > 2; i--) {
					parser = new Parser(this.text, this.wordCount, i);
					result.addAll(parser.getCandidates());
				}
				Parser parserTwoWords = new TwoWordsParser(this.text, this.wordCount, 2);
				result.addAll(parserTwoWords.getCandidates());
			} else {
				for (int i = this.N; i > 2; i--) {
					parser = new Parser(this.text, this.rubbish, this.wordCount, i);
					result.addAll(parser.getCandidates());
				}
				Parser parserTwoWords = new TwoWordsParser(this.text, this.rubbish, this.wordCount, 2);
				result.addAll(parserTwoWords.getCandidates());
			}
		}
		return result;
	}
}
