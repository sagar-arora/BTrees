JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        treesearch.java \
        BPlusTree.java \
        Node.java \
        InternalNode.java \
	LeafNode.java \
	KeyValue.java \
	Tuple.java

MAIN= treesearch

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN)

clean:
	$(RM) *.class
