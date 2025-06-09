/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.view;

import hr.algebra.godefroyproject.model.Director;
import hr.algebra.godefroyproject.repository.DirectorRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author manongodefroy
 */


public class DirectorPanel extends JPanel {

    private final JTable tblDirectors = new JTable();
    private DirectorTableModel tableModel;
    private final JButton btnAdd     = new JButton("Add");
    private final JButton btnEdit    = new JButton("Edit");
    private final JButton btnDelete  = new JButton("Delete");
    private final JButton btnRefresh = new JButton("Refresh");

    public DirectorPanel() {
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

        add(new JScrollPane(tblDirectors), BorderLayout.CENTER);

        btnRefresh.addActionListener(e -> loadData());
        btnAdd    .addActionListener(e -> onAdd());
        btnEdit   .addActionListener(e -> onEdit());
        btnDelete .addActionListener(e -> onDelete());
    }

    private void loadData() {
        try {
            List<Director> directors = DirectorRepository.findAllDirectors();
            tableModel = new DirectorTableModel(directors);
            tblDirectors.setModel(tableModel);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading directors:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onAdd() {
        String first = JOptionPane.showInputDialog(this, "First name:");
        if (first == null || first.trim().isEmpty()) return;
        String last = JOptionPane.showInputDialog(this, "Last name:");
        if (last == null) last = "";
        try {
            Director d = new Director(first.trim(), last.trim());
            DirectorRepository.createDirector(d);
            loadData();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error adding director:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEdit() {
        int row = tblDirectors.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a director first");
            return;
        }
        Director d = tableModel.getDirectorAt(row);
        String first = JOptionPane.showInputDialog(this,
            "First name:", d.getFirstName());
        if (first == null || first.trim().isEmpty()) return;
        String last = JOptionPane.showInputDialog(this,
            "Last name:", d.getLastName());
        if (last == null) last = "";
        try {
            d.setFirstName(first.trim());
            d.setLastName(last.trim());
            DirectorRepository.updateDirector(d);
            loadData();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error editing director:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDelete() {
        int row = tblDirectors.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a director first");
            return;
        }
        Director d = tableModel.getDirectorAt(row);
        if (JOptionPane.showConfirmDialog(this,
            "Delete '" + d.getFirstName() + " " + d.getLastName() + "'?",
            "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                DirectorRepository.deleteDirector(d.getId());
                loadData();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Error deleting director:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

