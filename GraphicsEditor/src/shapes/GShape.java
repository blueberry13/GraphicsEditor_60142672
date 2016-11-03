package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;

import constants.GConstants.EDrawingType;

public abstract class GShape {
	private EDrawingType eDrawingType;
	private GAnchors anchors;
	protected Shape shape;
	
	public EDrawingType geteDrawingType() { return eDrawingType; }
	
	public void seteDrawingType(EDrawingType eDrawingType) { this.eDrawingType = eDrawingType; }
	
	public GAnchors getAnchors() { return anchors; }
	
	public void setAnchors(GAnchors anchors) { this.anchors = anchors; }
	
	public GShape(EDrawingType eDrawingType) {
		this.eDrawingType = eDrawingType;
		this.anchors = new GAnchors();
	}
	
	public GShape clone() {
		try {
			return this.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean contains(int x, int y) {
		return shape.contains(x, y);
	}
	
	public abstract void initDrawing(int x, int y);
	public abstract void keepDrawing(int x, int y, Graphics2D g2D);
	public abstract void continueDrawing(int x, int y, Graphics2D g2D);
	public abstract void finishDrawing(int x, int y, Graphics2D g2D);
	public abstract void draw(Graphics2D g2D);
}
