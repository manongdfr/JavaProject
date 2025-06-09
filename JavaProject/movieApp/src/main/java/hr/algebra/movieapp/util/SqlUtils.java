/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.movieapp.util;

import hr.algebra.movieapp.config.Database;

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

            // charge le fichier depuis src/main/resources
            InputStream is = SqlUtils.class.getClassLoader().getResourceAsStream(resourceName);
            if (is == null) {
                System.err.println("❌ Fichier introuvable : " + resourceName);
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

            System.out.println("✅ Script exécuté : " + resourceName);

        } catch (Exception ex) {
            System.err.println("❌ Erreur d'exécution : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
