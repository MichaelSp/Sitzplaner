package net.sprauer.sitzplaner.model;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class DataBaseTest {

	private static final String STUDENT_NAME = "first last";
	private final Random rand = new Random(System.nanoTime());

	@Before
	public void setup() {
		DataBase.instance().clear();
	}

	@Test
	public void testBasicSize() {
		DataBase db = DataBase.instance();
		db.setSize(3);
		assertEquals(false, db.isEmpty());
		assertEquals(3, db.getSize());
	}

	@Test
	public void testNames() {
		DataBase db = DataBase.instance();
		db.setSize(3);
		for (int i = 0; i < 3; i++) {
			db.setName(i, STUDENT_NAME);
		}
		for (int i = 0; i < 3; i++) {
			assertEquals(STUDENT_NAME, db.getName(i));
		}
	}

	@Test
	public void testRelations() {
		DataBase db = DataBase.instance();
		db.setSize(3);
		db.setRelationBetween(0, 1, 20);
		assertEquals(20, db.relationBetween(1, 0));
	}

	@Test
	public void testMoreRelations() {
		DataBase db = DataBase.instance();
		db.setSize(50);

		Map<Integer, Point> relations = createRelations(30);
		// check relations
		for (int i = 0; i < DataBase.instance().getSize(); i++) {
			Point expected = relations.get(i);
			for (int j = 0; j < DataBase.instance().getSize(); j++) {
				int relationij = DataBase.instance().relationBetween(i, j);
				int relationji = DataBase.instance().relationBetween(j, i);
				assertEquals("ij == ji", relationij, relationji);

				if (expected == null || expected.x != j) {
					continue;
				}
				assertEquals("relation " + i + "to" + j, expected.y, relationij);
				assertEquals("relation " + i + "to" + j, expected.y, relationji);
			}
		}
	}

	private Map<Integer, Point> createRelations(int relationCount) {
		Map<Integer, Point> relations = new HashMap<Integer, Point>();
		for (int i = 0; i < relationCount; i++) {
			int relation = rand.nextInt(10) - 5;
			int target = rand.nextInt(DataBase.instance().getSize());
			if (target == i) {
				i--; // try again
				continue;
			}
			relations.put(i, new Point(target, relation));
			DataBase.instance().setRelationBetween(i, target, relation);
		}
		return relations;
	}

	@Test
	public void testPriority() throws Exception {
		DataBase db = DataBase.instance();
		db.setSize(3);
		db.setPriority(0, 10);
		assertEquals(10, db.getPriority(0));
	}

	@Test
	public void testResetToClassSizeZero() {
		DataBase db = DataBase.instance();
		db.setSize(0);
		assertEquals(0, db.getSize());
		assertEquals(true, db.isEmpty());
	}

	@Test
	public void testResetToClassSize() {
		DataBase db = DataBase.instance();
		db.setSize(20);
		assertEquals(20, db.getSize());
		db.setSize(10);
		assertEquals(10, db.getSize());
	}

	@Test
	public void testRemoveStudents_CheckRelations() throws Exception {
		DataBase db = DataBase.instance();
		db.setSize(5);
		db.setRelationBetween(0, 3, 10);
		assertEquals(10, db.relationBetween(0, 3));
		db.setSize(2);
		assertEquals(0, db.relationBetween(0, 3));
	}

	@Test
	public void testRemoveStudents_increaseAndCheckRelations() throws Exception {
		DataBase db = DataBase.instance();
		db.setSize(5);
		db.setRelationBetween(0, 3, 10);
		assertEquals(10, db.relationBetween(0, 3));
		db.setSize(2);
		assertEquals(0, db.relationBetween(0, 3));
		db.setSize(5);
		assertEquals(0, db.relationBetween(0, 3));
	}
}
