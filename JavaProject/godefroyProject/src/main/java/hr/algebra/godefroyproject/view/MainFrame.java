/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.view;

import hr.algebra.godefroyproject.model.AppUser;
import hr.algebra.godefroyproject.model.Movie;
import hr.algebra.godefroyproject.model.Genre;
import hr.algebra.godefroyproject.model.Director;
import hr.algebra.godefroyproject.repository.GenreRepository;
import hr.algebra.godefroyproject.repository.DirectorRepository;
import hr.algebra.godefroyproject.repository.MovieRepository;
import hr.algebra.godefroyproject.util.MenuUtils;
import hr.algebra.godefroyproject.util.RssParser;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainFrame extends JFrame {

    private final AppUser user;

    public MainFrame(AppUser user) {
        this.user = user;
        setTitle("Welcome " + user.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // launch API demo
        //demonstrateFunctionalApi();
    }

    private void initComponents() {
        // MenuUtils
        JMenuBar mb = MenuUtils.createMenuBar(this, user);

        // DemoApi
        JMenu admin = new JMenu("Admin");
        JMenuItem demoApi = new JMenuItem("Demo Functional API");
        demoApi.addActionListener(e -> demonstrateFunctionalApi());
        admin.add(demoApi);
        mb.add(admin);

        setJMenuBar(mb);

        // tabs
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Movies",    new MoviePanel());
        tabs.addTab("Genres",    new GenrePanel());
        tabs.addTab("Directors", new DirectorPanel());
        setContentPane(tabs);
    }

    /**
     * clear all the tables and img
     */
    public void clearAllData() {
        try (Connection conn = hr.algebra.godefroyproject.config.Database.getConnection();
             Statement st = conn.createStatement()) {

            String sql = Files.readString(Paths.get("src/main/resources/clear.sql"));
            st.execute(sql);

            try (Stream<Path> paths = Files.walk(Paths.get("assets/images"))) {
                paths.filter(Files::isRegularFile)
                     .forEach(p -> {
                         try { Files.delete(p); }
                         catch (IOException ignore) { }
                     });
            }

            JOptionPane.showMessageDialog(
                this,
                "All data cleared",
                "Info",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error clearing data:\n" + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * add new film using the RSS file
     */
    public void loadFromRss() {
        InputStream rssStream = null;
        try {
            // 1) loading from classpath or fallback from source
            rssStream = MainFrame.class
                .getClassLoader()
                .getResourceAsStream("movies.rss");
            if (rssStream == null) {
                Path p = Paths.get("src/main/resources/movies.rss");
                if (!Files.exists(p)) {
                    throw new FileNotFoundException("movies.rss not found in classpath or at " + p);
                }
                rssStream = Files.newInputStream(p);
            }

            // 2) get all the existing lists
            List<Genre>    genres    = GenreRepository.findAllGenres();
            List<Director> directors = DirectorRepository.findAllDirectors();

            // 3) Parsing et adding the films
            List<Movie> movies = RssParser.parse(rssStream, genres, directors);
            int count = 0;
            for (Movie m : movies) {
                MovieRepository.createMovie(m);
                count++;
            }

            JOptionPane.showMessageDialog(
                this,
                "Loaded " + count + " movies from RSS",
                "Info",
                JOptionPane.INFORMATION_MESSAGE
            );

            // 4) refresh the movie tab
            MoviePanel mp = (MoviePanel)((JTabbedPane)getContentPane()).getComponentAt(0);
            mp.loadData();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error loading RSS:\n" + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } finally {
            if (rssStream != null) {
                try { rssStream.close(); } catch (IOException ignored) {}
            }
        }
    }

    /**
     * API demonstration:
     * filtering the Sci-Fi movies and display ordered
     */
    private void demonstrateFunctionalApi() {
        try {
            List<Movie> allMovies = MovieRepository.findAllMovies();
            List<Genre> allGenres = GenreRepository.findAllGenres();

            List<String> sciFiTitles = allMovies.stream()
                .filter(m -> {
                    try {
                        String genreName = allGenres.stream()
                            .filter(g -> g.getId() == m.getGenreId())
                            .findFirst()
                            .map(Genre::getName)
                            .orElse("");
                        return "Sci-Fi".equalsIgnoreCase(genreName);
                    } catch (Exception ex) {
                        return false;
                    }
                })
                .sorted(Comparator.comparing(Movie::getTitle))
                .map(Movie::getTitle)
                .collect(Collectors.toList());

            System.out.println("=== Sci-Fi Movies ===");
            sciFiTitles.forEach(t -> System.out.println("• " + t));

            String message = sciFiTitles.isEmpty()
                ? "No Sci-Fi movies found."
                : "Sci-Fi movies:\n" + String.join("\n", sciFiTitles);
            JOptionPane.showMessageDialog(
                this,
                message,
                "Functional API Demo",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error in functional API demo:\n" + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
