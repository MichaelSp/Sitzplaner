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
		chrom = EAFactory.greedy(ConfigManager.instance().getCurrentConfig());
	}

	@Test
	public void testGeneration() throws Exception {
		Generation gen = new Generation(ConfigManager.instance().getCurrentConfig());
		assertEquals(gen.getBestSolution(), chrom);
	}

}
