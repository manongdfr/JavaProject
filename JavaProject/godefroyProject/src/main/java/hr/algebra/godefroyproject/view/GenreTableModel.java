/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.view;

import hr.algebra.godefroyproject.model.Genre;
import javax.swing.table.AbstractTableModel;
import java.util.List;


/**
 *
 * @author manongodefroy
 */

public class GenreTableModel extends AbstractTableModel {
    private final List<Genre> genres;
    private final String[] columnNames = {"Id", "Name"};

    public GenreTableModel(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public int getRowCount() {
        return genres.size();
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
        Genre genre = genres.get(rowIndex);
        switch (columnIndex) {
            case 0: return genre.getId();
            case 1: return genre.getName();
            default: return null;
        }
    }

    public Genre getGenreAt(int row) {
        return genres.get(row);
    }
}
