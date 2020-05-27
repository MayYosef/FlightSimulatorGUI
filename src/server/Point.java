package server;


public class Point {
public int row;
public int col;

public int getRow() {
	return row;
}

public void setRow(int row) {
	this.row = row;
}

public int getCol() {
	return col;
}

public void setCol(int col) {
	this.col = col;
}

public Point(int row, int col) {
		this.row = row;
		this.col = col;
	}

@Override
public boolean equals(Object o) {//compare between premitiv (==)
	Point s=(Point)o;
	if((s.getRow()==row)&& (s.getCol()==col))
		return true;
	return false;
}

}

