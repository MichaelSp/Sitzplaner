package net.sprauer.sitzplaner.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import net.sprauer.sitzplaner.model.DataBase;
import net.sprauer.sitzplaner.view.helper.Parameter;

class TableEventListener extends MouseAdapter {
	enum State {
		Nothing, SourceHovered, SourceSelected, TargetHovered, EditRelationToStudent, EditPriority, TableMoving, BlackboardSelected, RenameStudent
	};

	private Point mouseDownCoord;
	private final Table table;
	static Table source;
	static Table target;
	static State state;

	TableEventListener(Table classRoomTable) {
		this.table = classRoomTable;
		state = State.Nothing;
		classRoomTable.addMouseListener(this);
		classRoomTable.addMouseMotionListener(this);
		classRoomTable.addKeyListener(classRoomTable.classRoom.eventListener);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDownCoord = e.getPoint();
		e.consume();
		boolean leftButtonClicked = e.getButton() == MouseEvent.BUTTON1;
		boolean sourceHoveredState = state == State.SourceHovered;
		boolean doubleClicked = e.getClickCount() >= 2;
		if (leftButtonClicked) {
			table.classRoom.cancelEdit();
			if (sourceHoveredState) {
				if (doubleClicked) {
					state = State.RenameStudent;
					table.rename();
				} else {
					state = State.SourceSelected;
					table.setBackground(Color.lightGray);
					table.classRoom.showRelationsFor(table.student());
				}
			} else if (state == State.EditRelationToStudent) {
				state = State.SourceSelected;
				source.setBackground(Table.DEFAULT_BACKGROUND_COLOR);
				source.highlight(false);
				table.classRoom.cancelEdit();
				table.classRoom.hideRelations();
			} else if (state == State.RenameStudent) {
				state = State.SourceHovered;
				source.highlight(false);
				table.cancelRename();
			}
		} else if (e.getButton() == MouseEvent.BUTTON3 && sourceHoveredState) {
			state = State.TableMoving;
			table.setBackground(Color.blue);
		}
		source = table;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (state == State.TargetHovered) {
			state = State.EditRelationToStudent;
			source.setBackground(Color.lightGray);
			target.setBackground(relationAsColor());
		} else if (state == State.SourceSelected) {
			state = State.SourceHovered;
			table.setBackground(Table.DEFAULT_BACKGROUND_COLOR);
			table.classRoom.setRelationLine(null, null, -1);
			table.classRoom.hideRelations();
			source.highlight(false);
			source = null;
		} else if (state == State.BlackboardSelected) {
			state = State.EditPriority;
		} else if (state == State.EditPriority) {
			state = State.Nothing;
			source.classRoom.hideRelations();
			source.classRoom.setRelationLine(null, null, -1);
			source.highlight(false);
			source = null;
		} else if (state == State.TableMoving) {
			state = State.SourceHovered;
			table.setBackground(Table.DEFAULT_BACKGROUND_COLOR);
			source = null;
		}
		mouseDownCoord = null;
	}

	private Color relationAsColor() {
		int val = DataBase.instance().relationBetween(source.student(), target.student());
		return Table.getColorForValue(val);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (state == State.TargetHovered) {
			int relationValue = DataBase.instance().relationBetween(table.student(), target.student());
			table.classRoom.setRelationLine(table.getLocationOnScreen(), target.getLocationOnScreen(), relationValue);
		} else if (state == State.SourceSelected || state == State.BlackboardSelected) {
			Point blackboardPos = blackboardCenter();
			if (e.getLocationOnScreen().y > blackboardPos.y) {
				state = State.BlackboardSelected;
				int relationValue = DataBase.instance().getPriority(table.student());
				table.classRoom.setRelationLine(table.getLocationOnScreen(), blackboardPos, relationValue);
				table.classRoom.blackboard.highlight(true);
			} else {
				state = State.SourceSelected;
				table.classRoom.setRelationLine(table.getLocationOnScreen(), e.getLocationOnScreen(), -1);
				table.classRoom.legende.hideValueMarker();
				table.classRoom.blackboard.highlight(false);
			}
		} else if (state == State.TableMoving) {
			Point pos = e.getLocationOnScreen();
			pos.translate(-mouseDownCoord.x, -mouseDownCoord.y);
			table.setLocation(Table.coordsToPoint(table.alignedCoords(pos)));
			table.classRoom.setComponentZOrder(table, 0);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (state == State.Nothing) {
			state = State.SourceHovered;
			table.highlight(true);
			table.classRoom.showRelationsFor(table.studentIdx);
		} else if (state == State.SourceSelected && source != table) {
			state = State.TargetHovered;
			target = table;
			target.setBackground(relationAsColor());
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (state == State.SourceHovered) {
			state = State.Nothing;
			table.setBackground(Table.DEFAULT_BACKGROUND_COLOR);
			table.classRoom.hideRelations();
			table.highlight(false);
		} else if (state == State.TargetHovered) {
			state = State.SourceSelected;
			target = null;
			table.classRoom.legende.hideValueMarker();
			table.classRoom.showRelationsFor(source.studentIdx);
		} else if (state == State.EditRelationToStudent) {
			state = State.Nothing;
			source.setBackground(Table.DEFAULT_BACKGROUND_COLOR);
			source.highlight(false);
			source = null;
			target = null;
			table.classRoom.hideRelations();
			table.classRoom.cancelEdit();
		}
	}

	static Point blackboardCenter() {
		Point blackboardPos = new Point(source.classRoom.getLocationOnScreen().x + source.classRoom.getWidth() / 2,
				source.classRoom.getHeight() + source.classRoom.getLocationOnScreen().y - Parameter.blackboardHeight);
		return blackboardPos;
	}

	public static void setRelationTo(int relation) {
		DataBase.instance().setRelationBetween(source.student(), target.student(), relation);
		target.setBackground(Table.getColorForValue(relation));
	}

	public static void reset() {
		reset(source);
		reset(target);
		state = State.Nothing;
	}

	private static void reset(Table t) {
		if (t != null) {
			t.setBackground(Table.DEFAULT_BACKGROUND_COLOR);
			t.highlight(false);
			t = null;
		}
	}
}