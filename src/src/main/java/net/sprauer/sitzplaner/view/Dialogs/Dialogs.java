package net.sprauer.sitzplaner.view.Dialogs;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.sprauer.sitzplaner.view.helper.Parameter;

public class Dialogs {

	public static int getClassSize() {
		JPanel cmp = createSpinnerPanel();
		JSpinner spinner = createSpinner();
		cmp.add(spinner, BorderLayout.SOUTH);
		((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().selectAll();
		spinner.requestFocus();
		int option = JOptionPane.showOptionDialog(null, spinner, "Klassengröße eingeben", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (option == JOptionPane.CANCEL_OPTION) {
			return -1;
		}

		return (Integer) spinner.getValue();
	}

	private static JPanel createSpinnerPanel() {
		JPanel cmp = new JPanel();
		cmp.setLayout(new BorderLayout());
		cmp.add(new JLabel("Klassengröße eingeben:"), BorderLayout.NORTH);
		return cmp;
	}

	private static JSpinner createSpinner() {
		int num = Math.min(Parameter.numCols * Parameter.numRows, 20);
		SpinnerNumberModel sModel = new SpinnerNumberModel(num, 1, Parameter.numCols * Parameter.numRows, 1);
		JSpinner spinner = new JSpinner(sModel);
		return spinner;
	}

}