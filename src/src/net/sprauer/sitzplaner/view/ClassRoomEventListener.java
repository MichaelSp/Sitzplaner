package net.sprauer.sitzplaner.view;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import net.sprauer.sitzplaner.view.TableEventListener.State;

public class ClassRoomEventListener extends ComponentAdapter implements MouseWheelListener, KeyListener, MouseListener {

	private final ClassRoom classRoom;

	public ClassRoomEventListener(ClassRoom classRoom) {
		this.classRoom = classRoom;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		Parameter.numCols = classRoom.getWidth() / (Parameter.tableWidth + Parameter.spacing * 2);
		Parameter.numRows = (classRoom.getHeight() - Parameter.blackboardHeight)
				/ (Parameter.tableHeight + Parameter.spacing * 2);

		Parameter.widthFactor = Parameter.tableWidth + Parameter.spacing / 2;
		Parameter.heightFactor = Parameter.tableHeight + Parameter.spacing / 2;
		Parameter.maxWidth = (Parameter.numCols) * Parameter.widthFactor;
		Parameter.maxHeight = (Parameter.numRows) * Parameter.heightFactor;
		Parameter.offsetX = (classRoom.getWidth() - Parameter.maxWidth) / 2 + Parameter.legendWidth / 2;
		Parameter.offsetY = (classRoom.getHeight() - Parameter.maxHeight) / 2;
		classRoom.clear();
		classRoom.blackboard.init(classRoom.getSize());
		classRoom.legende.init(classRoom.getSize());
		classRoom.validate();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (TableEventListener.state == TableEventListener.State.EditRelationToStudent) {
			int relation = (int) (TableEventListener.source.student.relationTo(TableEventListener.target.student) * 10);
			relation -= e.getWheelRotation();
			relation = Math.max(0, Math.min(relation, 10));
			setRelationTo(relation);
		} else if (TableEventListener.state == TableEventListener.State.EditPriority) {
			int prio = (int) (TableEventListener.source.student.getFirstRowFactor() * 10);
			prio -= e.getWheelRotation();
			prio = Math.max(0, Math.min(prio, 10));
			setPrioTo(prio);
		}
	}

	private void setRelationTo(int relation) {
		TableEventListener.setRelationTo(relation / 10.0);
		classRoom.setRelationLine(TableEventListener.source.getLocationOnScreen(),
				TableEventListener.target.getLocationOnScreen(), relation);
	}

	private void setPrioTo(int prio) {
		TableEventListener.source.student.setFirstRowFactor(prio / 10.0);
		TableEventListener.source.classRoom.setRelationLine(TableEventListener.source.getLocationOnScreen(),
				TableEventListener.blackboardCenter(), prio);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int num = e.getKeyChar() - KeyEvent.VK_0;
		if (num == 0) {
			num = 10;
		}
		num = (num < 1 || num > 10) ? -1 : num;
		if (num != -1 && TableEventListener.state == State.EditPriority) {
			setPrioTo(num);
		} else if (num != -1 && TableEventListener.state == State.EditRelationToStudent) {
			setRelationTo(num);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		classRoom.cancelEdit();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
