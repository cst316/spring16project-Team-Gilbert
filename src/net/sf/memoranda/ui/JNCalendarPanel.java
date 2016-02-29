/*
File:JNCalendarPanel.java	
Author:	Steven Nesmith
Date:	2/7/2016

Description: I added a drop down box to pick weekly view or monthly view
for calendar. I have not added implementation of the drop down box yet, so
calendar will not change.
*/
package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.Local;

/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: JNCalendarPanel.java,v 1.9 2004/04/05 10:05:44 alexeya Exp $*/
public class JNCalendarPanel extends JPanel {
  String pickcalendar[] = {"Monthly", "Today's week"};
  CalendarDate _date = CurrentDate.get();
  JToolBar navigationBar = new JToolBar();
  JPanel mntyPanel = new JPanel(new BorderLayout());
  JPanel navbPanel = new JPanel(new BorderLayout());
  JButton monthFowardB = new JButton();
  JPanel monthFowardBPanel = new JPanel();
  JButton todayB = new JButton();
  JPanel todayBPanel = new JPanel();
  JPanel monthBackBPanel = new JPanel();
  JButton monthBackB = new JButton();
  JComboBox monthsCB = new JComboBox(Local.getMonthNames());
  JComboBox calendars = new JComboBox(pickcalendar);
  BorderLayout borderLayout4 = new BorderLayout();
  JNCalendar jnCalendar = new JNCalendar(CurrentDate.get());
  JNCalendarWeek jnCalendarWeek = new JNCalendarWeek(CurrentDate.get());
  JPanel jnCalendarPanel = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  JSpinner yearSpin = new JSpinner(new SpinnerNumberModel(jnCalendar.get().getYear(), 1980, 2999, 1));
  JSpinner.NumberEditor yearSpinner = new JSpinner.NumberEditor(yearSpin, "####");

  boolean ignoreChange = false;

  private Vector selectionListeners = new Vector();

  Border border1;
  Border border2;

