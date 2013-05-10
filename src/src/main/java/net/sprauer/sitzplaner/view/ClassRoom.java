package net.sprauer.sitzplaner.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sprauer.sitzplaner.EA.Chromosome;
import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.view.helper.Parameter;
import net.sprauer.sitzplaner.view.panels.StatisticsPanel;

public class ClassRoom extends JPanel {

	private static final long serialVersionUID = -7794245134688207661L;
	private static ClassRoom _instance;
	private final List<Table> tables = new ArrayList<Table>();
	final Blackboard blackboard;
	private Polygon selectionLine;
	private int selectedRelationValue;
	ClassRoomEventListener eventListener;
	Legende legende = null;
	private final JLabel lblFitness;
	private Chromosome chromosome;

	private ClassRoom() {
		eventListener = new ClassRoomEventListener(this);

		setLayout(null);
		blackboard = new Blackboard();
		legende = new Legende();

		Dimension size = new Dimension(800, 400);
		applySize(size);

		add(blackboard);
		add(legende);

		lblFitness = new JLabel("0.0");
		lblFitness.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFitness.setBounds(0, 10, getWidth(), 20);
		add(lblFitness);
	}

	private void applySize(Dimension size) {
		setPreferredSize(size);
		setMinimumSize(size);
		blackboard.init(size);
		legende.init(new Dimension(60, size.height - Parameter.blackboardHeight * 2));
		if (getParent() != null) {
			((JFrame) getParent().getParent().getParent().getParent().getParent()).pack();
		}
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
		for (int y = 0; y < Parameter.numRows + 1; y++) {
			g.drawLine(Parameter.offsetX, Parameter.offsetY + y * Parameter.heightFactor, Parameter.offsetX + Parameter.maxWidth,
					Parameter.offsetY + y * Parameter.heightFactor);
		}
		if (selectionLine != null) {
			g.setColor(Color.black);
			g.fillOval(selectionLine.xpoints[0] - 5, selectionLine.ypoints[0] - 5, 10, 10);
			g.drawLine(selectionLine.xpoints[0], selectionLine.ypoints[0], selectionLine.xpoints[1], selectionLine.ypoints[1]);

			int x = selectionLine.xpoints[0] - (selectionLine.xpoints[0] - selectionLine.xpoints[1]) / 2;
			int y = selectionLine.ypoints[0] - (selectionLine.ypoints[0] - selectionLine.ypoints[1]) / 2;
			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.drawString("" + selectedRelationValue, x, y);
		}
	}

	public void showChromosome(Chromosome chromosome) {
		clear();
		this.chromosome = chromosome;
		StatisticsPanel.instance().setCurrentFitness(chromosome.getFitness());

		for (int i = 0; i < chromosome.size(); i++) {
			Table table = new Table(i, this);
			tables.add(table);
			add(table);
			table.setPosition(chromosome.getPositionOf(i));
		}
		lblFitness.setText("" + chromosome.getFitness());
		validate();
		setFocusable(true);
		requestFocusInWindow();
	}

	public void clear() {
		chromosome = null;
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

	public void showRelationsFor(int index) {
		TableBase hisTable = null;
		for (Table table : tables) {
			if (table.studentIdx == index) {
				hisTable = table;
				continue;
			}
			table.setColorForRelationTo(index);
		}
		Point bbPos = new Point(blackboard.getLocationOnScreen());
		Point tablePos = hisTable.getLocation();
		tablePos.translate(getLocationOnScreen().x, getLocationOnScreen().y);
		setRelationLine(tablePos, bbPos, DataBase.instance().getPriority(index));
	}

	public void setRelationLine(Point source, Point target, int relationValue) {
		if (source == null || target == null) {
			selectionLine = null;
			legende.hideValueMarker();
		} else {
			source.translate(Parameter.cellWidth / 2, Parameter.cellHeight / 2);
			target.translate(Parameter.cellWidth / 2, Parameter.cellHeight / 2);
			source.translate(-getLocationOnScreen().x, -getLocationOnScreen().y);
			target.translate(-getLocationOnScreen().x, -getLocationOnScreen().y);

			selectionLine = new Polygon();
			selectionLine.addPoint(source.x, source.y);
			selectionLine.addPoint(target.x, target.y);
			selectedRelationValue = relationValue;
			legende.setValue(relationValue);
		}
		repaint();
	}

	public void cancelEdit() {
		for (TableBase tab : tables) {
			tab.cancelRename();
		}

		blackboard.highlight(false);
		TableEventListener.reset();
		selectionLine = null;
		legende.hideValueMarker();
		validate();
		repaint();
	}

	public static ClassRoom instance() {
		if (_instance == null) {
			_instance = new ClassRoom();
		}
		return _instance;
	}

	public void setNewDimensions(Dimension dim) {
		Parameter.numCols = dim.width;
		Parameter.numRows = dim.height;

		clear();
		Parameter.widthFactor = Parameter.cellWidth + Parameter.spacing / 2;
		Parameter.heightFactor = Parameter.cellHeight + Parameter.spacing / 2;
		Parameter.maxWidth = (Parameter.numCols) * Parameter.widthFactor;
		Parameter.maxHeight = (Parameter.numRows) * Parameter.heightFactor;
		Parameter.offsetX = (getWidth() - Parameter.maxWidth) / 2 + Parameter.legendWidth;
		Parameter.offsetY = (getHeight() - Parameter.maxHeight) / 2;

		applySize(getSize());
		validate();
		repaint();
	}

	public Dimension getDimensions() {
		return new Dimension(Parameter.numCols, Parameter.numRows);
	}

	public void updateTablePositions() {
		for (Table table : tables) {
			table.setPosition(chromosome.getPositionOf(table.student()));
		}
		validate();
	}

	public void lockStudent(int studentIdx, boolean locked) {
		chromosome.lockStudent(studentIdx, locked);
	}

}
