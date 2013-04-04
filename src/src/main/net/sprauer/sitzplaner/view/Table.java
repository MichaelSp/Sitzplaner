package net.sprauer.sitzplaner.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;

import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.view.helper.Parameter;

public class Table extends TableBase {

	private static final long serialVersionUID = 5192439868686744222L;
	static final Color DEFAULT_BACKGROUND_COLOR = new Color(240, 200, 20);

	private boolean highlighted = false;

	public Table(int i, ClassRoom classRoom) {
		super(i, classRoom);
		setBackground(DEFAULT_BACKGROUND_COLOR);
		new TableEventListener(this);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setSize(Parameter.cellWidth - Parameter.spacing, Parameter.cellHeight - Parameter.spacing);
	}

	public static Point coordsToPoint(Point coords) {
		Point p = new Point(coords.x * (Parameter.cellWidth + Parameter.spacing / 2) + Parameter.spacing / 2, coords.y
				* (Parameter.cellHeight + Parameter.spacing / 2) + Parameter.spacing / 2);

		p.translate(Parameter.offsetX, Parameter.offsetY);
		return p;
	}

	public Point alignedCoords(Point coords) {
		coords.translate(-classRoom.getLocationOnScreen().x, -classRoom.getLocationOnScreen().y);
		coords.translate(-Parameter.offsetX, -Parameter.offsetY);
		coords.translate(Parameter.cellWidth / 2, Parameter.cellHeight / 2);

		coords.x = coords.x / (Parameter.cellWidth + Parameter.spacing);
		coords.y = coords.y / (Parameter.cellHeight + Parameter.spacing);
		coords.x = Math.max(0, Math.min(coords.x, Parameter.numCols - 1));
		coords.y = Math.max(0, Math.min(coords.y, Parameter.numRows - 1));
		return coords;
	}

	public static Color getColorForValue(int val) {
		double scaled = val / 5.0;
		Color color = Table.DEFAULT_BACKGROUND_COLOR;
		if (val < 0) {
			color = new Color(color.getRed(), (int) ((1.0 - Math.abs(scaled)) * color.getGreen()), color.getBlue());
		} else if (val > 0) {
			color = new Color((int) ((1.0 - scaled) * color.getRed()), color.getGreen(), color.getBlue());
		}
		return color;
	}

	public void setColorForRelationTo(int otherStudent) {
		int val = DataBase.instance().relationBetween(studentIdx, otherStudent);
		setBackground(getColorForValue(val));
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

	public void setPosition(Point position) {
		setLocation(coordsToPoint(position));
	}
}
