import java.util.ArrayList;
import java.util.List;

public class BPlusTree {
	public Node root;
	public static int D=2;
	
	public void initialize(int order){
		D=order;
	}
	public List<String> search(double key){
		
		// if root is null return null
		if(root == null) {
			return null;
		}
		// find the leaf node that key is pointing to
		LeafNode leaf = (LeafNode)treeSearch(root, key);
		
		for(int i=0; i<leaf.keyvalues.size(); i++) {
			if(key==leaf.getKeyValue(i).getKey()) 
					return leaf.getKeyValue(i).getValues();
			}
		
		return null;
	}
	public Node treeSearch(Node node,double key){
		
		if(node.isLeafNode) {
			return node;
		}
		// if it is an Internal Node, recursively find the child
		else {
			InternalNode internal = (InternalNode)node;
			
			// K < K1, return treeSearch(C0, K)
			if(key<internal.keys.get(0))  {
				return treeSearch((Node)internal.children.get(0), key);
			}
			// K >= Km, return treeSearch(Cm, K), m 
			
			else if(key>=internal.keys.get(node.keys.size()-1))  {
				return treeSearch((Node)internal.children.get(internal.children.size()-1), key);
			}
			// Find i such that Ki <= K < K(i+1), return treeSearch(Ci,K)
			
			else {
				// search the child node to travel down the BPlus Tree
				
				for(int i=0; i<internal.keys.size()-1; i++) {
					if(key>=internal.keys.get(i) && key < internal.keys.get(i+1)) {
						return treeSearch((Node)internal.children.get(i+1), key);
					}
				}
 			}
		}
		
		return null;
	}
	
	// Range Search returns the Tuple of Double and List of String Values

/* first the search is applied on the key1. If the leaf node for the key1 is found, leaf node is traversed to find the values greater than the key1 and all the values lesser than the key2. If end of leaf node is reached than  by using the next pointer (since we are maintaining the doubly linked list) we traverse the next node by it and again compare the key with key1 and key2. 
*/
	public List<Tuple<Double,List<String>>> search(double key1, double key2){
		if(key1>key2)
			return null;
		// Tuple is the Class to hold key value pair
		Tuple<Double,List<String>> tuple;
		List<Tuple<Double,List<String>>> list=new ArrayList<>();
		LeafNode leaf = (LeafNode)treeSearch(root, key1);
		//Only if the leaf is found otherwise return null
		if (leaf != null){
			while(leaf!=null && leaf.getKeyValue(0).getKey()<=key2){
			for(int i=0; i<leaf.keyvalues.size(); i++) {
				if(key1<=leaf.getKeyValue(i).getKey() && key2>=leaf.getKeyValue(i).getKey()) {
					tuple=new Tuple<Double,List<String>>(leaf.getKeyValue(i).getKey(),leaf.getKeyValue(i).getValues());	
					list.add(tuple);	
				}
				}
			leaf=(LeafNode)leaf.nextNode;
			}
			return list;
		}			
		
		return null;
	}
	
	public void insert(double key, String value){
		
		//If the key is already present search the key first and 
		if(search(key)!=null){
			LeafNode leaf = (LeafNode)treeSearch(root, key);
			
			for(int i=0; i<leaf.keyvalues.size(); i++) {
				if(key==leaf.getKeyValue(i).getKey()) {
						leaf.getKeyValue(i).values.add(value);
						return;
				}
			}
			
		}
		
		LeafNode newLeaf = new LeafNode(key, value);
		
		Tuple<Double,Node> entry=new Tuple<Double, Node>(key,newLeaf);
		// Insert entry into subtree with root node pointer
		if(root == null ) {
			root = entry.getValue();
		}
		
		// newChildEntry null initially, and null on return unless child is split
		Tuple<Double, Node> newChildEntry = getChildEntry(root, entry, null);
		
		if(newChildEntry == null) {
			return;
		} else {
			InternalNode newRoot = new InternalNode(newChildEntry.getKey(), root, newChildEntry.getValue());
			root = newRoot;
			return;
		}
	}
		
