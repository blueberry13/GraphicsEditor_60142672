package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.Vector;

import constants.GConstants;
import constants.GConstants.EAnchors;

public class GAnchors extends Vector<Ellipse2D.Double>{
	private static final long serialVersionUID = 1L;
	
	int AWIDTH = GConstants.AWIDTH;
	int AHEIGHT = GConstants.AHEIGHT;
	
	public GAnchors() {
		for(int i = 0; i < EAnchors.values().length; i++) {
			this.add(new Ellipse2D.Double(0, 0, AWIDTH, AHEIGHT));
		}
	}

	private void computeCoordinates(Rectangle r) {
		for(int i = 0; i < EAnchors.values().length; i++) {
			switch(EAnchors.values()[i]) {
			case NW:
				this.get(i).x = r.x - AWIDTH/2;
				this.get(i).y = r.y - AHEIGHT/2;
				break;
			case NN:
				this.get(i).x = r.x - AWIDTH/2 + r.width/2;
				this.get(i).y = r.y - AHEIGHT/2;
				break;
			case NE:
				this.get(i).x = r.x - AWIDTH/2 + r.width;
				this.get(i).y = r.y - AHEIGHT/2;
				break;
			case WW:
				this.get(i).x = r.x - AWIDTH/2;
				this.get(i).y = r.y - AHEIGHT/2 + r.height/2;
				break;
			case EE:
				this.get(i).x = r.x - AWIDTH/2 + r.width;
				this.get(i).y = r.y - AHEIGHT/2 + r.height/2;
				break;
			case SW:
				this.get(i).x = r.x - AWIDTH/2;
				this.get(i).y = r.y - AHEIGHT/2 + r.height;
				break;
			case SS:
				this.get(i).x = r.x - AWIDTH/2 + r.width/2;
				this.get(i).y = r.y - AHEIGHT/2 + r.height;
				break;
			case SE:
				this.get(i).x = r.x - AWIDTH/2 + r.width;
				this.get(i).y = r.y - AHEIGHT/2 + r.height;
				break;
			case RR:
				this.get(i).x = r.x - AWIDTH/2 + r.width/2;
				this.get(i).y = r.y - AHEIGHT*4;
				break;
			default:
				break;
			}
		}
	}
	
	public void draw(Graphics2D g2D, Rectangle boundRectangle) {
		this.computeCoordinates(boundRectangle);
		for(int i = 0; i < EAnchors.values().length; i++) {
			g2D.draw(this.get(i));
		}
	}
}
