package net.sprauer.sitzplaner.view.helper;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sprauer.sitzplaner.exceptions.ClassRoomToSmallException;

public class Parameter {

	public static final int spacing = 10;
	public static int cellWidth = 80 + spacing;
	public static int cellHeight = 50 + spacing;
	public static final int blackboardBorder = 40;
	public static final int blackboardHeight = 10;

	public static int numRows = 4;
	public static int numCols = 8;
	public static int widthFactor;
	public static int heightFactor;
	public static int maxWidth;
	public static int maxHeight;
	public static int offsetX = 40;
	public static int offsetY = 40;
	public static final int legendWidth = 60;

	public static List<Point> randomUniquePositions(int count) throws ClassRoomToSmallException {
		List<Point> points = new ArrayList<Point>();
		if (numRows * numCols < count) {
			throw new ClassRoomToSmallException();
		}
		for (int x = 0; x < numCols; x++) {
			for (int y = 0; y < numRows; y++) {
				points.add(new Point(x, y));
			}
		}
		Collections.shuffle(points);
		return points;
	}
}
