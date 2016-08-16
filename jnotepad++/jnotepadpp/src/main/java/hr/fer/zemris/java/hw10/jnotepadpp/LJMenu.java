package hr.fer.zemris.java.hw10.jnotepadpp;

import javax.swing.JMenu;

/**
 * Localized JMenu component that changes its parameters on localization change.
 * @author Tomislav
 *
 */

public class LJMenu extends JMenu {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Key used to get localized name.
	 */
	private String key;
	/**
	 * Localization provider which notifies when localization is changed.
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Default constructor with two parameters.
	 * @param key Key used to get localized name.
	 * @param lp Localization provider which notifies when localization is changed.
	 */
	public LJMenu(String key, ILocalizationProvider lp) {
		this.key = key;
		this.lp = lp;
		this.lp.addLocalizationListener(new ILocalizationListener() {
			
			/**
			 * Method retrieves new localized name and updates component.
			 */
			@Override
			public void localizationChanged() {
				String translation = (LJMenu.this)
						.lp.getString((LJMenu.this).key);

				updateMenu(translation);			
			}
		});
		String translation = this.lp.getString(key);
		updateMenu(translation);	
	}
	
	/**
	 * Method updating this component parameters on localization change.
	 * @param text New text to be displayed
	 */
	private void updateMenu(String text) {
		this.setText(text);
	}
}
