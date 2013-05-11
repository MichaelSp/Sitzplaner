package net.sprauer.sitzplaner.EA;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.utils.DeepCopy;
import net.sprauer.sitzplaner.view.helper.Parameter;

public class Chromosome implements Comparable<Chromosome>, Comparator<Chromosome>, Serializable {

	private static final long serialVersionUID = -3578751251444286705L;
	private double fitness = -1;
	Configuration configuration;
	private final Vector<StudentPosition> byIndex = new Vector<StudentPosition>();
	private final HashMap<Point, StudentPosition> positionMap = new HashMap<Point, StudentPosition>();
	private boolean calculating;
	public String id = UUID.randomUUID().toString();

	private class StudentPosition implements Serializable {
		private static final long serialVersionUID = 7570524909720881283L;

		public StudentPosition(int i) {
			studentIndex = i;
		}

		int studentIndex;
		Point position = new Point();

		@Override
		public String toString() {
			return studentIndex + "@(" + position.x + "|" + position.y + ")";
		}
	}

	public Chromosome(Configuration conf) {
		configuration = conf;
		byIndex.setSize(DataBase.instance().getSize());
		for (int i = 0; i < size(); i++) {
			byIndex.set(i, new StudentPosition(i));
		}
	}

	public int size() {
		return byIndex.size();
	}

	public double getFitness() {
		if (fitness < 0 && !calculating) {
			try {
				calculating = true;
				fitness = EAFactory.fitness(this);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				calculating = false;
			}
		}
		return fitness;
	}

	public Point getPositionOf(int index) {
		return byIndex.get(index).position;
	}

	public void setPositionOf(int i, Point newPos) {
		byIndex.get(i).position = newPos;
		positionMap.put(newPos, byIndex.get(i));
	}

	@Override
	public int compare(Chromosome o1, Chromosome o2) {
		return Double.compare(o1.fitness, o2.fitness);
	}

	public void setFitness(double f) {
		fitness = f;
	}

	public int studentAt(Point pos) {
		if (positionMap.containsKey(pos)) {
			return positionMap.get(pos).studentIndex;
		} else {
			return -1;
		}
	}

	@Override
	public String toString() {
		return id + "(" + fitness + ")";
	}

	public List<Chromosome> mutate() {
		List<Chromosome> children = swap();
		children.addAll(invert());
		return children;
	}

	public List<Chromosome> swap() {
		List<Chromosome> swapped = new ArrayList<Chromosome>();
		for (int i = 0; i < configuration.getDescendantsUsingSwap(); i++) {
			Chromosome child = doClone().doSwap(configuration.getNumberOfSwaps());
			swapped.add(child);
		}
		return swapped;
	}

	public List<Chromosome> invert() {
		List<Chromosome> swapped = new ArrayList<Chromosome>();
		for (int i = 0; i < configuration.getDescendantsUsingInversion(); i++) {
			Chromosome child = doClone().doInvert(configuration.getNumberOfInversions());
			swapped.add(child);
		}
		return swapped;
	}

	private Chromosome doInvert(int times) {
		for (int k = 0; k < times; k++) {
			int row = (int) (Math.random() * Parameter.numRows);
			for (int col1 = 0, col2 = Parameter.numCols - 1; col1 < Parameter.numCols / 2; col1++, col2--) {
				final Point posI = new Point(col1, row);
				final Point posJ = new Point(col2, row);

				if (positionMap.containsKey(posI) && positionMap.containsKey(posJ)) {
					int i = positionMap.get(posI).studentIndex;
					int j = positionMap.get(posJ).studentIndex;
					doSwap(i, j);
				} else { // swap if one of the seats is empty
					if (positionMap.containsKey(posJ)) {
						final StudentPosition studJ = positionMap.remove(posJ);
						if (DataBase.instance().getStudent(studJ.studentIndex).isLocked()) {
							continue;
						}
						positionMap.put(posI, studJ);
						studJ.position = posI;
						byIndex.set(studJ.studentIndex, studJ);
					} else if (positionMap.containsKey(posI)) {
						final StudentPosition studI = positionMap.remove(posI);
						if (DataBase.instance().getStudent(studI.studentIndex).isLocked()) {
							continue;
						}
						positionMap.put(posJ, studI);
						studI.position = posJ;
						byIndex.set(studI.studentIndex, studI);
					}
				}
			}
		}
		return this;
	}

	private Chromosome doSwap(int times) {
		for (int y = 0; y < times; y++) {
			int i = (int) (Math.random() * byIndex.size());
			int j = (int) (Math.random() * byIndex.size());
			doSwap(i, j);
		}
		return this;
	}

	private void doSwap(int i, int j) {
		if (i == j) {
			return;
		}
		if (DataBase.instance().getStudent(i).isLocked() || DataBase.instance().getStudent(j).isLocked()) {
			return;
		}
		// System.out.println("Do SWAP " + this + " : " + byIndex.get(i) + "==>"
		// + byIndex.get(j));

		final int studI = byIndex.get(i).studentIndex;
		byIndex.get(i).studentIndex = j;
		byIndex.get(j).studentIndex = studI;
		Collections.swap(byIndex, i, j);
	}

	public Chromosome doClone() {
		Chromosome chrome;
		chrome = (Chromosome) DeepCopy.copy(this);
		chrome.configuration = configuration;
		chrome.id = UUID.randomUUID().toString();
		chrome.fitness = -1;
		return chrome;
	}

	public Configuration getConfig() {
		return configuration;
	}

	@Override
	public int compareTo(Chromosome o) {
		return compare(o, this);
	}

	public void dump() {
		System.out.println("----------------------------(" + Parameter.numCols + "*" + Parameter.numRows + ")");
		for (int y = 0; y < Parameter.numRows; y++) {
			for (int x = 0; x < Parameter.numCols; x++) {
				final Point pos = new Point(x, y);
				final StudentPosition studentPosition = positionMap.get(pos);
				if (studentPosition == null) {
					System.out.print("|  ");
					continue;
				}
				if (!studentPosition.position.equals(pos)) {
					System.out.print("|X ");
				} else {
					System.out.print("|" + String.format("%02d", studentPosition.studentIndex));
				}
			}
			System.out.println("|");
		}
	}
}
