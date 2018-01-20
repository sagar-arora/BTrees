import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class treesearch {

public static void main(String[] args) throws FileNotFoundException, IOException{
		
		String line=null;
		String[] arg;
		BufferedReader br=new BufferedReader(new FileReader(args[0]));
		BPlusTree bplus=new BPlusTree();
		BufferedWriter bw=new BufferedWriter(new FileWriter("output_file.txt",true));
		bplus.initialize(Integer.parseInt(br.readLine().trim()));
		List<String> values;
		List<Tuple<Double,List<String>>> map;
		while((line=br.readLine())!=null){		
			if (line.charAt(0)=='I'){
				arg=line.split("\\(");
				String[] s=arg[1].split(",");
				bplus.insert(Double.parseDouble(s[0]),s[1].substring(0, s[1].length()-1));
				continue;
			}	
			
			if (line.charAt(0)=='S'){

				if(line.indexOf(",")==-1){
					arg=line.split("\\(");
					values=bplus.search(Double.parseDouble(arg[1].substring(0, arg[1].length()-1)))	;		
					formatSearchOutput(bw,values);
					continue;
				}
				else{
					arg=line.split("\\(");
					String[] s=arg[1].split(",");
					map=bplus.search(Double.parseDouble(s[0]),Double.parseDouble(s[1].substring(0, s[1].length()-1)));
					formatRangeSearchOutput(bw,map);
					continue;
				}
			}
				
		}
	
		br.close();
		bw.close();
}
	
	public static void formatSearchOutput(BufferedWriter bw, List<String> values){
		try {
		if(values ==null){
			bw.write("Null");
			bw.newLine();
			return;
		}
		int i;
		for( i=0;i<values.size();i++){
				bw.write(values.get(i));
				if(i+1<values.size())
					bw.write(", ");
		}
		bw.newLine();
		} catch (IOException e) {
				
				e.printStackTrace();
			}
}

	public static void formatRangeSearchOutput(BufferedWriter bw, List<Tuple<Double,List<String>>> map){
		try{
		for(int i=0;i<map.size();i++){
			for (int j=0;j<map.get(i).getValue().size();j++){
				bw.write("("+map.get(i).getKey()+","+(map.get(i).getValue().get(j))+")" );
				if(j+1<(map.get(i).getValue()).size() && (map.get(i).getValue().get(j+1))!=null)
					bw.write(", ");
			}
			if(i+1<map.size() &&map.get(i+1)!=null)
				bw.write(", ");
		}
		bw.newLine();
		}
		catch(Exception e){
			e.getStackTrace();
		}
	}
	
	
}
