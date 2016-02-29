package net.sf.memoranda.ui;

// import java.awt.Dimension;
// import java.awt.Frame;
// import java.awt.Toolkit;
import java.util.Calendar;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.UIManager;

import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.util.Configuration;
import net.sf.memoranda.ui.LoginForm;

/**
 *
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: App.java,v 1.28 2007/03/20 06:21:46 alexeya Exp $*/
public class App {
	// boolean packFrame = false;

	static AppFrame frame = null;

	public static final String GUIDE_URL = "http://memoranda.sourceforge.net/guide.html";
	public static final String BUGS_TRACKER_URL = "http://sourceforge.net/tracker/?group_id=90997&atid=595566";
	public static final String WEBSITE_URL = "http://memoranda.sourceforge.net";

	private JFrame splash = null;

	/*========================================================================*/
	/* Note: Please DO NOT edit the version/build info manually!
       The actual values are substituted by the Ant build script using
       'version' property and datestamp.*/

	public static final String VERSION_INFO = "@VERSION@";
	public static final String BUILD_INFO = "@BUILD@";

	/*========================================================================*/

	public static AppFrame getFrame() {
		return frame;
	}

	public void show() {
		if (frame.isVisible()) {
			frame.toFront();
			frame.requestFocus();
		} else
			init();
	}

	public void startMenu(){
		System.out.println(VERSION_INFO);
		System.out.println(Configuration.get("LOOK_AND_FEEL"));
		// 1. Making updates for new Look and Feel options.
		// Ricky Lind
		try {
			if (Configuration.get("LOOK_AND_FEEL").equals("system"))
				UIManager.setLookAndFeel(
					//"com.sun.java.swing.plaf.motif.MotifLookAndFeel");
					UIManager.getSystemLookAndFeelClassName());
			else if (Configuration.get("LOOK_AND_FEEL").equals("java"))
				UIManager.setLookAndFeel(
					UIManager.getCrossPlatformLookAndFeelClassName());
			else if (Configuration.get("LOOK_AND_FEEL").equals("added"))
				/** 
				 *  1. Made the added Look and Feel button change the
				 *     programs appearance
				 *  Ricky Lind
				 */ 
				UIManager.setLookAndFeel(

					"com.sun.java.swing.plaf.motif.MotifLookAndFeel");
					//UIManager.getSystemLookAndFeelClassName());
			else if (Configuration.get("LOOK_AND_FEEL").toString().length() > 0)

				UIManager.setLookAndFeel(
					Configuration.get("LOOK_AND_FEEL").toString());

		} catch (Exception e) {

			new ExceptionDialog(e, "Error when initializing a pluggable "
					+ "look-and-feel. Default LF will be used.", "Make sure "
					+ "that specified look-and-feel library classes are on the "
					+ "CLASSPATH.");
		}
		if (Configuration.get("FIRST_DAY_OF_WEEK").equals("")) {
			String fdow;
			if (Calendar.getInstance().getFirstDayOfWeek() == 2)
				fdow = "mon";
			else
				fdow = "sun";
			Configuration.put("FIRST_DAY_OF_WEEK", fdow);
			Configuration.saveConfig();
			/* DEBUG */
			System.out.println("[DEBUG] first day of week is set to " + fdow);
		}

		EventsScheduler.init();
		frame = new AppFrame();

	}

	public App(boolean fullmode) {
		super();
		if (fullmode)
			fullmode = !Configuration.get("START_MINIMIZED").equals("yes");
		/* DEBUG */
		if (!fullmode)
			System.out.println("Minimized mode");
		if (!Configuration.get("SHOW_SPLASH").equals("no"))
			showSplash();

		// final JFrame frame = new JFrame("Memoranda");
		// loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// loginForm.setSize(400,300);

		LoginForm loginForm = new LoginForm(frame);
		loginForm.setVisible(true);
		if(loginForm.isSucceeded()){
			startMenu();
			if (fullmode){
				init();
			}
			if (!Configuration.get("SHOW_SPLASH").equals("no"))

			splash.dispose();
		} else{
			splash.dispose();
		}
	}

	void init() {
		double JVMVer =
			Double
				.valueOf(System.getProperty("java.version").substring(0, 3))
				.doubleValue();

		frame.pack();
		if (JVMVer >= 1.4) {
			frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		} else {
			frame.setExtendedState(Frame.NORMAL);
		}
		frame.setVisible(true);
		frame.toFront();
		frame.requestFocus();

	}

	public static void closeWindow() {
		if (frame == null)
			return;
		frame.dispose();
	}

	// 1. Added new method for minimizing the window
	//    to the taskbar rather than just closing it.
	public static void minimizeWindow() {
		if (frame == null)
			return;
		frame.setState (Frame.ICONIFIED);
	}

	/**
	 * Method showSplash.
	 */
	private void showSplash() {
		splash = new JFrame();
		ImageIcon spl =
			new ImageIcon(App.class.getResource("resources/splash.png"));
		JLabel l = new JLabel();
		l.setSize(400, 300);
		l.setIcon(spl);
		splash.getContentPane().add(l);
		splash.setSize(400, 300);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		splash.setLocation(
			(screenSize.width - 400) / 2,
			(screenSize.height - 300) / 2);
		splash.setUndecorated(true);
		splash.setVisible(true);
	}
}
