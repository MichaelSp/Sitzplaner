package net.sprauer.sitzplaner.view.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sprauer.sitzplaner.EA.Configuration;

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
	private final Map<Configuration, XYSeries> seriesMap = new HashMap<Configuration, XYSeries>();
	private final JTextField fitnessValue;
	private final XYSeriesCollection seriesCollection;

	public StatisticsPanel() {
		_instance = this;
		seriesCollection = new XYSeriesCollection();
		chart = createChart(seriesCollection);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 78, 0, 73, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 218, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblCurrentFitness = new JLabel("Current Fitness:");
		GridBagConstraints gbc_lblCurrentFitness = new GridBagConstraints();
		gbc_lblCurrentFitness.ipady = 5;
		gbc_lblCurrentFitness.ipadx = 5;
		gbc_lblCurrentFitness.anchor = GridBagConstraints.EAST;
		gbc_lblCurrentFitness.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentFitness.gridx = 1;
		gbc_lblCurrentFitness.gridy = 0;
		add(lblCurrentFitness, gbc_lblCurrentFitness);

		fitnessValue = new JTextField();
		fitnessValue.setEditable(false);
		fitnessValue.setText("0.0");
		GridBagConstraints gbc_fitnessValue = new GridBagConstraints();
		gbc_fitnessValue.ipady = 5;
		gbc_fitnessValue.ipadx = 5;
		gbc_fitnessValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_fitnessValue.insets = new Insets(0, 0, 5, 5);
		gbc_fitnessValue.gridx = 2;
		gbc_fitnessValue.gridy = 0;
		add(fitnessValue, gbc_fitnessValue);
		fitnessValue.setColumns(10);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(800, 200));
		GridBagConstraints gbc_chartPanel = new GridBagConstraints();
		gbc_chartPanel.weighty = 1.0;
		gbc_chartPanel.weightx = 1.0;
		gbc_chartPanel.fill = GridBagConstraints.BOTH;
		gbc_chartPanel.insets = new Insets(0, 0, 0, 5);
		gbc_chartPanel.gridwidth = 4;
		gbc_chartPanel.gridx = 0;
		gbc_chartPanel.gridy = 1;
		add(chartPanel, gbc_chartPanel);
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

	public void addFitness(Configuration conf, double best) {
		fitnessValue.setText("" + best);
		XYSeries series = seriesMap.get(conf);
		if (series == null) {
			int index = seriesMap.size();
			series = new XYSeries("Fitness " + (index + 1));
			seriesCollection.addSeries(series);
			seriesMap.put(conf, series);
			chart.getXYPlot().getRenderer().setSeriesPaint(index, conf.getColor());
		}
		if (series.getItemCount() == 0) {
			series.add(1, 0);
		} else {
			series.add(series.getMaxX() + 1, best);
		}
	}

	public void clear() {
		seriesMap.clear();
		seriesCollection.removeAllSeries();
		fitnessValue.setText("0.0");
		validate();
	}

	public void setCurrentFitness(double fitness) {
		fitnessValue.setText("" + fitness);
	}
}
