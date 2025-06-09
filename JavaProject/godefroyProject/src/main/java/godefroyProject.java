/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import hr.algebra.godefroyproject.config.Database;
import hr.algebra.godefroyproject.util.SqlUtils;
import hr.algebra.godefroyproject.view.LoginForm;
/**
 *
 * @author manongodefroy
 */

public class godefroyProject {

    public static void main(String[] args) {
        // 1) Test DB connection
        try {
            Database.getConnection();
            System.out.println("✅ Database OK");
        } catch (Exception ex) {
            System.err.println("❌ Cannot connect: " + ex.getMessage());
            return;
        }

        // 2) Initialization
        //SqlUtils.runSqlScript("clear.sql");
        SqlUtils.runSqlScript("init.sql");
        //SqlUtils.runSqlScript("procs.sql");
        
        

        // 3) login modal
        LoginForm login = new LoginForm(null);
        login.setVisible(true);
    }
}

