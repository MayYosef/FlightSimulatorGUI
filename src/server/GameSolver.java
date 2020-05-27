package server;


import java.util.ArrayList;

public class GameSolver implements Solver<Searchable<Point>, ArrayList<State<Point>>>{
	Searcher<Point> s;

	public GameSolver(Searcher<Point> s) {
		//this.s=new BFS<Point>();
		this.s=s;
	}

	@Override
	public ArrayList<State<Point>> solve(Searchable<Point> p) {
		ArrayList<State<Point>> solution= new ArrayList<>();
		solution=s.search(p);
		return solution;
	}
}