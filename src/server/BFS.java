package server;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class BFS<T> extends CommonSearcher<T> {

	@Override
	public ArrayList<State<T>> search(Searchable<T> s) {
		openList =new PriorityQueue<State<T>>((s1,s2)->Double.compare(s1.getCost(), s2.getCost()));
		openList.add(s.getInitialState());
		HashSet<State<T>> closedSet=new HashSet<State<T>>();
		while(openList.size()>0) {
			State<T> n=popOpenList();
			closedSet.add(n);
			if(n.equals(s.getGoalState()))
				return backTrace(n,s.getInitialState());
			ArrayList<State<T>> successors=s.getAllPossibleStates(n);
			for(State<T> state:successors) {
				if(!closedSet.contains(state)&&!openList.contains(state)) {
					state.setCameFrom(n);
					openList.add(state);
				}
				else 
				{
					if(!openList.contains(state))
						openList.add(state);
				}
			}
		}
		return null;
	}
		
	@Override
	public int getNumbrOfNodesEvaluated() {
		return evaluatedNodes;
	}
}




	
