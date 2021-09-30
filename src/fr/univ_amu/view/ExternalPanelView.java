package fr.univ_amu.view;

import fr.univ_amu.controller.ExternalPanelController;
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

    private static JFrame window;
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
            bUp.addActionListener(new ExternalPanelController(i, Direction.UP));

            JButton bDown = new JButton("↓");
            bDown.addActionListener(new ExternalPanelController(i, Direction.DOWN));

            JLabel label = new JLabel("Level: " + finalI);

            panel.add(bUp);
            panel.add(bDown);
            panel.add(label);
            grid.add(panel);

            buttonsUp[i] = bUp;
            buttonsDown[i] = bDown;
        }

        JScrollPane scroll = new JScrollPane(grid, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setEnabled(true);
        scroll.setPreferredSize(new Dimension(200, 400));

        window.add(scroll);
        window.setLocationRelativeTo(InternalPanelView.getJFrame());
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

    /**
     * Getter of current external panels window
     * @return JFrame of external view
     */
    public static JFrame getJFrame() {
        return window;
    }
}