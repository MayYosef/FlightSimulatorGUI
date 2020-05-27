package server;


import java.util.ArrayList;
import java.util.PriorityQueue;

public abstract class CommonSearcher<T> implements Searcher<T> {
protected PriorityQueue<State<T>> openList;
protected int evaluatedNodes;
public CommonSearcher() {
	//openList = new PriorityQueue<State<T>>((s1,s2)->Double.compare(s1.getCost(), s2.getCost()));//between two states compare according to their cost
	evaluatedNodes=0;
}

protected State<T> popOpenList(){
	evaluatedNodes++;
	return openList.poll();
}

protected ArrayList<State<T>> backTrace(State<T> goal,State<T> init) {
	ArrayList<State<T>> path=new ArrayList<>();
	while(!goal.equals(init)) {
		path.add(goal);
		goal=goal.getCameFrom();
	}
	path.add(init);
	return path;
}
}


