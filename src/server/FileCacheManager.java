package server;



import java.util.ArrayList;
import java.util.HashMap;

public class FileCacheManager implements CacheManager<Searchable<Point>,  ArrayList<State<Point>>>  {
	HashMap<Searchable<Point>,  ArrayList<State<Point>>> map;
	public FileCacheManager() {
		map=new HashMap<>();
	}
	
public HashMap<Searchable<Point>,  ArrayList<State<Point>>> getMap() {
		return map;
	}

	public void setMap(HashMap<Searchable<Point>,  ArrayList<State<Point>>> map) {
		this.map = map;
	}

@Override
public ArrayList<State<Point>> Search(Searchable<Point> p) { 
		return map.get(p);
}

public void Save(ArrayList<State<Point>>  s,Searchable<Point> p) {
	map.put(p,s);
}

}
	
//	@Override
//	public String Save(String p){
//
//		ObjectInputStream reader=null;
//		PrintWriter writer=null;
//		try {
//			reader=new ObjectInputStream(new FileInputStream(problem+".txt"));
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//			writer=new PrintWriter(new FileWriter(out));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			if(!(s.solve(p).equals(reader.read()))) {
//			//	str.add(s.solve(p));
//				writer.println(s.solve(p));
//				writer.flush();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return s.solve(p);
			
		
	


