package server;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class MyClientHandler implements ClientHandler {
	
	protected Solver<Searchable<Point>,ArrayList<State<Point>>> solver;
	protected CacheManager<Searchable<Point>, ArrayList<State<Point>>> cm;
	
	
public MyClientHandler() {
		this.solver = new GameSolver(new BFS<Point>());
		this.cm = new FileCacheManager();
	}

	public void handleClient(InputStream input, OutputStream output) {
		try {
			String line=new String();
			String[] split=null;
			int countRows=0;//count the number of the lines- this will be the number of the rows in matrix
			int countCol=0;
			int m=0;
			String entrance=new String();
			String exit=new String();
			ArrayList<String> SplitCopy=new ArrayList<String>();
			BufferedReader in=new BufferedReader(new InputStreamReader(input));
			PrintWriter out=new PrintWriter(new OutputStreamWriter(output));
			while(!(line=in.readLine()).equals("end")) {
				countRows++;
			    split = line.split(",");
			    for (String s : split) {
			    	SplitCopy.add(s);
			    }
			}
			countCol=SplitCopy.size()/countRows;
		    
			int[][] matrix=new int[countRows][countCol];
			for(int i=0;i<countRows;i++) {
				for(int j=0;j<countCol;j++) {
					matrix[i][j]=Integer.parseInt(SplitCopy.get(m));
					m++;
				}
			}
			entrance=in.readLine();
			String[] splitStart=entrance.split(",");
			int rowStart=Integer.parseInt(splitStart[0]);
			int colStart=Integer.parseInt(splitStart[1]);
			exit=in.readLine();
			String[] splitEnd=exit.split(",");
			int rowEnd=Integer.parseInt(splitEnd[0]);
			int colEnd=Integer.parseInt(splitEnd[1]);
			Point pointStart=new Point(rowStart, colStart);
			Point pointEnd=new Point(rowEnd, colEnd);
			GameSearchable Theproblem=new GameSearchable(matrix,pointStart,pointEnd);
			ArrayList<State<Point>> Thesolution=new ArrayList<State<Point>>();
			if(cm.Search(Theproblem)!=null) {
				Thesolution=cm.Search(Theproblem);
			}
			else {
				Thesolution=solver.solve(Theproblem);
				Collections.reverse(Thesolution);
				cm.Save(Thesolution,Theproblem);
			}
			StringBuilder directions=new StringBuilder();
			int row=pointStart.getRow();
			int col=pointStart.getCol();
			for(int l=1;l<Thesolution.size();l++) {
				if((Thesolution.get(l).getState().getRow()==row+1)&&(Thesolution.get(l).getState().getCol()==col)) {
					directions.append("Down,");
					row++;
				}
				if((Thesolution.get(l).getState().getRow()==row-1)&&(Thesolution.get(l).getState().getCol()==col)) {
					directions.append("Up,");
					row--;
				}
				if((Thesolution.get(l).getState().getRow()==row)&&(Thesolution.get(l).getState().getCol()==col+1)) {
					directions.append("Right,");
					col++;
				}
				if((Thesolution.get(l).getState().getRow()==row)&&(Thesolution.get(l).getState().getCol()==col-1)) {
					directions.append("Left,");
					col--;
				}
			}
			directions.deleteCharAt(directions.length()-1);
			out.println(directions.toString());
			out.flush();
			in.close();
			out.close();
		}catch (IOException e) {
			e.printStackTrace();}
	}
}
