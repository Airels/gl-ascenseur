package fr.univ_amu.view;

import elevator.IPanelSimulator;
import fr.univ_amu.controller.ExternalPanelController;
import fr.univ_amu.model.Direction;
import fr.univ_amu.utils.Configuration;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static fr.univ_amu.utils.Configuration.MAX_LEVEL;

/**
 * Visual representation of external panel command control of the elevator
 * @author VIZCAINO Yohan
 */
public class ExternalPanelView implements Runnable {

    private JFrame window;
    private JPanel grid;
    private JButton[] buttonsUp, buttonsDown;

    private IPanelSimulator panelSimulator;

    /**
     * Default constructor
     */
    public ExternalPanelView(IPanelSimulator panelSimulator) {
        this.panelSimulator = panelSimulator;

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
            bUp.addActionListener(new ExternalPanelController(panelSimulator, i, Direction.UP));

            JButton bDown = new JButton("↓");
            bDown.addActionListener(new ExternalPanelController(panelSimulator, i, Direction.DOWN));

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
        window.setLocationByPlatform(true);
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

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            for (int i = 0; i <= MAX_LEVEL; i++) {
                setButtonLight(i, Direction.DOWN, panelSimulator.getDownLight(i));
                setButtonLight(i, Direction.UP, panelSimulator.getUpLight(i));
            }

            try {
                Thread.sleep(Configuration.FRAME_RATE_GUI);
            } catch (InterruptedException ignored) {}
        }
    }
}