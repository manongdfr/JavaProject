/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.util;


import hr.algebra.godefroyproject.config.Database;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 *
 * @author manongodefroy
 */

public class SqlUtils {

    public static void runSqlScript(String resourceName) {
        try (Connection conn = Database.getConnection()) {

            // load the file from src/main/resources
            InputStream is = SqlUtils.class.getClassLoader().getResourceAsStream(resourceName);
            if (is == null) {
                System.err.println("❌ file not found  : " + resourceName);
                return;
            }

            String sql = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .collect(Collectors.joining("\n"));

            try (Statement stmt = conn.createStatement()) {
                for (String command : sql.split(";")) {
                    if (!command.trim().isEmpty()) {
                        stmt.execute(command.trim());
                    }
                }
            }

            System.out.println("✅ script ok : " + resourceName);

        } catch (Exception ex) {
            System.err.println("❌ error : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
