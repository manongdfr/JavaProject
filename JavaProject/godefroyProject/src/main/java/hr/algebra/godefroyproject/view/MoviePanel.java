/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.view;

import hr.algebra.godefroyproject.model.Genre;
import hr.algebra.godefroyproject.model.Director;
import hr.algebra.godefroyproject.model.Movie;
import hr.algebra.godefroyproject.repository.GenreRepository;
import hr.algebra.godefroyproject.repository.DirectorRepository;
import hr.algebra.godefroyproject.repository.MovieRepository;
import hr.algebra.godefroyproject.util.DialogUtils;
import hr.algebra.godefroyproject.util.FileChooserUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoviePanel extends JPanel {

    private final JTable tblMovies = new JTable();
    private MovieTableModel tableModel;
    private final JLabel lblImage = new JLabel("", SwingConstants.CENTER);

    private final JButton btnAdd     = new JButton("Add");
    private final JButton btnEdit    = new JButton("Edit");
    private final JButton btnDelete  = new JButton("Delete");
    private final JButton btnRefresh = new JButton("Refresh");

    // For filtering
    private List<Movie> allMovies;
    private JRadioButton rbAll;
    private JRadioButton rbSciFi;

    public MoviePanel() {
        super(new BorderLayout());
        initComponents();
        loadData();
    }

    private void initComponents() {
        // Toolbar
        JToolBar bar = new JToolBar();
        bar.add(btnAdd);
        bar.add(btnEdit);
        bar.add(btnDelete);
        bar.add(btnRefresh);

        // Filter radio buttons
        rbAll   = new JRadioButton("All", true);
        rbSciFi = new JRadioButton("Sci-Fi only");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbAll);
        bg.add(rbSciFi);
        JPanel filterPanel = new JPanel();
        filterPanel.add(rbAll);
        filterPanel.add(rbSciFi);
        bar.addSeparator();
        bar.add(filterPanel);

        add(bar, BorderLayout.NORTH);

        // Table + thumbnail
        JScrollPane scrollTable = new JScrollPane(tblMovies);
        lblImage.setBorder(new LineBorder(Color.GRAY));
        lblImage.setPreferredSize(new Dimension(160,160));
        JPanel detail = new JPanel(new BorderLayout());
        detail.add(lblImage, BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            scrollTable,
            detail
        );
        split.setResizeWeight(0.6);
        add(split, BorderLayout.CENTER);

        // Actions
        btnRefresh.addActionListener(e -> applyFilter(getSelectedGenre()));
        btnAdd    .addActionListener(e -> onAdd());
        btnEdit   .addActionListener(e -> onEdit());
        btnDelete .addActionListener(e -> onDelete());

        // Radio listeners
        rbAll.addActionListener(e -> applyFilter(null));
        rbSciFi.addActionListener(e -> applyFilter("Sci-Fi"));

        // Show thumbnail on selection
        tblMovies.getSelectionModel()
         .addListSelectionListener((ListSelectionEvent e) -> {
             if (!e.getValueIsAdjusting()) showSelectedThumbnail();
         });
    }

    public void loadData() {
        try {
            // Load all movies once
            allMovies = MovieRepository.findAllMovies();
            applyFilter(getSelectedGenre());
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error loading movies:\n" + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private String getSelectedGenre() {
        return rbSciFi.isSelected() ? "Sci-Fi" : null;
    }

    private void applyFilter(String genreName) {
        try {
            // Filter list
            List<Movie> filtered = (genreName == null)
                ? allMovies
                : allMovies.stream()
                    .filter(m -> {
                        try {
                            String g = GenreRepository.findAllGenres().stream()
                                .filter(gen -> gen.getId() == m.getGenreId())
                                .findFirst()
                                .map(Genre::getName)
                                .orElse("");
                            return genreName.equalsIgnoreCase(g);
                        } catch (Exception ex) {
                            return false;
                        }
                    })
                    .collect(Collectors.toList());

            // Reload table model
            List<Genre>    genres    = GenreRepository.findAllGenres();
            List<Director> directors = DirectorRepository.findAllDirectors();
            tableModel = new MovieTableModel(filtered, genres, directors);
            tblMovies.setModel(tableModel);
            tblMovies.setRowHeight(60);
            tblMovies.getColumnModel().getColumn(6).setPreferredWidth(60);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showSelectedThumbnail() {
        int row = tblMovies.getSelectedRow();
        if (row < 0) {
            lblImage.setIcon(null);
            return;
        }
        Movie m = tableModel.getMovieAt(row);
        Path img = Paths.get("assets/images", m.getImagePath());
        if (Files.exists(img)) {
            ImageIcon icon = new ImageIcon(img.toAbsolutePath().toString());
            Image thumb = icon.getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(thumb));
        } else {
            lblImage.setIcon(null);
        }
    }

    private void onAdd() {
        try {
            // 1) load genres & directors
            List<Genre> genres       = GenreRepository.findAllGenres();
            List<Director> directors = DirectorRepository.findAllDirectors();

            // 2) create form fields
            JTextField fTitle    = new JTextField();
            JTextArea  fDesc     = new JTextArea(3, 20);
            JSpinner   spYear    = new JSpinner(
                new SpinnerNumberModel(
                    Calendar.getInstance().get(Calendar.YEAR),
                    1900,
                    Calendar.getInstance().get(Calendar.YEAR) + 1,
                    1
                )
            );
            JComboBox<Genre>    cbGenre    = new JComboBox<>();
            JComboBox<Director> cbDirector = new JComboBox<>();

            // 3) build the form panel
            JPanel form = DialogUtils.buildMovieForm(
                fTitle, fDesc, spYear,
                cbGenre, cbDirector,
                genres, directors
            );

            // 4) show dialog
            int option = JOptionPane.showConfirmDialog(
                this, form, "Add New Movie",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            if (option != JOptionPane.OK_OPTION) {
                return;  // user cancelled
            }

            // 5) read inputs
            String   title       = fTitle.getText().trim();
            String   description = fDesc.getText().trim();
            int      year        = (int) spYear.getValue();
            Genre    genre       = (Genre) cbGenre.getSelectedItem();
            Director director    = (Director) cbDirector.getSelectedItem();

            // 6) choose & copy image
            String imageName = FileChooserUtils.chooseAndCopyImage(this);

            // 7) create movie object
            Movie m = new Movie(
                title,
                description,
                year,
                genre.getId(),
                director.getId(),
                imageName   // may be null if user cancelled
            );

            // 8) save to DB
            MovieRepository.createMovie(m);

            // 9) reload table
            loadData();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error adding movie:\n" + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void onEdit() {
        int row = tblMovies.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a movie first");
            return;
        }
        try {
            // 1) get the movie to edit
            Movie m = tableModel.getMovieAt(row);

            // 2) load genres & directors
            List<Genre> genres       = GenreRepository.findAllGenres();
            List<Director> directors = DirectorRepository.findAllDirectors();

            // 3) prepare form fields with current values
            JTextField fTitle    = new JTextField(m.getTitle());
            JTextArea  fDesc     = new JTextArea(m.getDescription(), 3, 20);
            JSpinner   spYear    = new JSpinner(
                new SpinnerNumberModel(
                    m.getReleaseYear(),
                    1900,
                    Calendar.getInstance().get(Calendar.YEAR) + 1,
                    1
                )
            );
            JComboBox<Genre>    cbGenre    = new JComboBox<>();
            JComboBox<Director> cbDirector = new JComboBox<>();

            // 4) build form panel
            JPanel form = DialogUtils.buildMovieForm(
                fTitle, fDesc, spYear,
                cbGenre, cbDirector,
                genres, directors
            );

            // 5) pre-select combo values
            cbGenre.setSelectedItem(
                genres.stream()
                      .filter(g -> g.getId() == m.getGenreId())
                      .findFirst()
                      .orElse(null)
            );
            cbDirector.setSelectedItem(
                directors.stream()
                         .filter(d -> d.getId() == m.getDirectorId())
                         .findFirst()
                         .orElse(null)
            );

            // 6) show edit dialog
            int option = JOptionPane.showConfirmDialog(
                this, form, "Edit Movie",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            if (option != JOptionPane.OK_OPTION) {
                return;
            }

            // 7) update values from form
            m.setTitle      (fTitle   .getText().trim());
            m.setDescription(fDesc    .getText().trim());
            m.setReleaseYear((int) spYear.getValue());
            m.setGenreId    (((Genre)    cbGenre.getSelectedItem()).getId());
            m.setDirectorId (((Director) cbDirector.getSelectedItem()).getId());

            // 8) optionally change the image
            int change = JOptionPane.showConfirmDialog(
                this,
                "Change poster image?",
                "Change Image",
                JOptionPane.YES_NO_OPTION
            );
            if (change == JOptionPane.YES_OPTION) {
                String newImage = FileChooserUtils.chooseAndCopyImage(this);
                m.setImagePath(newImage);
            }

            // 9) persist changes
            MovieRepository.updateMovie(m);

            // 10) refresh table & keep selection
            loadData();
            tblMovies.setRowSelectionInterval(row, row);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error editing movie:\n" + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void onDelete() {
        int row = tblMovies.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a movie first");
            return;
        }

        Movie m = tableModel.getMovieAt(row);

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Delete \"" + m.getTitle() + "\"?",
            "Confirm",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // 1) delete poster file only if imagePath is set
            String imageName = m.getImagePath();
            if (imageName != null && !imageName.isEmpty()) {
                Path imgPath = Paths.get("assets/images", imageName);
                Files.deleteIfExists(imgPath);
            }

            // 2) delete from database
            MovieRepository.deleteMovie(m.getId());

            // 3) reload table
            loadData();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error deleting movie:\n" + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

}
