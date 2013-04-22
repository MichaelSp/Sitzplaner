package net.sprauer.sitzplaner.EA;

import static org.junit.Assert.assertEquals;
import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.model.Generation;

import org.junit.Before;
import org.junit.Test;

public class GenerationTest {

	private Chromosome chrom;

	@Before
	public void setup() throws Exception {
		DataBase.instance().setSize(20);
		chrom = EAFactory.greedy();
	}

	@Test
	public void testGeneration() {
		Generation gen = new Generation(chrom, 0);
		assertEquals(gen.getBestSolution(), chrom);
	}

}
