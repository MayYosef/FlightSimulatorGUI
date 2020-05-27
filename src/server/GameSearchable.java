package server;



import java.util.ArrayList;

public class GameSearchable implements Searchable<Point> {
	public int[][] matrix;
	public Point pointStart;
	public Point pointEnd;
	
	public GameSearchable(int[][] matrix, Point pointStart, Point pointEnd) {
		this.matrix=matrix;
		this.pointStart=pointStart;
		this.pointEnd=pointEnd;
	}

	@Override
	public State<Point> getInitialState() {
		State<Point> statePointStart=new State<Point>(pointStart);
//		statePointStart.setCameFrom(null);
		statePointStart.setCost(matrix[pointStart.getRow()][pointStart.getCol()]);
		return statePointStart;
	}

	@Override
	public State<Point> getGoalState() {
		State<Point> statePointEnd=new State<Point>(pointEnd);
		statePointEnd.setCost(matrix[pointEnd.getRow()][pointEnd.getCol()]);
		return statePointEnd;
	}

	@Override
	public ArrayList<State<Point>> getAllPossibleStates(State<Point> s) {
		ArrayList<State<Point>> stateArr=new ArrayList<State<Point>>();
		int row=s.getState().getRow();
		int col=s.getState().getCol();
		if(row-1>0) {
			Point p=new Point(row-1,col);
			State<Point> Statepoint=new State<Point>(p);
			Statepoint.setCost(matrix[row-1][col]+s.getCost());
			stateArr.add(Statepoint);
		}
		if(row+1<matrix.length)//return row of the matrix
		{
			Point p1=new Point(row+1, col);
			State<Point> Statepoint1=new State<Point>(p1);
			Statepoint1.setCost(matrix[row+1][col]+s.getCost());
			stateArr.add(Statepoint1);
		}
		if(col+1<matrix[0].length)//return the col of the matrix
		{
			Point p2=new Point(row, col+1);
			State<Point> Statepoint2=new State<Point>(p2);
			Statepoint2.setCost(matrix[row][col+1]+s.getCost());
			stateArr.add(Statepoint2);
		}
		if(col-1>0)
		{
			Point p3=new Point(row, col-1);
			State<Point> Statepoint3=new State<Point>(p3);
			Statepoint3.setCost(matrix[row][col-1]+s.getCost());
			stateArr.add(Statepoint3);
		}
		return stateArr;

}
}