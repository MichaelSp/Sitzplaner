package net.sprauer.sitzplaner.view.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.view.ClassRoom;
import net.sprauer.sitzplaner.view.Commands.Factory;
import net.sprauer.sitzplaner.view.helper.Parameter;

public class ToolsPanel extends JPanel {

	private static final long serialVersionUID = -5782625606599426122L;
	private final JButton btnSaveClass;
	private final JButton btnLoadClass;
	private final JButton btnNewSeatingPlan;
	private final JSpinner numCols;
	private final JSpinner numRows;
	private static ToolsPanel _instance;
	private final JPanel pnlLoadAndSave;
	private final JSpinner classSize;
	private boolean blockUpdates = false;
	private JTabbedPane tabbedPane;
	private JLabel lblClassSize;
	private JButton btnCreate;

	public ToolsPanel() {
		_instance = this;
		ChangeListener classDimensionChangeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (blockUpdates) {
					return;
				}
				Dimension dim = new Dimension((Short) numCols.getValue(), (Short) numRows.getValue());
				ClassRoom.instance().setNewDimensions(dim);
				Short maximum = (short) (dim.height * dim.width);
				((SpinnerNumberModel) classSize.getModel()).setMaximum(maximum);
				if ((Short) classSize.getValue() > maximum) {
					classSize.setValue(maximum);
				}
			}
		};

		SpinnerNumberModel rowSpinnerModel = createRowSpinnerModel(classDimensionChangeListener);
		SpinnerNumberModel colSpinnerModel = createColSpinnerModel(classDimensionChangeListener);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 150, 80, 0 };
		gridBagLayout.rowHeights = new int[] { 97, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 0, 5);
		gbc_tabbedPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		add(tabbedPane, gbc_tabbedPane);

		JPanel pnlClassRoomSize = new JPanel();
		tabbedPane.addTab("Create Class", null, pnlClassRoomSize, null);
		GridBagLayout gbl_pnlClassRoomSize = new GridBagLayout();
		gbl_pnlClassRoomSize.columnWidths = new int[] { 30, 50, 154, 50, 29 };
		gbl_pnlClassRoomSize.rowHeights = new int[] { 20, 20, 0 };
		gbl_pnlClassRoomSize.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_pnlClassRoomSize.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		pnlClassRoomSize.setLayout(gbl_pnlClassRoomSize);

		JLabel label = new JLabel("Rows:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.ipady = 3;
		gbc_label.ipadx = 3;
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(10, 10, 10, 10);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		pnlClassRoomSize.add(label, gbc_label);

		numRows = new JSpinner();
		numRows.setModel(rowSpinnerModel);
		GridBagConstraints gbc_numRows = new GridBagConstraints();
		gbc_numRows.insets = new Insets(0, 0, 5, 5);
		gbc_numRows.weightx = 10.0;
		gbc_numRows.fill = GridBagConstraints.HORIZONTAL;
		gbc_numRows.gridx = 1;
		gbc_numRows.gridy = 0;
		pnlClassRoomSize.add(numRows, gbc_numRows);

		lblClassSize = new JLabel("Class size:");
		GridBagConstraints gbc_lblClassSize = new GridBagConstraints();
		gbc_lblClassSize.anchor = GridBagConstraints.EAST;
		gbc_lblClassSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblClassSize.gridx = 2;
		gbc_lblClassSize.gridy = 0;
		pnlClassRoomSize.add(lblClassSize, gbc_lblClassSize);

		classSize = new JSpinner();
		GridBagConstraints gbc_classSize = new GridBagConstraints();
		gbc_classSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_classSize.insets = new Insets(0, 0, 5, 0);
		gbc_classSize.gridx = 3;
		gbc_classSize.gridy = 0;
		pnlClassRoomSize.add(classSize, gbc_classSize);
		classSize.setModel(new SpinnerNumberModel(new Short((short) 1), new Short((short) 1), new Short((short) 1), new Short(
				(short) 1)));
		classSize.setValue(new Short((short) (Parameter.numCols * Parameter.numRows)));
		classSize.getModel().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (blockUpdates) {
					return;
				}
				DataBase.instance().setSize((Short) classSize.getValue());
			}
		});

		JLabel label_1 = new JLabel("Columns:");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(10, 10, 10, 10);
		gbc_label_1.ipady = 3;
		gbc_label_1.ipadx = 3;
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 1;
		pnlClassRoomSize.add(label_1, gbc_label_1);

		numCols = new JSpinner();
		numCols.setModel(colSpinnerModel);
		GridBagConstraints gbc_numCols = new GridBagConstraints();
		gbc_numCols.insets = new Insets(0, 0, 0, 5);
		gbc_numCols.fill = GridBagConstraints.HORIZONTAL;
		gbc_numCols.gridx = 1;
		gbc_numCols.gridy = 1;
		pnlClassRoomSize.add(numCols, gbc_numCols);

		btnCreate = new JButton("Create");
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.gridx = 3;
		gbc_btnCreate.gridy = 1;
		pnlClassRoomSize.add(btnCreate, gbc_btnCreate);

		pnlLoadAndSave = new JPanel();
		tabbedPane.addTab("Load & Save", null, pnlLoadAndSave, null);
		pnlLoadAndSave.setBorder(new TitledBorder(null, "Persistence", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_pnlLoadAndSave = new GridBagLayout();
		gbl_pnlLoadAndSave.columnWidths = new int[] { 50, 0 };
		gbl_pnlLoadAndSave.rowHeights = new int[] { 24, 20, 0 };
		gbl_pnlLoadAndSave.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_pnlLoadAndSave.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		pnlLoadAndSave.setLayout(gbl_pnlLoadAndSave);

		btnLoadClass = new JButton();
		GridBagConstraints gbc_btnLoadClass = new GridBagConstraints();
		gbc_btnLoadClass.anchor = GridBagConstraints.WEST;
		gbc_btnLoadClass.insets = new Insets(0, 0, 5, 0);
		gbc_btnLoadClass.gridx = 0;
		gbc_btnLoadClass.gridy = 0;
		pnlLoadAndSave.add(btnLoadClass, gbc_btnLoadClass);

		btnSaveClass = new JButton();
		GridBagConstraints gbc_btnSaveClass = new GridBagConstraints();
		gbc_btnSaveClass.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSaveClass.gridx = 0;
		gbc_btnSaveClass.gridy = 1;
		pnlLoadAndSave.add(btnSaveClass, gbc_btnSaveClass);

		btnNewSeatingPlan = new JButton();
		GridBagConstraints gbc_btnNewSeatingPlan = new GridBagConstraints();
		gbc_btnNewSeatingPlan.gridx = 1;
		gbc_btnNewSeatingPlan.gridy = 0;
		add(btnNewSeatingPlan, gbc_btnNewSeatingPlan);

	}

	private SpinnerNumberModel createColSpinnerModel(ChangeListener classDimensionChangeListener) {
		SpinnerNumberModel colSpinnerModel = new SpinnerNumberModel(new Short((short) Parameter.numCols), new Short((short) 1),
				new Short((short) 20), new Short((short) 1));
		colSpinnerModel.addChangeListener(classDimensionChangeListener);
		return colSpinnerModel;
	}

	private SpinnerNumberModel createRowSpinnerModel(ChangeListener classDimensionChangeListener) {
		SpinnerNumberModel rowSpinnerModel = new SpinnerNumberModel(new Short((short) Parameter.numRows), new Short((short) 1),
				new Short((short) 20), new Short((short) 1));
		rowSpinnerModel.addChangeListener(classDimensionChangeListener);
		return rowSpinnerModel;
	}

	public void initView() {
		btnNewSeatingPlan.setAction(Factory.CommandNewSeatingPlan);
		btnLoadClass.setAction(Factory.CommandLoadClass);
		btnSaveClass.setAction(Factory.CommandSaveClass);
		btnCreate.setAction(Factory.CommandNewClass);
	}

	public static ToolsPanel instance() {
		return _instance;
	}

	public void setClassRoomDimensions(Dimension dim, short size) {
		blockUpdates = true;
		numCols.setValue((short) dim.getWidth());
		numRows.setValue((short) dim.getHeight());

		Short maximum = (short) (((Short) numRows.getValue()) * (Short) numCols.getValue());
		((SpinnerNumberModel) classSize.getModel()).setMaximum(maximum);
		blockUpdates = false;
		classSize.setValue(size);
	}

	public int getClassSize() {
		return (Short) classSize.getValue();
	}

}
