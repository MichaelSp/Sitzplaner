package net.sprauer.sitzplaner.view.panels;

import java.awt.Color;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class StatisticsPanel extends JPanel {
	private static final long serialVersionUID = -5215412009575339154L;

	private static StatisticsPanel _instance;
	private final JFreeChart chart;
	private final ChartPanel chartPanel;
	private XYSeries series;

	public StatisticsPanel() {
		_instance = this;
		chart = createChart(createDataset());
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(800, 200));
		add(chartPanel);
	}

	public static StatisticsPanel instance() {
		return _instance;
	}

	private JFreeChart createChart(XYSeriesCollection dataset) {
		JFreeChart chart = ChartFactory.createXYLineChart("", // title
				"Generation", // x axis label
				"Fitness", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, false, // include legend
				true, // tooltips
				false // urls
				);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		return chart;

	}

	private XYSeriesCollection createDataset() {
		XYSeriesCollection collection = new XYSeriesCollection();
		series = new XYSeries("Fitness");
		collection.addSeries(series);
		return collection;
	}

	public void addFitness(double fitness) {
		if (series.getItemCount() == 0) {
			series.add(1, 0);
		} else {
			series.add(series.getMaxX() + 1, fitness);
		}
	}

	public void clear() {
		series.clear();
	}
}
