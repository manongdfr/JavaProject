/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.view;

import hr.algebra.godefroyproject.model.Genre;
import hr.algebra.godefroyproject.repository.GenreRepository;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author manongodefroy
 */


public class GenrePanel extends JPanel {

    private final JTable tblGenres = new JTable();
    private GenreTableModel tableModel;
    private final JButton btnAdd     = new JButton("Add");
    private final JButton btnEdit    = new JButton("Edit");
    private final JButton btnDelete  = new JButton("Delete");
    private final JButton btnRefresh = new JButton("Refresh");

    public GenrePanel() {
        super(new BorderLayout());
        initComponents();
        loadData();
    }

    private void initComponents() {
        JToolBar bar = new JToolBar();
        bar.add(btnAdd);
        bar.add(btnEdit);
        bar.add(btnDelete);
        bar.add(btnRefresh);
        add(bar, BorderLayout.NORTH);

        add(new JScrollPane(tblGenres), BorderLayout.CENTER);

        btnRefresh.addActionListener(e -> loadData());
        btnAdd    .addActionListener(e -> onAdd());
        btnEdit   .addActionListener(e -> onEdit());
        btnDelete .addActionListener(e -> onDelete());
    }

    private void loadData() {
        try {
            List<Genre> genres = GenreRepository.findAllGenres();
            tableModel = new GenreTableModel(genres);
            tblGenres.setModel(tableModel);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading genres:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onAdd() {
        String name = JOptionPane.showInputDialog(this, "Genre name:");
        if (name == null || name.trim().isEmpty()) return;
        try {
            Genre g = new Genre(name.trim());
            GenreRepository.createGenre(g);
            loadData();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error adding genre:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEdit() {
        int row = tblGenres.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a genre first");
            return;
        }
        Genre g = tableModel.getGenreAt(row);
        String name = JOptionPane.showInputDialog(this,
            "Update name:", g.getName());
        if (name == null || name.trim().isEmpty()) return;
        try {
            g.setName(name.trim());
            GenreRepository.updateGenre(g);
            loadData();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error editing genre:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDelete() {
        int row = tblGenres.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a genre first");
            return;
        }
        Genre g = tableModel.getGenreAt(row);
        if (JOptionPane.showConfirmDialog(this,
            "Delete '" + g.getName() + "'?",
            "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                GenreRepository.deleteGenre(g.getId());
                loadData();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Error deleting genre:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

