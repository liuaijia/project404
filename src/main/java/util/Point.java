package util;

import model.manager.MoveTypeEnum;

import java.util.Vector;


public class Point {
	public int i;
	public int j;
	public Vector<MoveTypeEnum> path;
	public Point(int i,int j) {
		this.i = i;
		this.j = j;
		path = new Vector<>();
	}
}
