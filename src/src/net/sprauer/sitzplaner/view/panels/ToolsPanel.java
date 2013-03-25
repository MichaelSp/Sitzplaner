package net.sprauer.sitzplaner.view.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sprauer.sitzplaner.view.ClassRoom;
import net.sprauer.sitzplaner.view.Commands.Factory;
import net.sprauer.sitzplaner.view.helper.Parameter;

public class ToolsPanel extends JPanel {

	private static final long serialVersionUID = -5782625606599426122L;
	private final JButton btnNewClass;
	private final JButton btnSaveClass;
	private final JButton btnLoadClass;
	private final JButton btnNewSeatingPlan;
	private final JSpinner numCols;
	private final JSpinner numRows;
	private final JSeparator separator;
	private static ToolsPanel _instance;

	public ToolsPanel() {
		_instance = this;
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Class room size", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 30, 50, 29 };
		gbl_panel.rowHeights = new int[] { 20, 20, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel label = new JLabel("Rows:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.ipady = 3;
		gbc_label.ipadx = 3;
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(10, 10, 10, 10);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);

		numRows = new JSpinner();
		ChangeListener classDimensionChangeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Dimension dim = new Dimension((Short) numCols.getValue(), (Short) numRows.getValue());
				ClassRoom.instance().setNewDimensions(dim);
			}
		};
		SpinnerNumberModel rowSpinnerModel = new SpinnerNumberModel(new Short((short) Parameter.numRows), new Short((short) 1),
				new Short((short) 20), new Short((short) 1));
		rowSpinnerModel.addChangeListener(classDimensionChangeListener);
		numRows.setModel(rowSpinnerModel);
		GridBagConstraints gbc_numRows = new GridBagConstraints();
		gbc_numRows.weightx = 10.0;
		gbc_numRows.fill = GridBagConstraints.HORIZONTAL;
		gbc_numRows.gridx = 1;
		gbc_numRows.gridy = 0;
		panel.add(numRows, gbc_numRows);

		JLabel label_1 = new JLabel("Columns:");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.insets = new Insets(10, 10, 10, 10);
		gbc_label_1.ipady = 3;
		gbc_label_1.ipadx = 3;
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 1;
		panel.add(label_1, gbc_label_1);

		numCols = new JSpinner();
		SpinnerNumberModel colSpinnerModel = new SpinnerNumberModel(new Short((short) Parameter.numCols), new Short((short) 1),
				new Short((short) 20), new Short((short) 1));
		numCols.setModel(colSpinnerModel);
		colSpinnerModel.addChangeListener(classDimensionChangeListener);
		GridBagConstraints gbc_numCols = new GridBagConstraints();
		gbc_numCols.fill = GridBagConstraints.HORIZONTAL;
		gbc_numCols.gridx = 1;
		gbc_numCols.gridy = 1;
		panel.add(numCols, gbc_numCols);

		btnLoadClass = new JButton();
		add(btnLoadClass);

		btnSaveClass = new JButton();
		add(btnSaveClass);

		btnNewClass = new JButton();
		add(btnNewClass);

		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		add(separator);

		btnNewSeatingPlan = new JButton();
		add(btnNewSeatingPlan);

	}

	public void initView() {
		btnNewSeatingPlan.setAction(Factory.CommandNewSeatingPlan);
		btnLoadClass.setAction(Factory.CommandLoadClass);
		btnSaveClass.setAction(Factory.CommandSaveClass);
		btnNewClass.setAction(Factory.CommandNewClass);

	}

	public static ToolsPanel instance() {
		return _instance;
	}

	public void setClassRoomDimensions(Dimension dim) {
		numCols.setValue((short) dim.getWidth());
		numRows.setValue((short) dim.getHeight());
	}

}
