package net.sprauer.sitzplaner.view;

import java.awt.BorderLayout;
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

	public TableBase(int studentIdx, ClassRoom classRoom) {
		this.studentIdx = studentIdx;
		this.classRoom = classRoom;

		lblStudent = new JLabel(DataBase.instance().getName(studentIdx));
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
		setLayout(new FlowLayout());
		add(lblStudent);
		validate();
	}

	protected void applyStudentName() {
		DataBase.instance().setName(studentIdx, nameInput.getText());
		lblStudent.setText(DataBase.instance().getName(studentIdx));
		cancelRename();

	}

	public void rename() {
		remove(lblStudent);
		setLayout(new BorderLayout());
		add(nameInput, BorderLayout.NORTH);
		nameInput.setText(DataBase.instance().getName(studentIdx));
		nameInput.setSize(new Dimension(getParent().getWidth(), nameInput.getHeight()));
		nameInput.selectAll();
		nameInput.setVisible(true);
		nameInput.requestFocus();
		validate();
	}

	public int student() {
		return studentIdx;
	}

	public void updateName() {
		lblStudent.setText(DataBase.instance().getName(studentIdx));
	}

}