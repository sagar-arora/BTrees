import java.util.List;

public class Node {
	public List<Double> keys;
	public boolean isLeafNode=false;
	
	public boolean isOverflowed() {
		return keys.size() > BPlusTree.D-1;
	}
	
}
