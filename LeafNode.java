import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class LeafNode extends Node{
	List<String> values;
	LeafNode prevNode;	//since the Leaf Node also forms the linked list, prevNode points to the previous Leaf Node.
	LeafNode nextNode;	//since the Leaf Node also forms the linked list, nextNode points to the next Leaf Node.
	KeyValue keyVal;	//Refrence to the KeyValue class. It  contains a key and multiple String values. This is provided for Duplication of values on a single key. for each key there 				//isArrakeys aylist of Values.
	List<KeyValue> keyvalues; //After each keyvalue is created, it is added in the List of the particular leafnode.

	
	public LeafNode(double firstKey, String firstValue){
		isLeafNode=true;
		keyVal=new KeyValue(firstKey, firstValue);
		keyvalues=new ArrayList<>();
		keyvalues.add(keyVal);
	}
	
	public LeafNode(List<KeyValue> newKeyValues) {
		isLeafNode = true;
		keyvalues = new ArrayList<KeyValue>(newKeyValues);

	}

/* The insertSorted method is implemented to find the location of Tuple in the list of the Tuples, to put it at that location in the list.*/
	
	public void insertSorted(KeyValue k){
		if (k.compareTo(keyvalues.get(0))<0){
			keyvalues.add(0,k);
		}else if (k.compareTo(keyvalues.get(keyvalues.size()-1))>0){
			keyvalues.add(k);
		}
		else{
			ListIterator<KeyValue> iterator=keyvalues.listIterator();
			while(iterator.hasNext()){	
				if(iterator.next().compareTo(k)>0){
					int position=iterator.previousIndex();
					keyvalues.add(position, k);
					break;
				}			
			}
		}
	}

	public KeyValue getKeyValue(int i){
		return keyvalues.get(i);		//getter to return the KeyValue Pair
	}
	public boolean isOverflowed() {			//to check if Leaf Node is full
		return keyvalues.size() > BPlusTree.D-1;
	}
	
}


