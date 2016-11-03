package frames;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import constants.GConstants.EDrawingType;
import shapes.GShape;

public class GDrawingPanel extends JPanel {
	// attributes
	private static final long serialVersionUID = 1L;
	// object states
	private enum EState {idle, drawing};
	// components
	private Vector<GShape> shapeVector;
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
	}
	
	public void paint(Graphics g) {
		for (GShape shape: this.shapeVector) {
			shape.draw((Graphics2D)g);
		}
	}
	
	private void initDrawing(int x, int y) {
		this.currentShape= this.selectedShape.clone();
		currentShape.initDrawing(x, y);
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
	}

	class MouseEventHandler implements MouseInputListener, MouseMotionListener {
		private EState eState = EState.idle;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(selectedShape != null && selectedShape.geteDrawingType() == EDrawingType.NP) {			
				if(e.getClickCount() == 1) {
					mouse1Clicked(e);
				} else if(e.getClickCount() == 2) {
					mouse2Clicked(e);
				}
			}
		}
		
		private void mouse1Clicked(MouseEvent e) {
			if(eState == EState.idle) {
				initDrawing(e.getX(), e.getY());
				eState = EState.drawing;
			} else if(eState == EState.drawing) {
				continueDrawing(e.getX(), e.getY());
			}
		}
		
		private void mouse2Clicked(MouseEvent e) {
			if(eState == EState.drawing) {
				finishDrawing(e.getX(), e.getY());
				eState = EState.idle;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(selectedShape != null && selectedShape.geteDrawingType() == EDrawingType.NP) {			
				if (eState == EState.drawing) {
					keepDrawing(e.getX(), e.getY());
				}
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(selectedShape != null && selectedShape.geteDrawingType() == EDrawingType.TP) {			
				if(eState == EState.idle) {
					initDrawing(e.getX(), e.getY());
					eState = EState.drawing;
				}
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if(selectedShape != null && selectedShape.geteDrawingType() == EDrawingType.TP) {			
				if(eState == EState.drawing) {
					keepDrawing(e.getX(), e.getY());
				}
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(selectedShape != null && selectedShape.geteDrawingType() == EDrawingType.TP) {			
				if(eState == EState.drawing) {
					finishDrawing(e.getX(), e.getY());
					eState = EState.idle;
				}
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
