package net.sprauer.sitzplaner.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sprauer.sitzplaner.EA.GeneString;
import net.sprauer.sitzplaner.model.Student;

public class ClassRoom extends JPanel {

	private static final long serialVersionUID = -7794245134688207661L;
	private final List<Table> tables = new ArrayList<Table>();
	final Blackboard blackboard;
	private Polygon selectionLine;
	private int selectionRelationValue;
	ClassRoomEventListener eventListener;
	final Legende legende;
	private final JLabel lblFitness;

	public ClassRoom() {
		Dimension size = new Dimension(760, 340);
		setPreferredSize(size);
		setMinimumSize(size);
		eventListener = new ClassRoomEventListener(this);
		addComponentListener(eventListener);
		addMouseWheelListener(eventListener);
		addKeyListener(eventListener);
		addMouseListener(eventListener);
		setLayout(null);
		blackboard = new Blackboard();
		add(blackboard);
		blackboard.init(size);
		legende = new Legende(size);
		add(legende);

		lblFitness = new JLabel("0.0");
		lblFitness.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFitness.setBounds(0, 10, getWidth(), 20);
		add(lblFitness);
		legende.init(new Dimension(60, size.height - Parameter.blackboardHeight * 2));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

		for (int x = 0; x < Parameter.numCols + 1; x++) {
			g.drawLine(Parameter.offsetX + x * Parameter.widthFactor, Parameter.offsetY, Parameter.offsetX + x
					* Parameter.widthFactor, Parameter.offsetY + Parameter.maxHeight);
		}
		for (int y = 0; y < Parameter.numRows + 2; y++) {
			g.drawLine(Parameter.offsetX, Parameter.offsetY + y * Parameter.heightFactor, Parameter.offsetX + Parameter.maxWidth,
					Parameter.offsetY + y * Parameter.heightFactor);
		}
		if (selectionLine != null) {
			g.setColor(selectionRelationValue < 0 ? Color.red : Color.black);
			g.fillOval(selectionLine.xpoints[0] - 5, selectionLine.ypoints[0] - 5, 10, 10);
			g.drawLine(selectionLine.xpoints[0], selectionLine.ypoints[0], selectionLine.xpoints[1], selectionLine.ypoints[1]);
			if (selectionRelationValue >= 0) {
				int x = selectionLine.xpoints[0] - (selectionLine.xpoints[0] - selectionLine.xpoints[1]) / 2;
				int y = selectionLine.ypoints[0] - (selectionLine.ypoints[0] - selectionLine.ypoints[1]) / 2;
				g.setFont(new Font("Arial", Font.BOLD, 16));
				g.drawString("" + selectionRelationValue, x, y);
			}
		}
	}

	public void setModel(GeneString classModel) {
		clear();

		for (Student student : classModel.getStudents()) {
			Table table = new Table(student, this);
			if (student.position == null) {
				continue;
			}
			tables.add(table);
			add(table);
			table.setLocation(Table.coordsToPoint(student.position));
		}
		lblFitness.setText("" + classModel.getFitness());
		validate();
		setFocusable(true);
		requestFocusInWindow();
	}

	public void clear() {
		for (TableBase table : tables) {
			remove(table);
		}
		tables.clear();
		validate();
		repaint();
	}

	public void hideRelations() {
		for (TableBase table : tables) {
			table.setBackground(Table.DEFAULT_BACKGROUND_COLOR);
		}
		setRelationLine(null, null, -1);
	}

	public void showRelationsFor(Student student) {
		for (Table table : tables) {
			if (table.student == student) {
				continue;
			}
			table.setColorForRelationTo(student);
		}
		Point bbPos = new Point(blackboard.getLocationOnScreen());
		Point tablePos = Table.coordsToPoint(student.position);
		tablePos.translate(getLocationOnScreen().x, getLocationOnScreen().y);
		setRelationLine(tablePos, bbPos, (int) (student.getFirstRowFactor() * 10));
	}

	public void setRelationLine(Point source, Point target, int relationValue) {
		if (source == null || target == null) {
			selectionLine = null;
			legende.hideValueMarker();
		} else {
			source.translate(Parameter.tableWidth / 2, Parameter.tableHeight / 2);
			target.translate(Parameter.tableWidth / 2, Parameter.tableHeight / 2);
			source.translate(-getLocationOnScreen().x, -getLocationOnScreen().y);
			target.translate(-getLocationOnScreen().x, -getLocationOnScreen().y);

			selectionLine = new Polygon();
			selectionLine.addPoint(source.x, source.y);
			selectionLine.addPoint(target.x, target.y);
			selectionRelationValue = relationValue;
			legende.setValue(relationValue * 10);
		}
		repaint();
	}

	public void cancelEdit() {
		for (Table tab : tables) {
			tab.cancelRename();
		}

		blackboard.highlight(false);
		TableEventListener.reset();
		selectionLine = null;
		legende.hideValueMarker();
		validate();
		repaint();
	}
}
