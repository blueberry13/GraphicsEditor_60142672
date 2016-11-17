package frames;
import javax.swing.JMenuBar;

import constants.GConstants.EMenu;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	GMenuBar() {
		for(EMenu eMenu:EMenu.values()) {
			this.add(eMenu.getMenu());
		}
	}

	public void initialize() {
	}
}
