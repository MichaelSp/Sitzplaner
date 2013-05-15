package net.sprauer.sitzplaner.EA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
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
	private static ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	static double fitness(Chromosome gene) throws Exception {
		new OperationFitness().invoke(gene);
		return gene.getFitness();
	}

	public static Chromosome greedy(Configuration conf) throws Exception {
		Chromosome chrome = new Chromosome(conf);
		new OperationGreedy().invoke(chrome);
		return chrome;
	}

	public static void nextGeneration(List<Callable<Object>> todo) throws Exception {
		for (final Configuration conf : ConfigManager.instance()) {
			todo.add(Executors.callable(new Runnable() {

				@Override
				public void run() {
					Generation generation = generationsPool.get(conf);
					if (generation == null) {
						try {
							generation = new Generation(conf);
							generationsPool.put(conf, generation);
						} catch (Exception e) {
							e.printStackTrace();
							return;
						}
					}
					generation.evolve();
					StatisticsPanel.instance().addFitness(conf, generation.getBestSolution().getFitness(),
							generation.getWorstSolution().getFitness());
				}
			}));

		}
	}

	public static void nextGenerations() throws Exception {
		try {
			calculationActive = true;
			ToolsPanel.instance().setCalculationActive(true);
			List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();

			for (int i = 0; i < numOfGenerations; i++) {
				nextGeneration(tasks);
				ToolsPanel.instance().setProgress(i, numOfGenerations);
			}
			exec.invokeAll(tasks);
		} finally {
			showChromosomeForCurrentConfig();
			stop();
		}
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
		exec.shutdownNow();
		ToolsPanel.instance().setProgress(1, 1);
		calculationActive = false;
		ToolsPanel.instance().setCalculationActive(false);
		exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}
}
