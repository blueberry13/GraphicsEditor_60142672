package shapes;

import java.awt.Graphics2D;

import constants.GConstants.EDrawingType;

public class GRectangle extends GShape{
	private int x, y, w, h;
	
	public GRectangle() {
		super(EDrawingType.TP);
		this.x = 0;
		this.y = 0;
		this.w = 0;
		this.h = 0;
	}
	
	@Override
	public void initDrawing(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void keepDrawing(int x, int y, Graphics2D g2D) {
		this.draw(g2D);
		this.w = x - this.x;
		this.h = y - this.y;
		this.draw(g2D);
	}

	@Override
	public void continueDrawing(int x, int y, Graphics2D g2D) {
	}

	@Override
	public void finishDrawing(int x, int y, Graphics2D g2D) {
		this.draw(g2D);
		this.draw(g2D);
	}

	@Override
	public void draw(Graphics2D g2D) {
		g2D.drawRect(x, y, Math.abs(w), Math.abs(h));

	}
}
