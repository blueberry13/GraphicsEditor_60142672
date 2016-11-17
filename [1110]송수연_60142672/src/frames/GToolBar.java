package frames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import constants.GConstants.EToolBarButton;

public class GToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	private GDrawingPanel drawingPanel;
	
	public GToolBar() {
		ButtonGroup buttonGroup = new ButtonGroup();
		ActionHandler actionHandler = new ActionHandler();
		
		for(EToolBarButton eToolBarButton: EToolBarButton.values()) {
			JRadioButton button = new JRadioButton();
			button.setIcon(new ImageIcon(eToolBarButton.getIcon()));
			button.setSelectedIcon(new ImageIcon(eToolBarButton.getSelectedIcon()));
			button.addActionListener(actionHandler);
			button.setActionCommand(eToolBarButton.toString());
			this.add(button);
			buttonGroup.add(button);
		}
	}

	// association
	public void setDrawingPanel(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public void initialize() {
		JRadioButton button = (JRadioButton) this.getComponentAtIndex(EToolBarButton.rectangle.ordinal());
		button.doClick();
	}
	
	public class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			drawingPanel.setSelectedShape(EToolBarButton.valueOf(e.getActionCommand()).getShape());
		}
	}
}
