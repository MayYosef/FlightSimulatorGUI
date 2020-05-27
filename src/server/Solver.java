package server;


import java.util.ArrayList;

public interface Solver<Problem,Solution> {
	 ArrayList<State<Point>> solve(Searchable<Point> p);
}
