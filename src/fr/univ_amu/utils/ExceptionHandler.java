package fr.univ_amu.utils;

import javax.swing.*;

/**
 * TODO
 */
public class ExceptionHandler {

    /**
     * TODO
     * @param e
     */
    public static void showAndExit(Exception e) {
        e.printStackTrace();

        StringBuilder message = new StringBuilder();
        message.append(e);
        message.append("\n");
        message.append("Application will now close");

        JOptionPane.showMessageDialog(null, message.toString(), "A critical error occured!", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
