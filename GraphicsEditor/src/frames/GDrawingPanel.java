package frames;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import constants.GConstants.EAnchors;
import shapes.GCursor;
import shapes.GPolygon;
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
	// for currentSelectedShape
	private GShape tempShape;
		
	public GDrawingPanel() {
		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
		
		this.shapeVector = new Vector<GShape>();
		
		this.setOpaque(false);
	}

	public void initialize() {
	}
	
	// toolbar selectedShape
	public void setSelectedShape(GShape shape) {
		this.selectedShape = shape;
		switch(this.selectedShape.geteDrawingType()) {
		case TP: eState = EState.idleTP; break;
		case NP: eState = EState.idleNP; break;
		}
	}
	
	private void changeCursor(int x, int y) {
		tempShape = onShape(x, y);
		if(tempShape != null){
			if(tempShape.getSelectedAnchor() == null) {
				this.setCursor(GCursor.defaultCursor);
			} else {
				if(tempShape.getSelectedAnchor()==EAnchors.MM) {
					this.setCursor(GCursor.moveCursor);
				} else if(tempShape.getSelectedAnchor()==EAnchors.NN) {
					this.setCursor(GCursor.nnCursor);
				} else if(tempShape.getSelectedAnchor()==EAnchors.SS) {
					this.setCursor(GCursor.ssCursor);
				} else if(tempShape.getSelectedAnchor()==EAnchors.WW) {
					this.setCursor(GCursor.wwCursor);
				} else if(tempShape.getSelectedAnchor()==EAnchors.EE) {
					this.setCursor(GCursor.eeCursor);
				} else if(tempShape.getSelectedAnchor()==EAnchors.NE) {
					this.setCursor(GCursor.neCursor);
				} else if(tempShape.getSelectedAnchor()==EAnchors.NW) {
					this.setCursor(GCursor.nwCursor);
				} else if(tempShape.getSelectedAnchor()==EAnchors.SE) {
					this.setCursor(GCursor.seCursor);
				} else if(tempShape.getSelectedAnchor()==EAnchors.SW) {
					this.setCursor(GCursor.swCursor);
				}
				//this.setCursor(GCursor.moveCursor);
			}
		}else{
			this.setCursor(GCursor.defaultCursor);
		}
		
	}
	
	// clicked shape
	private void setSelected() {
		for(GShape shape: this.getShapes()) {
			shape.setSelected(false);
		}
		tempShape.setSelected(true);
		this.repaint();
	}
	
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
		tempShape = this.currentShape;
		setSelected();
	}

	class MouseEventHandler implements MouseInputListener, MouseMotionListener {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(selectedShape instanceof GPolygon) {
				if(e.getClickCount() == 1) {
					mouse1Clicked(e);
				} else if(e.getClickCount() == 2) {
					mouse2Clicked(e);
				}
			} else if(onShape(e.getX(), e.getY()) != null) {
				tempShape = onShape(e.getX(), e.getY());
				setSelected();
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
			if(onShape(e.getX(), e.getY()) != null) {
				tempShape = onShape(e.getX(), e.getY());
				setSelected();
			} else if(eState == EState.idleTP) {
				initDrawing(e.getX(), e.getY());
				eState = EState.drawingTP;
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
