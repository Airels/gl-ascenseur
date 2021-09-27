package fr.univ_amu.view;

import fr.univ_amu.utils.Configuration;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Visual representation of external panel command control of the elevator
 * @author VIZCAINO Yohan
 */
public class ExternalPanelView {

    private JFrame window;
    private JPanel grid, buttonsGrid;

    /**
     * Default constructor
     */
    public ExternalPanelView() {
        window = new JFrame("External panels");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());

        grid = new JPanel();
        grid.setLayout(new GridLayout(Configuration.MAX_LEVEL+1, 1));

        for (int i = Configuration.MAX_LEVEL; i >= 0; i--) {
            JPanel panel = new JPanel();

            JButton bUp = new JButton("↑");
            int finalI = i;
            bUp.addActionListener((e) -> System.out.println("Hello ↑ " + finalI));

            JButton bDown = new JButton("↓");
            bDown.addActionListener((e) -> System.out.println("Hello ↓ " + finalI));

            JLabel label = new JLabel("Level: " + finalI);

            panel.add(bUp);
            panel.add(bDown);
            panel.add(label);
            grid.add(panel);
        }

        window.add(grid);
        window.setLocationRelativeTo(null);
        window.pack();
        window.setVisible(true);
    }

    /**
     * 
     */
    private boolean illuminatedUpButton;

    /**
     * 
     */
    private boolean illuminatedDownButton;


    /**
     * Turn on specific light of panel
     * @param direction
     */
    public void setLight(/* Direction direction */) {
        // TODO implement here
    }

    /**
     * Turn off both lights of the panel
     */
    public void resetLights() {
        // TODO implement here
    }

}