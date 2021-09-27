package fr.univ_amu.view;

import fr.univ_amu.model.Direction;
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
    private JPanel grid;
    private JButton[] buttonsUp, buttonsDown;

    /**
     * Default constructor
     */
    public ExternalPanelView() {
        buttonsUp = new JButton[Configuration.MAX_LEVEL+1];
        buttonsDown = new JButton[Configuration.MAX_LEVEL+1];

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

            buttonsUp[i] = bUp;
            buttonsDown[i] = bDown;
        }

        window.add(grid);
        window.setLocationRelativeTo(null);
        window.pack();
        window.setVisible(true);
    }

    /**
     * Turns on light on external specific panel for specific direction
     * @param level external panel level
     * @param direction direction to light up/down
     * @param lightOn true to light it up, false to light it down
     */
    public void setButtonLight(int level, Direction direction, boolean lightOn) {
        JButton[] buttons = (direction == Direction.UP) ? buttonsUp : buttonsDown;
        if (lightOn)
            buttons[level].setBackground(Color.CYAN);
        else
            buttons[level].setBackground(InternalPanelView.defaultButtonColor);
    }

}