package constants;

import java.awt.Cursor;

import javax.swing.JMenu;

import menus.GEditMenu;
import menus.GFileMenu;
import shapes.GEllipse;
import shapes.GLine;
import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GShape;

public class GConstants {
	public final static String MAINFRAME_TITLE = "GraphicsEditor";
	public final static String FILEMENU_TITLE = "File";
	public final static String EDITMENU_TITLE = "Edit";
	public final static int AWIDTH = 8;
	public final static int AHEIGHT = 8;
	
	public enum EMainFrame {
		X(100), Y(100), W(400), H(400);
		
		private int value;
		
		private EMainFrame (int value) {
			this.value = value;
		}
		
		public int getValue() { return this.value; }
	}
	
	public static enum EMenu {
		fileMenu(new GFileMenu()),
		editMenu(new GEditMenu());
		
		private JMenu menu;
		
		private EMenu(JMenu menu) {
			this.menu = menu;
		}
		
		public JMenu getMenu() { return this.menu; }
	}
	
	public static enum EFileMenuItem {
		newFile("New"),
		open("Open"),
		close("Close"),
		save("Save"),
		saveAs("Save as"),
		print("Print"),
		exit("Exit");
		
		private String text;
		
		private EFileMenuItem(String text) {
			this.text = text;
		}
		
		public String getText() { return this.text; }
	}
	
	public static enum EEditMenuItem {
		undo("Undo"),
		repeat("Repeat"),
		cut("Cut"),
		copy("Copy"),
		paste("Paste");
		
		private String text;
		
		private EEditMenuItem(String text) {
			this.text = text;
		}
		
		public String getText() { return this.text; }
	}
	
	public static enum EToolBarButton {
		rectangle("rsc/Rectangle.gif", "rsc/RectangleSLT.gif", new GRectangle()),
		ellipse("rsc/Ellipse.gif", "rsc/EllipseSLT.gif", new GEllipse()),
		line("rsc/Line.gif", "rsc/LineSLT.gif", new GLine()),
		polygon("rsc/Polygon.gif", "rsc/PolygonSLT.gif", new GPolygon());
		
		private String icon;
		private String selectedIcon;
		private GShape shape;
		
		private EToolBarButton(String icon, String selectedIcon, GShape shape) {
			this.icon = icon;
			this.selectedIcon = selectedIcon;
			this.shape = shape;
		}
		
		public String getIcon() { return this.icon; }
		public String getSelectedIcon() { return this.selectedIcon; }
		public GShape getShape() { return this.shape; }
	}

	public static enum EDrawingType {
		TP, NP;
	}
	
	public static enum ECursor {
		defaultCursor(new Cursor(Cursor.DEFAULT_CURSOR)),
		handCursor(new Cursor(Cursor.HAND_CURSOR));
			
		private Cursor cursor;
		
		private ECursor(Cursor cursor) {
			this.cursor = cursor;
		}
		
		public Cursor getCursor() { return this.cursor; }
	}
	
	public enum EAnchors {NW, NN, NE, WW, EE, SW, SS, SE};
}
