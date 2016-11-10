package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;

import constants.GConstants.EDrawingType;

public abstract class GShape {
	// attributes
	private EDrawingType eDrawingType;
	private boolean selected;
	// component
	private Shape shape;
	private GAnchors anchors;
	
	// getters & setters
	public EDrawingType geteDrawingType() { return eDrawingType; }
	public void seteDrawingType(EDrawingType eDrawingType) { this.eDrawingType = eDrawingType; }
	public boolean isSelected() { return this.selected; }
	public void setSelected(boolean selected, Graphics2D g2D) {
		this.selected = selected;
		
		this.getAnchors().draw(g2D, this.shape.getBounds());

//		if(this.selected){
//			this.anchors = new GAnchors();
//			this.getAnchors().draw(g2D, this.shape.getBounds());
//		}else{
//			this.anchors = null;
//		}
	}
	
	public GAnchors getAnchors() { return this.anchors; }
	public void setAnchors(GAnchors anchors) { this.anchors = anchors; }
	public Shape getShape() { return this.shape; }
	
	// constructors
	public GShape(EDrawingType eDrawingType, Shape shape) {
		this.eDrawingType = eDrawingType;
		//
		this.selected = false;
		this.shape = shape;
		this.anchors = new GAnchors();
		//
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
		return this.shape.getBounds2D().contains(x, y);
	}
	
	public void draw(Graphics2D g2D) {
		g2D.draw(this.shape);
		if(this.selected) {
			this.anchors.draw(g2D, this.shape.getBounds());
		}
	}
	public abstract void initDrawing(int x, int y, Graphics2D g2D);
	public abstract void keepDrawing(int x, int y, Graphics2D g2D);
	public abstract void continueDrawing(int x, int y, Graphics2D g2D);
	public abstract void finishDrawing(int x, int y, Graphics2D g2D);
}
