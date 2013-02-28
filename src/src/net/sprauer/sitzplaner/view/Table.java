package net.sprauer.sitzplaner.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;

import net.sprauer.sitzplaner.model.Student;

public class Table extends TableBase {

	private static final long serialVersionUID = 5192439868686744222L;
	static final Color DEFAULT_BACKGROUND_COLOR = new Color(128, 128, 0); // Color(255,

	private boolean highlighted = false;

	public Table(Student student, ClassRoom classRoom) {
		super(student, classRoom);
		setBackground(DEFAULT_BACKGROUND_COLOR);
		new TableEventListener(this);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setSize(Parameter.tableWidth - Parameter.spacing, Parameter.tableHeight - Parameter.spacing);
	}

	public static Point coordsToPoint(Point coords) {
		Point p = new Point(coords.x * (Parameter.tableWidth + Parameter.spacing / 2) + Parameter.spacing / 2, coords.y
				* (Parameter.tableHeight + Parameter.spacing / 2) + Parameter.spacing / 2);

		p.translate(Parameter.offsetX, Parameter.offsetY);
		return p;
	}

	public Point alignedCoords(Point coords) {
		coords.translate(-classRoom.getLocationOnScreen().x, -classRoom.getLocationOnScreen().y);
		coords.translate(-Parameter.offsetX, -Parameter.offsetY);
		coords.translate(Parameter.tableWidth / 2, Parameter.tableHeight / 2);

		coords.x = coords.x / (Parameter.tableWidth + Parameter.spacing);
		coords.y = coords.y / (Parameter.tableHeight + Parameter.spacing);
		coords.x = Math.max(0, Math.min(coords.x, Parameter.numCols - 1));
		coords.y = Math.max(0, Math.min(coords.y, Parameter.numRows - 1));
		return coords;
	}

	public void setColorForRelationTo(Student other) {
		double val = student.relationTo(other);
		setBackground(new Color((int) (val * 255.0), (int) ((1.0 - val) * 255.0), 0));
	}

	public void highlight(boolean val) {
		highlighted = val;
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (highlighted) {
			g.setColor(Color.white);
			for (int i = 0; i < 6; i++) {
				g.drawRect(0 + i, 0 + i, getWidth() - i * 2, getHeight() - i * 2);
			}
		}

	}
}
