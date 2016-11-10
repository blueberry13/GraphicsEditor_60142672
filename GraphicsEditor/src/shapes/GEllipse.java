package shapes;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import constants.GConstants.EDrawingType;

public class GEllipse extends GShape{
	private Ellipse2D ellipse;
	
	public GEllipse() {
		super(EDrawingType.TP, new Ellipse2D.Double(0, 0, 0, 0));
		this.ellipse = (Ellipse2D) getShape();
	}
	
	@Override
	public void initDrawing(int x, int y, Graphics2D g2D) {
		this.ellipse.setFrame(x, y, 0, 0);
		this.draw(g2D);
	}

	@Override
	public void keepDrawing(int x, int y, Graphics2D g2D) {
		this.draw(g2D);
		this.ellipse.setFrame(this.ellipse.getX(), this.ellipse.getY(), x - this.ellipse.getX(), y - this.ellipse.getY());
		this.draw(g2D);
	}

	@Override
	public void continueDrawing(int x, int y, Graphics2D g2D) {
	}

	@Override
	public void finishDrawing(int x, int y, Graphics2D g2D) {
		this.getAnchors().draw(g2D, this.ellipse.getBounds());
	}
}
