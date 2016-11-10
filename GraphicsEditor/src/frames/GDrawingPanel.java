package frames;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import constants.GConstants.ECursor;
import shapes.GShape;

public class GDrawingPanel extends JPanel {
	// attributes
	private static final long serialVersionUID = 1L;
	
	// object states
	private enum EState {idleTP, idleNP, drawingTP, drawingNP};
	private EState eState = EState.idleTP;
	// components
	private Vector<GShape> shapeVector;
	private Vector<GShape> getShapes() { return this.shapeVector; }
	// associative attributes
	private GShape selectedShape;
	// working objects;
	private GShape currentShape;
		
	public GDrawingPanel() {
		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
		
		this.shapeVector = new Vector<GShape>();
	}

	public void initialize() {
	}
	
	public void setSelectedShape(GShape shape) {
		this.selectedShape = shape;
		switch(this.selectedShape.geteDrawingType()) {
		case TP: eState = EState.idleTP; break;
		case NP: eState = EState.idleNP; break;
		}
	}
	
	private void changeCursor(int x, int y) {
		if(onShape(x, y) != null) {
			//EAnchors 
			this.setCursor(ECursor.handCursor.getCursor());
		} else {
			this.setCursor(ECursor.defaultCursor.getCursor());
		}
	}

//	private void resetSelected() {
//		for(GShape shape: this.getShapes())
//	}
//	
	private GShape onShape(int x, int y) {
		for(GShape shape: this.getShapes()) {
			if(shape.contains(x, y)) {
				return shape;
			}
		}
		return null;
	}

	public void paint(Graphics g) {
		for (GShape shape: this.getShapes()) {
			shape.draw((Graphics2D) g);
		}
	}
	
	private void initDrawing(int x, int y) {
		this.currentShape= this.selectedShape.clone();
		Graphics2D g2D = (Graphics2D) this.getGraphics();
		g2D.setXORMode(g2D.getBackground());
		this.currentShape.initDrawing(x, y, g2D);
	}
	
	private void continueDrawing(int x, int y) {
		Graphics2D g2D = (Graphics2D) this.getGraphics();
		g2D.setXORMode(g2D.getBackground());
		this.currentShape.continueDrawing(x, y, g2D);
	}

	private void keepDrawing(int x, int y) {
		Graphics2D g2D = (Graphics2D) this.getGraphics();
		g2D.setXORMode(g2D.getBackground());
		this.currentShape.keepDrawing(x, y, g2D);
	}
	
	private void finishDrawing(int x, int y) {
		Graphics2D g2D = (Graphics2D) this.getGraphics();
		g2D.setXORMode(this.getBackground());
		this.currentShape.finishDrawing(x, y, g2D);
		this.shapeVector.add(this.currentShape);
		this.currentShape.setSelected(true, g2D);
	}

	class MouseEventHandler implements MouseInputListener, MouseMotionListener {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(selectedShape != null) {
				if(e.getClickCount() == 1) {
					mouse1Clicked(e);
				} else if(e.getClickCount() == 2) {
					mouse2Clicked(e);
				}
			} else if(onShape(e.getX(), e.getY()) != null) {
				
			}
		}

		private void mouse1Clicked(MouseEvent e) {
			if(eState == EState.idleNP) {
				initDrawing(e.getX(), e.getY());
				eState = EState.drawingNP;
			} else if(eState == EState.drawingNP) {
				continueDrawing(e.getX(), e.getY());
			}
		}
		
		private void mouse2Clicked(MouseEvent e) {
			if(eState == EState.drawingNP) {
				finishDrawing(e.getX(), e.getY());
				eState = EState.idleNP;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (eState == EState.drawingNP) {
				keepDrawing(e.getX(), e.getY());
			} else {
				changeCursor(e.getX(), e.getY());
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(selectedShape != null) {			
				if(eState == EState.idleTP) {
					initDrawing(e.getX(), e.getY());
					eState = EState.drawingTP;
				}
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {			
			if(eState == EState.drawingTP) {
				keepDrawing(e.getX(), e.getY());
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {			
			if(eState == EState.drawingTP) {
				finishDrawing(e.getX(), e.getY());
				eState = EState.idleTP;
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {	
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}
