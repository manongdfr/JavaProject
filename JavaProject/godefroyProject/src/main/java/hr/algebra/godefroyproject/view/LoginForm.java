/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.view;

import hr.algebra.godefroyproject.model.AppUser;
import hr.algebra.godefroyproject.repository.UserRepository;

import javax.swing.*;
import java.awt.*;


/**
 *
 * @author manongodefroy
 */

public class LoginForm extends JDialog {

    private JTextField txtUsername = new JTextField(15);
    private JPasswordField txtPassword = new JPasswordField(15);
    private JButton btnLogin = new JButton("Login");

    public LoginForm(Frame owner) {
        super(owner, "Please log in", true);
        initUI();
    }

    private void initUI() {
        JPanel pnl = new JPanel(new GridLayout(3, 2, 5, 5));
        pnl.add(new JLabel("Username:"));
        pnl.add(txtUsername);
        pnl.add(new JLabel("Password:"));
        pnl.add(txtPassword);
        pnl.add(new JLabel());
        pnl.add(btnLogin);

        btnLogin.addActionListener(e -> doLogin());

        setContentPane(pnl);
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void doLogin() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());

        try {
            AppUser u = UserRepository.authenticate(user, pass);
            if (u != null) {
                dispose();               // close dialog
                // after entering the credentials
                this.dispose();
                new MainFrame(u);

            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Invalid credentials",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Login error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
