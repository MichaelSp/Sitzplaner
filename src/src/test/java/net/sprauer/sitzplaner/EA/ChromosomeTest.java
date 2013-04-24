package net.sprauer.sitzplaner.EA;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Point;

import net.sprauer.sitzplaner.model.DataBase;

import org.junit.Before;
import org.junit.Test;

public class ChromosomeTest {

	private Chromosome chrom;

	@Before
	public void setup() throws Exception {
		DataBase.instance().setSize(20);
		chrom = EAFactory.greedy();
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
		Chromosome newChrom = chrom.swap(0);

		assertNotEquals(newChrom.id, chrom.id);
		assertEquals(0, chrom.compare(newChrom, chrom));
		assertEquals(chrom.getFitness(), chrom.getFitness(), 0.0001);
	}

	@Test
	public void testStudentAt() {
		final Point pos = new Point(1, 3);
		final int studentID = 10;
		chrom.setPositionOf(studentID, pos);

		assertEquals(studentID, chrom.studentAt(pos));
	}

	@Test
	public void testGenerations() throws Exception {
		assertEquals(null, EAFactory.currentGeneration);
		EAFactory.nextGenerations();
		assertNotNull(EAFactory.currentGeneration);
		EAFactory.resetGeneration();
		assertEquals(null, EAFactory.currentGeneration);
	}
}
