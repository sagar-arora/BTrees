import java.util.ArrayList;
import java.util.List;

public class KeyValue implements Comparable<KeyValue>{
	//KeyValue class contains a key and multiple String values. This is provided for Duplication of values on a single key. for each key there is Arraylist of Values 
	double key;
	List<String> values;
	
	public KeyValue(double key,String value){ //This is the constructor to intialize the key value pair in the given tuple.
			this.key=key;
			this.values= new ArrayList<String>();
			values.add(value);
	}
	
	public void setValue(String value){
		values.add(value); //getter to add value in the List of values associated with a key
	}
	
	public double getKey(){
		return this.key; // getter to add Key in the KeyValue Pair
	}
	
	public String getValue(){
		return values.get(0);
	}

	public List<String> getValues(){
		return values;		//getter to get all the String values associated with the key
	}
	
	@Override
	public int compareTo(KeyValue k) { // method to compare each keys
		if (key == k.getKey()){
			return 0;
		}
		else if (key>k.getKey()){
			return 1;
		}
		else{
			return -1;
		}
	}	
	
}