  public JNCalendarPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      new ExceptionDialog(ex);
    }
  }

  public Action dayBackAction =
        new AbstractAction(
            // 1. With change of forward and back button functionality,
            //    the new label shall read "Go one month back"
            // Ricky Lind 2/10/2016
            "Go one month back",
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/back16.png"))) {
        public void actionPerformed(ActionEvent e) {
            monthBackB_actionPerformed(e);
        }
  };
  
  public Action dayForwardAction =
        new AbstractAction(
            // 1. With change of forward and back button functionality,
            //    the new label shall read "Go one month back"
            // Ricky Lind 2/10/2016
            "Go one month forward",
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/forward16.png"))) {
        public void actionPerformed(ActionEvent e) {
            monthFowardB_actionPerformed(e);
        }
  };
  
  public Action todayAction =
        new AbstractAction(
            "Go to today",
            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/today16.png"))) {
        public void actionPerformed(ActionEvent e) {
            todayB_actionPerformed(e);
        }
  };
      
  void jbInit() throws Exception {
    //dayBackAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, KeyEvent.ALT_MASK));
    //dayForwardAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, KeyEvent.ALT_MASK));
    todayAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_HOME, KeyEvent.ALT_MASK));
    
    monthsCB.setRequestFocusEnabled(false);
    monthsCB.setMaximumRowCount(12);
    monthsCB.setPreferredSize(new Dimension(50 , 20));
    calendars.setRequestFocusEnabled(false);
    calendars.setMaximumRowCount(2);
    calendars.setPreferredSize(new Dimension(50 , 20));
    border1 = BorderFactory.createEmptyBorder(0,0,5,0);
    border2 = BorderFactory.createEmptyBorder();
    this.setLayout(new BorderLayout());
    navigationBar.setFloatable(false);
    monthFowardB.setAction(dayForwardAction);
    monthFowardB.setMinimumSize(new Dimension(24, 24));
    monthFowardB.setOpaque(false);
    monthFowardB.setPreferredSize(new Dimension(24, 24));
    monthFowardB.setRequestFocusEnabled(false);
    monthFowardB.setBorderPainted(false);
    monthFowardB.setFocusPainted(false);
    monthFowardB.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/forward.png")));
    monthFowardB.setText("");
    // 1. With change of forward and back button functionality,
    //    the new label shall read "One month forward"
    // Ricky Lind 2/10/2016
    monthFowardB.setToolTipText(Local.getString("One month forward"));
    
    monthFowardBPanel.setAlignmentX((float) 0.0);
    monthFowardBPanel.setMinimumSize(new Dimension(40, 24));
    monthFowardBPanel.setOpaque(false);
    monthFowardBPanel.setPreferredSize(new Dimension(40, 24));
    
    todayB.setAction(todayAction);
    todayB.setMinimumSize(new Dimension(24, 24));
    todayB.setOpaque(false);
    todayB.setPreferredSize(new Dimension(24, 24));
    todayB.setRequestFocusEnabled(false);
    todayB.setBorderPainted(false);
    todayB.setFocusPainted(false);
    todayB.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/today.png")));
    todayB.setText("");
    todayB.setToolTipText(Local.getString("To today"));
    
    monthBackBPanel.setAlignmentX((float) 1.5);
    monthBackBPanel.setMinimumSize(new Dimension(40, 24));
    monthBackBPanel.setOpaque(false);
    monthBackBPanel.setPreferredSize(new Dimension(40, 24));
    
    monthBackB.setAction(dayBackAction);
    monthBackB.setMinimumSize(new Dimension(24, 24));
    monthBackB.setOpaque(false);
    monthBackB.setPreferredSize(new Dimension(24, 24));
    monthBackB.setRequestFocusEnabled(false);
    monthBackB.setToolTipText("");
    monthBackB.setBorderPainted(false);
    monthBackB.setFocusPainted(false);
    monthBackB.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/back.png")));
    monthBackB.setText("");
    // 1. With change of forward and back button functionality,
    //    the new label shall read "One month back"
    // Ricky Lind 2/10/2016
    monthBackB.setToolTipText(Local.getString("One month back"));
    
    yearSpin.setPreferredSize(new Dimension(70, 20));
    yearSpin.setRequestFocusEnabled(false);
        yearSpin.setEditor(yearSpinner);
    navbPanel.setMinimumSize(new Dimension(202, 30));
    navbPanel.setOpaque(false);
    navbPanel.setPreferredSize(new Dimension(155, 30));
    jnCalendar.getTableHeader().setFont(new java.awt.Font("Dialog", 1, 10));
    jnCalendar.setFont(new java.awt.Font("Dialog", 0, 10));
    jnCalendar.setGridColor(Color.lightGray);
    jnCalendarWeek.getTableHeader().setFont(new java.awt.Font("Dialog", 1, 10));
    jnCalendarWeek.setFont(new java.awt.Font("Dialog", 0, 10));
    jnCalendarWeek.setGridColor(Color.lightGray);
    jnCalendarPanel.setLayout(borderLayout5);
    todayBPanel.setMinimumSize(new Dimension(68, 24));
    todayBPanel.setOpaque(false);
    todayBPanel.setPreferredSize(new Dimension(51, 24));
    this.add(navigationBar, BorderLayout.NORTH);
    navigationBar.add(navbPanel, null);
    navbPanel.add(monthBackBPanel, BorderLayout.WEST);
    monthBackBPanel.add(monthBackB, null);
    navbPanel.add(todayBPanel, BorderLayout.CENTER);
    todayBPanel.add(todayB, null);
    navbPanel.add(monthFowardBPanel, BorderLayout.EAST);
    monthFowardBPanel.add(monthFowardB, null);
    this.add(mntyPanel,  BorderLayout.SOUTH);
    mntyPanel.add(monthsCB, BorderLayout.CENTER);
    mntyPanel.add(calendars, BorderLayout.NORTH);
    mntyPanel.add(yearSpin,  BorderLayout.EAST);
    this.add(jnCalendarPanel,  BorderLayout.CENTER);
    jnCalendar.getTableHeader().setPreferredSize(new Dimension(200, 15));
    jnCalendarPanel.add(jnCalendar.getTableHeader(), BorderLayout.NORTH);
    jnCalendarPanel.add(jnCalendar, BorderLayout.CENTER);
    jnCalendar.addSelectionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        setCurrentDateDay(jnCalendar.get(), jnCalendar.get().getDay());
      }
    });
    /*CurrentDate.addChangeListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        _date = CurrentDate.get();
        refreshView();
      }
    });*/
    monthsCB.setFont(new java.awt.Font("Dialog", 0, 11));
    calendars.setFont(new java.awt.Font("Dialog", 0, 11));

    monthsCB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        monthsCB_actionPerformed(e);
      }
    });
    
    calendars.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          calendars_actionPerformed(e);
        }
      });
    
    yearSpin.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        yearSpin_actionPerformed();
      }
    });
    CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange(Project p, NoteList nl, TaskList tl, ResourcesList rl) {}
            public void projectWasChanged() {
                jnCalendar.updateUI();
            }
        });


    refreshView();
    yearSpin.setBorder(border2);
    
  }

  public void set(CalendarDate date) {
    _date = date;
    refreshView();
  }

  public CalendarDate get() {
    return _date;
  }

  public void addSelectionListener(ActionListener al) {
        selectionListeners.add(al);
    }

  private void notifyListeners() {
        for (Enumeration en = selectionListeners.elements(); en.hasMoreElements();)
             ((ActionListener) en.nextElement()).actionPerformed(new ActionEvent(this, 0, "Calendar event"));
  }

  private void setCurrentDateDay(CalendarDate dt, int d) {
    if (ignoreChange) return;
    if (_date.equals(dt)) return;
    _date = new CalendarDate(d, _date.getMonth(), _date.getYear());
    notifyListeners();
  }

  private void refreshView() {
    ignoreChange = true;
    jnCalendar.set(_date);
    monthsCB.setSelectedIndex(new Integer(_date.getMonth()));
    yearSpin.setValue(new Integer(_date.getYear()));
    ignoreChange = false;
  }
  

  void monthsCB_actionPerformed(ActionEvent e) {
    if (ignoreChange) return;
    _date = new CalendarDate(_date.getDay(), monthsCB.getSelectedIndex(), _date.getYear());
    jnCalendar.set(_date);
    notifyListeners();
  }
  
  void calendars_actionPerformed(ActionEvent e) {
	    if (ignoreChange) return;
	    else if (calendars.getSelectedIndex() == 1)
	    {
	        jnCalendarWeek.getTableHeader().setPreferredSize(new Dimension(200, 15));
	        jnCalendarPanel.add(jnCalendarWeek.getTableHeader(), BorderLayout.NORTH);
	        jnCalendarPanel.add(jnCalendarWeek, BorderLayout.CENTER);
	    	_date = new CalendarDate(_date.getDay(), calendars.getSelectedIndex(), _date.getYear());
	        jnCalendarWeek.set(_date);
	    }
	    else if (calendars.getSelectedIndex() == 0)
	    {
	        jnCalendarPanel.add(jnCalendar, BorderLayout.CENTER);
	        set(_date);
	        monthsCB_actionPerformed (e);
	        
	    }
	    notifyListeners();
	  }
  

  void yearSpin_actionPerformed() {
    if (ignoreChange) return;
    _date = new CalendarDate(_date.getDay(), _date.getMonth(), ((Integer)yearSpin.getValue()).intValue());
    jnCalendar.set(_date);
    notifyListeners();
  }

  void monthBackB_actionPerformed(ActionEvent e) {
    Calendar cal = _date.getCalendar();
     // 1. Changed Calendar monthBackB to move the calendar
     //    one month back instead of one day back
     // 2. Replaced "dayBackB" with "monthBackB" throughout
     //    entire file.
	 // Ricky Lind 2/10/2016
    cal.add(Calendar.MONTH, -1); 
    cal.getTime();
    _date = new CalendarDate(cal);
    refreshView();
    notifyListeners();
  }

  void todayB_actionPerformed(ActionEvent e) {
    _date = CalendarDate.today();
    refreshView();
    notifyListeners();
  }

  void monthFowardB_actionPerformed(ActionEvent e) {
    Calendar cal = _date.getCalendar();
     // 1. Changed Calendar monthFowardB to move the calendar
     //    one month forward instead of one day forward.
     // 2. Replaced "dayFowardB" with "monthForwardB"
     //    throughout enire file.
	 // Ricky Lind 2/10/2016
    cal.add(Calendar.MONTH, 1); 
    cal.getTime();
    _date = new CalendarDate(cal);
    refreshView();
    notifyListeners();
  }



}