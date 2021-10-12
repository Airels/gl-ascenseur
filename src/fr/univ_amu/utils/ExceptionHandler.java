package fr.univ_amu.utils;

import javax.swing.*;
import java.util.Arrays;

/**
 * Exception handler used to print exceptions on screen if critical errors occurs
 */
public class ExceptionHandler {

    /**
     * Print exception to user then exit program
     *
     * @param e exception to show
     */
    public static void showAndExit(Exception e) {
        e.printStackTrace();

        String title = "The Elevator - Catastrophic failure";
        StringBuilder message = new StringBuilder();
        message.append("An unrecoverable error occurred :").append("\n\n")
                .append(e).append("\n")
                .append(Arrays.toString(e.getStackTrace()))
                .append("\n\n")
                .append("Application will now close.");

        JOptionPane.showMessageDialog(null, message.toString(), title, JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
