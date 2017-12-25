# coding=utf-8
import sys
import jieba
import jieba.posseg

fin = open(sys.argv[1], 'r', encoding="utf8")
fou = open(sys.argv[2], 'w', encoding="utf8")
jieba.load_userdict(sys.argv[3]) 
line = fin.readline()

while line:
	newline = jieba.cut(line, HMM=False)
	for w in newline:
		tmp = "\n".join(newline)
		fou.write(tmp)	
	line = fin.readline()

fin.close()
fou.close()