package net.sprauer.sitzplaner.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
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
	protected final JCheckBox chkLocked = new JCheckBox();

	public TableBase(int studentIdx, ClassRoom classRoom) {
		this.studentIdx = studentIdx;
		this.classRoom = classRoom;
		setLayout(new BorderLayout(0, 0));

		lblStudent = new JLabel(DataBase.instance().getName(studentIdx));
		add(lblStudent, BorderLayout.CENTER);
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

		chkLocked.getModel().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean locked = chkLocked.isSelected();
				TableBase.this.classRoom.lockStudent(TableBase.this.studentIdx, locked);
			}
		});
		add(chkLocked, BorderLayout.EAST);
	}

	protected void cancelRename() {
		TableEventListener.state = State.Nothing;
		nameInput.setVisible(false);
		remove(nameInput);
		setLayout(new FlowLayout());
		add(lblStudent, BorderLayout.CENTER);
		add(chkLocked, BorderLayout.EAST);
		validate();
	}

	protected void applyStudentName() {
		DataBase.instance().setName(studentIdx, nameInput.getText());
		lblStudent.setText(DataBase.instance().getName(studentIdx));
		cancelRename();
	}

	public void rename() {
		remove(lblStudent);
		remove(chkLocked);
		setLayout(new BorderLayout());
		add(nameInput, BorderLayout.NORTH);
		nameInput.setText(DataBase.instance().getName(studentIdx));
		nameInput.setSize(new Dimension(getParent().getWidth(), nameInput.getHeight()));
		nameInput.selectAll();
		nameInput.setVisible(true);
		nameInput.requestFocus();
		validate();
	}

	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if (chkLocked != null) {
			chkLocked.setBackground(bg);
		}
	}

	public int student() {
		return studentIdx;
	}

	public void updateName() {
		lblStudent.setText(DataBase.instance().getName(studentIdx));
	}

}