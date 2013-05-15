package net.sprauer.sitzplaner.EA;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sprauer.sitzplaner.EA.operations.OperationFitness;
import net.sprauer.sitzplaner.EA.operations.OperationGreedy;
import net.sprauer.sitzplaner.model.Generation;
import net.sprauer.sitzplaner.view.ClassRoom;
import net.sprauer.sitzplaner.view.panels.StatisticsPanel;
import net.sprauer.sitzplaner.view.panels.ToolsPanel;

public class EAFactory {

	private static int numOfGenerations = 1;
	static Map<Configuration, Generation> generationsPool = new HashMap<Configuration, Generation>();
	static boolean calculationActive = false;
	static boolean stopRequested = false;

	static double fitness(Chromosome gene) throws Exception {
		new OperationFitness().invoke(gene);
		return gene.getFitness();
	}

	public static Chromosome greedy(Configuration conf) throws Exception {
		Chromosome chrome = new Chromosome(conf);
		new OperationGreedy().invoke(chrome);
		return chrome;
	}

	public static void nextGeneration(ExecutorService exec) throws Exception {
		for (final Configuration conf : ConfigManager.instance()) {
			exec.execute(new Runnable() {

				@Override
				public void run() {
					Generation generation = generationsPool.get(conf);
					if (generation == null) {
						try {
							generation = new Generation(conf);
						} catch (Exception e) {
							e.printStackTrace();
						}
						generationsPool.put(conf, generation);
					}
					generation.evolve();
					StatisticsPanel.instance().addFitness(conf, generation.getBestSolution().getFitness(),
							generation.getWorstSolution().getFitness());
				}
			});

		}
	}

	public static void nextGenerations() throws Exception {
		calculationActive = true;
		ExecutorService exec = Executors.newFixedThreadPool(ConfigManager.instance().getSize() * 10);
		for (int i = 0; i < numOfGenerations; i++) {
			nextGeneration(exec);

			if (stopRequested) {
				stopRequested = false;
				ToolsPanel.instance().setProgress(i, i);
				break;
			} else {
				ToolsPanel.instance().setProgress(i, numOfGenerations);
			}
		}
		showChromosomeForCurrentConfig();
		calculationActive = false;
	}

	public static void showChromosomeForCurrentConfig() {
		final Configuration currentConfig = ConfigManager.instance().getCurrentConfig();
		if (generationsPool.containsKey(currentConfig)) {
			ClassRoom.instance().setGeneration(generationsPool.get(currentConfig));
		}
	}

	public static void resetGeneration() {
		generationsPool = new HashMap<Configuration, Generation>();
		StatisticsPanel.instance().clear();
	}

	public static void setNumberOfGenerations(int numOfGenerations) {
		EAFactory.numOfGenerations = numOfGenerations;

	}

	public static boolean isCalculating() {
		return calculationActive;
	}

	public static void stop() {
		stopRequested = true;
	}
}
