package net.sf.memoranda.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import net.sf.memoranda.ui.Login;

public class LoginForm extends JDialog{

    private JTextField username;
    private JPasswordField password;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton cancelButton;
    private boolean succeeded;

    public LoginForm(Frame parent){
		super(parent, "Login", true);
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		cs.fill = GridBagConstraints.HORIZONTAL;

		usernameLabel = new JLabel("Username: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(usernameLabel, cs);

		username = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(username, cs);

		passwordLabel = new JLabel("Password: ");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(passwordLabel, cs);

		password = new JPasswordField(20);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(password, cs);

		panel.setBorder(new LineBorder(Color.GRAY));

		loginButton = new JButton("Login");

		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (Login.authenticate(getUsername(), getPassword())){
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Hi " + getUsername() + "! Welcome back!",
                            "Login",
                            JOptionPane.INFORMATION_MESSAGE);
					succeeded = true;
					dispose();
				} else{
					JOptionPane.showMessageDialog(LoginForm.this, "Invalid username or password.", "Login", JOptionPane.ERROR_MESSAGE);
					username.setText("");
					password.setText("");
					succeeded = false;
				}
			}
		});
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		JPanel bp = new JPanel();
		bp.add(loginButton);
		bp.add(cancelButton);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);

	}

    public String getUsername(){
		return username.getText().trim();
	}

	public String getPassword(){
		return new String(password.getPassword());
	}

    public boolean isSucceeded(){
        return succeeded;
    }
}
