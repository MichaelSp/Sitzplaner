package net.sprauer.sitzplaner.EA;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.List;

import net.sprauer.sitzplaner.model.DataBase;

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

}
