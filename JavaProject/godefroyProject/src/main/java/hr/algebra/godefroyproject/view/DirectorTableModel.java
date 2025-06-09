/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.view;

import hr.algebra.godefroyproject.model.Director;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 *
 * @author manongodefroy
 */


public class DirectorTableModel extends AbstractTableModel {

    private final List<Director> directors;
    private final String[] columnNames = { "Id", "First Name", "Last Name" };

    public DirectorTableModel(List<Director> directors) {
        this.directors = directors;
    }

    @Override
    public int getRowCount() {
        return directors.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Director d = directors.get(rowIndex);
        switch (columnIndex) {
            case 0: return d.getId();
            case 1: return d.getFirstName();
            case 2: return d.getLastName();
            default: return null;
        }
    }

    public Director getDirectorAt(int row) {
        return directors.get(row);
    }
}

