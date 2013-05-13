package net.sprauer.sitzplaner.view;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.view.TableEventListener.State;
import net.sprauer.sitzplaner.view.helper.Parameter;

public class ClassRoomEventListener extends ComponentAdapter implements MouseWheelListener, KeyListener, MouseListener {

	private final ClassRoom classRoom;

	public ClassRoomEventListener(ClassRoom classRoom) {
		this.classRoom = classRoom;
		this.classRoom.addComponentListener(this);
		this.classRoom.addMouseWheelListener(this);
		this.classRoom.addKeyListener(this);
		this.classRoom.addMouseListener(this);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		Parameter.offsetX = (classRoom.getWidth() - Parameter.maxWidth) / 2 + Parameter.legendWidth / 2;
		Parameter.offsetY = (classRoom.getHeight() - Parameter.maxHeight) / 2;
		classRoom.blackboard.init(classRoom.getSize());
		classRoom.legende.init(classRoom.getSize());
		classRoom.btnWorse.setLocation(Parameter.offsetX - classRoom.btnWorse.getWidth() - 2, classRoom.getSize().height
				- classRoom.btnWorse.getHeight() - 2);
		classRoom.btnBetter.setLocation(Parameter.offsetX + classRoom.blackboard.getWidth() + 2, classRoom.getSize().height
				- classRoom.btnBetter.getHeight() - 2);

		classRoom.validate();
		classRoom.updateTablePositions();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (TableEventListener.state == TableEventListener.State.EditRelationToStudent) {
			int relation = getRelationSourceToTarget();
			relation -= e.getWheelRotation();
			relation = Math.max(-5, Math.min(relation, 5));
			setRelationTo(relation);
		} else if (TableEventListener.state == TableEventListener.State.EditPriority) {
			int prio = DataBase.instance().getPriority(TableEventListener.source.student());
			prio -= e.getWheelRotation();
			prio = Math.max(0, Math.min(prio, 5));
			setPrioTo(prio);
		}
	}

	private int getRelationSourceToTarget() {
		return DataBase.instance().relationBetween(TableEventListener.source.student(), TableEventListener.target.student());
	}

	private void setRelationTo(int relation) {
		TableEventListener.setRelationTo(relation);
		classRoom.setRelationLine(TableEventListener.source.getLocationOnScreen(),
				TableEventListener.target.getLocationOnScreen(), relation);
	}

	private void setPrioTo(int prio) {
		DataBase.instance().setPriority(TableEventListener.source.student(), prio);
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
