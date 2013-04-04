package net.sprauer.sitzplaner.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DataBaseTest {

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
			db.setName(i, "first", "last");
		}
		for (int i = 0; i < 3; i++) {
			assertEquals("first last", db.getName(i));
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
