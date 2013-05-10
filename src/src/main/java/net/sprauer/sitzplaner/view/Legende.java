package net.sprauer.sitzplaner.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import net.sprauer.sitzplaner.view.helper.Parameter;

public class Legende extends JComponent {

	private static final int INVALID_VALUE = -10;
	private static final long serialVersionUID = 6295984087908608613L;
	private int value = INVALID_VALUE;

	public Legende() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JLabel lblGood = new JLabel("GOOD");
		springLayout.putConstraint(SpringLayout.WEST, lblGood, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, lblGood, 109, SpringLayout.WEST, this);
		add(lblGood);

		JLabel lblBad = new JLabel("BAD");
		springLayout.putConstraint(SpringLayout.WEST, lblBad, 0, SpringLayout.WEST, lblGood);
		springLayout.putConstraint(SpringLayout.SOUTH, lblBad, -10, SpringLayout.SOUTH, this);
		add(lblBad);
		setLocation(0, 0);

		JLabel lblFront = new JLabel("Front");
		springLayout.putConstraint(SpringLayout.EAST, lblBad, 0, SpringLayout.EAST, lblFront);
		springLayout.putConstraint(SpringLayout.NORTH, lblGood, 6, SpringLayout.SOUTH, lblFront);
		springLayout.putConstraint(SpringLayout.NORTH, lblFront, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblFront, 10, SpringLayout.WEST, this);
		add(lblFront);

		JLabel lblBack = new JLabel("Back");
		springLayout.putConstraint(SpringLayout.NORTH, lblBack, 6, SpringLayout.SOUTH, lblGood);
		springLayout.putConstraint(SpringLayout.WEST, lblBack, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, lblBack, -6, SpringLayout.NORTH, lblBad);
		add(lblBack);
	}

	@Override
	public void paint(Graphics g) {
		paintBackground(g);
		paintFrame(g);
		paintValueMarker(g);
		super.paint(g);
	}

	private void paintFrame(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth(), getHeight());
	}

	private void paintValueMarker(Graphics g) {
		if (value < INVALID_VALUE) {
			return;
		}
		g.setColor(Color.white);
		int pos = (int) ((100 - value) / 100.0 * (getHeight() - 10));
		g.fillRect(2, pos, getWidth() - 4, 10);
	}

	private void paintBackground(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setPaint(new GradientPaint(new Point(0, 0), Color.green, new Point(0, getHeight()), Color.red));
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.dispose();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int v) {
		value = (Math.min(5, Math.max(-5, v)) + 5) * 10;
		repaint();
	}

	public void init(Dimension dimension) {
		setLocation(0, 0);
		setSize(Parameter.legendWidth, dimension.height);
	}

	public void hideValueMarker() {
		value = INVALID_VALUE;
		repaint();
	}
}
