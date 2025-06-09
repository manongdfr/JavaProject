package hr.algebra.godefroyproject.view;

import hr.algebra.godefroyproject.model.Movie;
import hr.algebra.godefroyproject.model.Genre;
import hr.algebra.godefroyproject.model.Director;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieTableModel extends AbstractTableModel {

    private final List<Movie> movies;
    private final Map<Integer,String> genreMap;
    private final Map<Integer,String> directorMap;

    private final String[] cols = {"ID","Title","Year","Description","Genre","Director","Image"};

    public MovieTableModel(List<Movie> movies, List<Genre> genres, List<Director> directors) {
        this.movies      = movies;
        this.genreMap    = genres.stream()
                                 .collect(Collectors.toMap(Genre::getId, Genre::getName));
        this.directorMap = directors.stream()
                                    .collect(Collectors.toMap(Director::getId, Director::getName));
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Movie m = movies.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> m.getId();
            case 1 -> m.getTitle();
            case 2 -> m.getReleaseYear();
            case 3 -> m.getDescription();
            case 4 -> genreMap.get(m.getGenreId());
            case 5 -> directorMap.get(m.getDirectorId());
            case 6 -> m.getImagePath();
            default -> null;
        };
    }

    public Movie getMovieAt(int rowIndex) {
        return movies.get(rowIndex);
    }
}
