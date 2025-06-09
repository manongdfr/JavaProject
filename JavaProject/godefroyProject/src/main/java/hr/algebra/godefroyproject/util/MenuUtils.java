package hr.algebra.godefroyproject.util;

import hr.algebra.godefroyproject.model.AppUser;
import hr.algebra.godefroyproject.view.LoginForm;
import hr.algebra.godefroyproject.view.MainFrame;

import javax.swing.*;

public class MenuUtils {

    /** 
     * File, Logout, Load from RSS
     * Admin → Clear Data
     */
    public static JMenuBar createMenuBar(JFrame frame, AppUser user) {
        JMenuBar mb = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(e -> {
            frame.dispose();
            new LoginForm(frame).setVisible(true);
        });
        file.add(logout);
        mb.add(file);

        JMenu admin = new JMenu("Config");

        // Only admins get Clear Data
        if (user.isAdmin()) {
            JMenuItem clear = new JMenuItem("Clear Data");
            clear.addActionListener(e -> ((MainFrame) frame).clearAllData());
            admin.add(clear);
        }

        // Everyone gets Load from RSS
        JMenuItem load = new JMenuItem("Load from RSS");
        load.addActionListener(e -> ((MainFrame) frame).loadFromRss());
        admin.add(load);

        mb.add(admin);
        return mb;
    }
}
