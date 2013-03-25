package net.sprauer.sitzplaner.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.view.TableEventListener.State;

public class TableBase extends JPanel {

	private static final long serialVersionUID = 7454235700262659177L;
	protected final int studentIdx;
	protected final ClassRoom classRoom;
	protected final JTextField nameInput = new JTextField();
	protected final JLabel lblStudent;
	protected final JLabel lblHelp = new JLabel("Vorname Nachname");

	public TableBase(int studentIdx, ClassRoom classRoom) {
		this.studentIdx = studentIdx;
		this.classRoom = classRoom;

		lblHelp.setForeground(Color.red);
		lblHelp.setBackground(Color.gray);

		lblStudent = new JLabel(DataBase.getName(studentIdx));
		add(lblStudent);
		nameInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					cancelRename();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					applyStudentName();
				}
			}
		});
		nameInput.setInputVerifier(new InputVerifier() {

			@Override
			public boolean verify(JComponent input) {
				return (((JTextField) input).getText().split(" ").length == 2);
			}
		});
	}

	protected void cancelRename() {
		TableEventListener.state = State.Nothing;
		nameInput.setVisible(false);
		remove(nameInput);
		remove(lblHelp);
		setLayout(new FlowLayout());
		add(lblStudent);
		validate();
	}

	protected void applyStudentName() {
		String[] names = nameInput.getText().split(" ");
		if (names.length == 2) {
			DataBase.setName(studentIdx, names[0], names[1]);
			lblStudent.setText(DataBase.getName(studentIdx));
			cancelRename();
		} else {
			add(lblHelp, BorderLayout.SOUTH);
			validate();
		}
	}

	public void rename() {
		remove(lblStudent);
		setLayout(new BorderLayout());
		add(nameInput, BorderLayout.NORTH);
		nameInput.setText(DataBase.getName(studentIdx));
		nameInput.setSize(new Dimension(getParent().getWidth(), nameInput.getHeight()));
		nameInput.selectAll();
		nameInput.setVisible(true);
		nameInput.requestFocus();
		validate();
	}

	public int student() {
		return studentIdx;
	}

}