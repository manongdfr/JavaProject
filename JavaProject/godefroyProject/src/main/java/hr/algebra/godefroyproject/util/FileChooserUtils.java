/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.godefroyproject.util;

import javax.swing.*;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 *
 * @author manongodefroy
 */

public class FileChooserUtils {

    /**
     * Show a file chooser, copy the selected file into assets/images,
     * and return its filename, or null if cancelled.
     */
    public static String chooseAndCopyImage(Component parent) throws IOException {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Select Image for Movie");
        if (fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        File src = fc.getSelectedFile();
        Path imagesDir = Paths.get("assets/images");
        if (!Files.exists(imagesDir)) {
            Files.createDirectories(imagesDir);
        }
        Path dest = imagesDir.resolve(src.getName());
        Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        return src.getName();
    }
}
