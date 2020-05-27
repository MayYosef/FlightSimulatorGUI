package server;

public class State<T> implements Comparable<State<T>>{//domain independent
private T state;;
private double cost;//cost to reach this state
private State<T> cameFrom;//the state we came from to this state

public State(T state) {
	this.state = state;
}

@Override
public boolean equals(Object o) {//compare between address
	State<T> s=(State<T>)o;
	return state.equals(s.state);
}

public double getCost() {
	return cost;
}

public T getState() {
	return state;
}

public void setState(T state) {
	this.state = state;
}

public void setCost(double cost) {
	this.cost = cost;
}

public State<T> getCameFrom() {
	return cameFrom;
}

public void setCameFrom(State<T> cameFrom) {
	this.cameFrom = cameFrom;
}

@Override
public int compareTo(State<T> arg0) {//return the minimum cost 
	return (int) (this.cost-arg0.getCost());//knows automatically which one is the smallest if its negative
}


}
