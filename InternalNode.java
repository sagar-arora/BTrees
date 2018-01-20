import java.util.ArrayList;
import java.util.List;

public class InternalNode extends Node{

	protected ArrayList<Node> children; // InternalNode Inherits properties of Nodes, It contains the Keys inherited from Node and refernce to Child Pointers

	public InternalNode(Double key, Node child0, Node child1) {
		isLeafNode = false;	
		keys = new ArrayList<Double>(); 	//Initialize keys
		keys.add(key);				//add First Key
		children = new ArrayList<Node>();	//Initialize childrens
		children.add(child0);			//add child1
		children.add(child1);			//add child2
	}

	public InternalNode(List<Double> newKeys, List<Node> newChildren) {
		isLeafNode = false;				
		keys = new ArrayList<Double>(newKeys);
		children = new ArrayList<Node>(newChildren);

	}
	
	/* The insertSorted method is implemented to find the location of Tuple in the list of the Tuples, to put it at that location in the list.*/
	public void insertSorted(Tuple<Double,Node> e, int index) {
		Double key = e.getKey();  		//sort the Nodes according to the key Value
		Node child = e.getValue();
		if (index >= keys.size()) {
			keys.add(key);
			children.add(child);
		} else {
			keys.add(index, key);
			children.add(index+1, child);
		}
	}

		
	}

