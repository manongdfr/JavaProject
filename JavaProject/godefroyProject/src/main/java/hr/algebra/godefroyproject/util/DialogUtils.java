/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.util;

import hr.algebra.godefroyproject.model.Genre;
import hr.algebra.godefroyproject.model.Director;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author manongodefroy
 */

public class DialogUtils {

    /**
     * create a panel for Movie => cbGenre and cbDirector => object rom genre&director
     */
    public static JPanel buildMovieForm(
            JTextField tfTitle,
            JTextArea taDesc,
            JSpinner spYear,
            JComboBox<Genre> cbGenre,
            JComboBox<Director> cbDirector,
            List<Genre> genres,
            List<Director> directors) {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.gridx  = 0; gbc.gridy = 0;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        panel.add(tfTitle, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        panel.add(new JScrollPane(taDesc), gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Release Year:"), gbc);
        gbc.gridx = 1;
        panel.add(spYear, gbc);

        // --- Genre ComboBox ---
        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Genre:"), gbc);
        gbc.gridx = 1;
        cbGenre.removeAllItems();
        for (Genre g : genres) {
            cbGenre.addItem(g);
        }
        panel.add(cbGenre, gbc);

        // --- Director ComboBox ---
        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Director:"), gbc);
        gbc.gridx = 1;
        cbDirector.removeAllItems();
        for (Director d : directors) {
            cbDirector.addItem(d);
        }
        panel.add(cbDirector, gbc);

        return panel;
    }
}
