package server;


import java.util.ArrayList;

public interface Searchable<T> {
	State<T> getInitialState();
	State<T> getGoalState();
	ArrayList<State<T>> getAllPossibleStates(State<T> s);

}