		public Tuple<Double,Node> getChildEntry(Node node, Tuple<Double, Node> entry, Tuple<Double, Node> newChildEntry) 
		{
			
			if(!node.isLeafNode) {
				// Choose subtree, find i such that Ki <= entry's key value < J(i+1)
				InternalNode index = (InternalNode) node;
				int i = 0;
				while(i < index.keys.size()) {
					if(entry.getKey().compareTo(index.keys.get(i)) < 0) {
						break;
					}
					i++;
				}
				// Recursively, insert entry
				newChildEntry = getChildEntry((Node) index.children.get(i), entry, newChildEntry);
				
				// Usual case, didn't split child
				if(newChildEntry == null) {
					return null;
				} 
				// Split child case, must insert newChildEntry in node
				else {
					int j = 0;
					while (j < index.keys.size()) {
						if(newChildEntry.getKey().compareTo(index.keys.get(j)) < 0) {
							break;
						}
						j++;
					}
					
					index.insertSorted(newChildEntry, j);
					
					// Usual case, put newChildEntry on it, set newChildEntry to null, return
					if(!index.isOverflowed()) {
						return null;
					} 
					else{
						newChildEntry = splitInternalNode(index);
						
						// Root was just split
						if(index == root) {
							// Create new node and make tree's root-node pointer point to newRoot
							
							InternalNode newRoot = new InternalNode(newChildEntry.getKey(), root, 
									newChildEntry.getValue());
							root = newRoot;
							return null;
						}
						return newChildEntry;
					}
				}
			}
			// Node pointer is a leaf node
			else {
				LeafNode leaf = (LeafNode)node;
				LeafNode newLeaf = (LeafNode)entry.getValue();
				
				leaf.insertSorted(newLeaf.keyVal);
				
				// the case in which leaf has space, put entry and set newChildEntry to null and return
				if(!leaf.isOverflowed()) {
					return null;
				}
				// when the leaf is full
				else {
					newChildEntry = splitLeafNode(leaf);
					if(leaf == root) {
						InternalNode newRoot = new InternalNode(newChildEntry.getKey(), leaf, 
								newChildEntry.getValue());
						root = newRoot;
						return null;
					}
					return newChildEntry;
				}
			}
		}
		
		
		public Tuple<Double, Node> splitLeafNode(LeafNode leaf) {
			
			ArrayList<KeyValue> newKeyValue=new ArrayList<>();

			for(int i=(int)Math.ceil((D-1)/2); i<=D-1; i++) {
				newKeyValue.add(leaf.getKeyValue(i));
			}
			

			for(int i=(int)Math.ceil((D-1)/2); i<=D-1; i++) {
				leaf.keyvalues.remove(leaf.keyvalues.size()-1);
			}
			
			Double splitKey = newKeyValue.get(0).getKey();
			
			LeafNode rightNode=new LeafNode(newKeyValue);
			
			// Set pointers for Next Node and Previous Node to create a Doubly Linked List
			LeafNode tmp = leaf.nextNode;
			leaf.nextNode = rightNode;
			leaf.nextNode.prevNode = rightNode;
			rightNode.prevNode = leaf;
			rightNode.nextNode = tmp;
	        
			Tuple<Double, Node> newChildEntry = new Tuple<Double, Node>(splitKey, rightNode);
			
			return newChildEntry;
		}
	
		public Tuple<Double, Node> splitInternalNode(InternalNode index) {
			
			ArrayList<Double> newKeys = new ArrayList<Double>();
			ArrayList<Node> newChildren = new ArrayList<Node>();
			
			// Calculate the Split Index, according to the formulae
			
			int splitIndex=(int)Math.ceil((D-1)/2);
			Double splitKey = (Double)index.keys.get(splitIndex);
			index.keys.remove(splitIndex);
			
			// from First to splitIndex key values and splitIndex+1 node pointers stay
			// splitInddex to Last keys and splitIndex+1 pointers move to new node
			newChildren.add((Node)index.children.get(splitIndex+1));
			index.children.remove(splitIndex+1);
			
			while(index.keys.size() > splitIndex){
				newKeys.add(index.keys.get(splitIndex));
				index.keys.remove(splitIndex);
				newChildren.add((Node)index.children.get(splitIndex+1));
				index.children.remove(splitIndex+1);
			}

			InternalNode rightNode = new InternalNode(newKeys, newChildren);
			Tuple<Double,Node> newChildEntry = new Tuple<Double,Node>(splitKey, rightNode);

			return newChildEntry;
		}

}

