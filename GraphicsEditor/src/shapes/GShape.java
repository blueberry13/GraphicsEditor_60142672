package shapes;

import java.awt.Graphics2D;

import constants.GConstants.EDrawingType;

public abstract class GShape {
	private EDrawingType eDrawingType;
	
	public EDrawingType geteDrawingType() { return eDrawingType;}
	
	public GShape(EDrawingType eDrawingType){
		this.eDrawingType = eDrawingType;
	}
	
	public GShape clone() {
		try {
			return this.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public abstract void initDrawing(int x, int y);
	public abstract void keepDrawing(int x, int y, Graphics2D g2D);
	public abstract void continueDrawing(int x, int y, Graphics2D g2D);
	public abstract void finishDrawing(int x, int y, Graphics2D g2D);
	public abstract void draw(Graphics2D g2D);
}
