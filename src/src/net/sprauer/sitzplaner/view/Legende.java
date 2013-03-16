package net.sprauer.sitzplaner.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class Legende extends JComponent {

	private static final long serialVersionUID = 6295984087908608613L;
	private int value = -1;

	public Legende(Dimension size) {
		setLayout(new BorderLayout(10, 10));

		JLabel lblOk = new JLabel(" BAD ");
		add(lblOk, BorderLayout.NORTH);

		JLabel lblBad = new JLabel(" OK ");
		add(lblBad, BorderLayout.SOUTH);
		setLocation(0, 0);
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
		if (value < 0) {
			return;
		}
		g.setColor(Color.white);
		int pos = (int) ((100 - value) / 100.0 * (getHeight() - 10));
		g.fillRect(2, pos, getWidth() - 4, 10);
	}

	private void paintBackground(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setPaint(new GradientPaint(new Point(0, 0), Color.red, new Point(0, getHeight()), Color.green));
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.dispose();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int v) {
		value = Math.min(100, Math.max(0, v));
		repaint();
	}

	public void init(Dimension dimension) {
		setLocation(0, 0);
		setSize(Parameter.legendWidth, dimension.height);
	}

	public void hideValueMarker() {
		value = -1;
		repaint();
	}

}
