package net.sprauer.sitzplaner.EA;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.List;

import net.sprauer.sitzplaner.EA.Chromosome.StudentPosition;
import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.view.helper.Parameter;

import org.junit.Before;
import org.junit.Test;

public class ChromosomeTest {

	private Chromosome chrom;

	@Before
	public void setup() throws Exception {
		DataBase.instance().setSize(20);
		chrom = EAFactory.greedy(ConfigManager.instance().getCurrentConfig());
	}

	@Test
	public void testFitness() {
		assertEquals(0, chrom.getFitness(), 0.01);
	}

	@Test
	public void testPositions() {
		final Point pos = new Point(1, 3);
		final int studentID = 10;
		chrom.setPositionOf(studentID, pos);

		assertEquals(pos, chrom.getPositionOf(studentID));
	}

	@Test
	public void testCompare() {
		ConfigManager.instance().getCurrentConfig().setDescendantsUsingSwap(0);
		List<Chromosome> children = chrom.swap();

		assertEquals(0, children.size());
	}

	@Test
	public void testStudentAt() {
		final Point pos = new Point(1, 3);
		final int studentID = 10;
		chrom.setPositionOf(studentID, pos);

		assertEquals(studentID, chrom.studentAt(pos));
	}

	@Test
	public void testSwap() {
		ConfigManager.instance().getCurrentConfig().setNumberOfSwaps(1);
		ConfigManager.instance().getCurrentConfig().setDescendantsUsingSwap(1);
		List<Chromosome> children = chrom.swap();

		assertEquals(1, children.size());
		Chromosome child = children.get(0);
		int numberOfChangedPositions = numberOfChanges(child);
		assertTrue("Too much or too less positions have changed. Expected 0 or 2", numberOfChangedPositions == 2
				|| numberOfChangedPositions == 0);
	}

	@Test
	public void testInversion() {
		ConfigManager.instance().getCurrentConfig().setNumberOfInversions(1);
		ConfigManager.instance().getCurrentConfig().setDescendentsUsingInversion(1);

		chrom.dump();
		List<Chromosome> children = chrom.invert();
		assertEquals(1, children.size());

		for (Chromosome child : children) {
			child.dump();
			sanityCheck(child);
			int changedRow = findChangedRow(child);
			if (changedRow == -1) {
				System.out.println("No/empty row changed!");
				continue; // no row changed
			}
			System.out.println("changed row " + changedRow);
			for (int x1 = 0, x2 = Parameter.numCols - 1; x1 < Parameter.numCols / 2; x1++, x2--) {
				final Point position = new Point(x1, changedRow);
				StudentPosition stud1 = child.positionMap.get(position);
				StudentPosition stud2 = chrom.positionMap.get(new Point(x2, changedRow));
				assertEquals(stud1.studentIndex, stud2.studentIndex);
				assertEquals(position, stud1.position);
				assertSame(child.byIndex.get(stud1.studentIndex), stud1);
			}
		}
	}

	private int findChangedRow(Chromosome child) {
		int changedRow = -1;
		for (int y = 0; y < Parameter.numRows; y++) {
			final Point pos = new Point(0, y);
			if ((child.positionMap.get(pos) != null && chrom.positionMap.get(pos) != null && child.positionMap.get(pos).studentIndex != chrom.positionMap
					.get(pos).studentIndex)
					|| (child.positionMap.get(pos) == null && chrom.positionMap.get(pos) != null)
					|| (chrom.positionMap.get(pos) == null && child.positionMap.get(pos) != null)) {
				changedRow = y;
				break;
			}
		}
		return changedRow;
	}

	private void sanityCheck(Chromosome chromosome) {
		for (int i = 0; i < DataBase.instance().getSize(); i++) {
			final StudentPosition stud = chromosome.byIndex.get(i);
			assertEquals(i, stud.studentIndex);
			assertSame(stud, chromosome.positionMap.get(stud.position));
		}
	}

	@Test
	public void testMoreDescendentsSwap() {
		final int numberOfDescendents = 10;
		ConfigManager.instance().getCurrentConfig().setNumberOfSwaps(1);
		ConfigManager.instance().getCurrentConfig().setDescendantsUsingSwap(numberOfDescendents);
		List<Chromosome> children = chrom.swap();

		assertEquals(numberOfDescendents, children.size());
		for (Chromosome child : children) {
			sanityCheck(child);
			int numberOfChangedPositions = numberOfChanges(child);
			if (numberOfChangedPositions > 2) {
				child.dump();
			}
			assertTrue("Too much or too less positions have changed. Expected 0 or 2, Actual " + numberOfChangedPositions,
					numberOfChangedPositions == 2 || numberOfChangedPositions == 0);
		}

	}

	private int numberOfChanges(Chromosome child) {
		int numberOfChangedPositions = 0;
		for (int y = Parameter.numRows - 1, i = 0; y > 0 && i < DataBase.instance().getSize(); y--) {
			for (int x = 0; x < Parameter.numCols && i < DataBase.instance().getSize(); x++, i++) {
				final Point pos = new Point(x, y);
				final int studentAt = child.studentAt(pos);
				if (studentAt != i) {
					numberOfChangedPositions++;
				}
			}
		}
		return numberOfChangedPositions;
	}
}
