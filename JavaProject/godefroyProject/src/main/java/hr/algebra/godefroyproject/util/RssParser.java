/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.util;

import hr.algebra.godefroyproject.model.Movie;
import hr.algebra.godefroyproject.model.Genre;
import hr.algebra.godefroyproject.model.Director;
import hr.algebra.godefroyproject.repository.GenreRepository;
import hr.algebra.godefroyproject.repository.DirectorRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;


/**
 *
 * @author manongodefroy
 */

public class RssParser {

    /**
     * 
     * Parse RSS from InputSream
     * return => movie list
     * create the genre & directors if needed
     */
    
    public static List<Movie> parse(InputStream rssStream,
                                    List<Genre> genres,
                                    List<Director> directors) throws Exception {

        Set<String> seenTitles = new HashSet<>();
        List<Movie> movies = new ArrayList<>();

        // prep directories
        Path assetsDir = Paths.get("assets/images");
        if (!Files.exists(assetsDir)) Files.createDirectories(assetsDir);

        DocumentBuilder db = DocumentBuilderFactory
                                .newInstance()
                                .newDocumentBuilder();
        Document doc = db.parse(rssStream);
        NodeList items = doc.getElementsByTagName("item");

        for (int i = 0; i < items.getLength(); i++) {
            Element it = (Element) items.item(i);
            String title    = getText(it, "title");
            if (!seenTitles.add(title)) continue;    // filtre doublons

            String desc      = getText(it, "description");
            int year         = Integer.parseInt(getText(it, "year"));
            String genreNm   = getText(it, "category");
            String dirNm     = getText(it, "director");
            String imgUrl    = getText(it, "image");

            // genre
            Genre g = genres.stream()
                .filter(x -> x.getName().equalsIgnoreCase(genreNm))
                .findFirst()
                .orElseGet(() -> {
                    try {
                        Genre ng = new Genre(genreNm);
                        GenreRepository.createGenre(ng);
                        genres.add(ng);
                        return ng;
                    } catch (Exception ex) {
                        throw new RuntimeException("Failed to create genre '"+genreNm+"'", ex);
                    }
                });

            // directors
            Director d = directors.stream()
                .filter(x -> (x.getFirstName()+" "+x.getLastName())
                              .equalsIgnoreCase(dirNm))
                .findFirst()
                .orElseGet(() -> {
                    try {
                        String[] parts = dirNm.split(" ", 2);
                        Director nd = new Director(
                            parts[0],
                            parts.length > 1 ? parts[1] : ""
                        );
                        DirectorRepository.createDirector(nd);
                        directors.add(nd);
                        return nd;
                    } catch (Exception ex) {
                        throw new RuntimeException("Failed to create director '"+dirNm+"'", ex);
                    }
                });

            // dl img
            String fileName = "";
            if (!imgUrl.isBlank()) {
                try {
                    URL u = new URL(imgUrl);
                    HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);

                    if (conn.getResponseCode() == 200) {
                        fileName = Paths.get(u.getPath())
                                        .getFileName().toString();
                        try (InputStream in = conn.getInputStream()) {
                            Files.copy(in,
                                       assetsDir.resolve(fileName),
                                       StandardCopyOption.REPLACE_EXISTING);
                        }
                    } else {
                        System.err.println("Skip image, HTTP "
                                           + conn.getResponseCode()
                                           + " for URL: " + imgUrl);
                    }
                    conn.disconnect();
                } catch (Exception ex) {
                    System.err.println("Error downloading "
                                       + imgUrl + " → " + ex.getMessage());
                }
            }

            // new movie as object => add
            movies.add(new Movie(
                title,
                desc,
                year,
                g.getId(),
                d.getId(),
                fileName
            ));
        }

        return movies;
    }

    private static String getText(Element parent, String tag) {
        NodeList nl = parent.getElementsByTagName(tag);
        return nl.getLength() > 0
            ? nl.item(0).getTextContent().trim()
            : "";
    }
}
