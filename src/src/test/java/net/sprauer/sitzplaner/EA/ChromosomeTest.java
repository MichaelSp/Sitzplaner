package net.sprauer.sitzplaner.EA;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.List;

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
		assertTrue("Too much or too less positions have changed. Expected 0 or 2", numberOfChangedPositions == 2
				|| numberOfChangedPositions == 0);
	}
}
