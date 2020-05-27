package server;


import java.util.ArrayList;

public interface CacheManager<Solution, Problem>{
	ArrayList<State<Point>> Search(Searchable<Point> p);
	void Save(ArrayList<State<Point>>s, Searchable<Point> p);

}
