package net.sf.memoranda.ui;

import javax.swing.JOptionPane;

import net.sf.memoranda.util.Local;

public class ImportSticker {

String name;        
        
        public ImportSticker(String x) {
        	name = x;
        }

        public boolean import_file() {
        	/*
            We are working on this =)
             */
                
<<<<<<< HEAD
        	    //Changed error message from Spanish to English
        	    //Ricky Lind 2/1/16
                JOptionPane.showMessageDialog(null,Local.getString("Import Failed"));
                return true;
=======
        	//Changed error message from Spanish to English
        	//Ricky Lind 2/1/16
            JOptionPane.showMessageDialog(null,Local.getString("Import Failed"));
            return true;
>>>>>>> US-9
        }
        
        public boolean export_file() {
        	//Create an error message for the export buttons
        	//Ricky Lind
            JOptionPane.showMessageDialog(null,Local.getString("Export Failed"));
            return true;
        }
}