package fr.univ_amu.view;

import elevator.IPanelSimulator;
import fr.univ_amu.controller.InternalPanelController;
import fr.univ_amu.model.ButtonType;
import fr.univ_amu.model.Movement;
import fr.univ_amu.utils.Configuration;
import fr.univ_amu.utils.TextTransformation;

import javax.swing.*;

import java.awt.*;

import static fr.univ_amu.utils.Configuration.MAX_LEVEL;

/**
 * Visual representation of the internal panel command control of the elevator
 * @author VIZCAINO Yohan
 */
public class InternalPanelView implements Runnable {

    public static final Color defaultButtonColor = new JButton().getBackground();

    private JFrame window;
    private JPanel grid, buttonsGrid;
    private JLabel visual;
    private JButton[] buttons;
    private boolean inEmergency;

    private IPanelSimulator panelSimulator;

    private static InternalPanelView internalPanelView;

    /**
     * Default constructor
     */
    public InternalPanelView(IPanelSimulator panelSimulator) {
        internalPanelView = this;

        this.panelSimulator = panelSimulator;
        inEmergency = false;

        buttons = new JButton[MAX_LEVEL+3];

        window = new JFrame("Internal Panel");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());

        grid = new JPanel();
        GridBagConstraints gridConstraints = new GridBagConstraints();
        grid.setLayout(new GridBagLayout());

        visual = new JLabel("INIT");
        visual.setHorizontalAlignment(SwingConstants.CENTER);
        visual.setFont(new Font("Consolas", Font.BOLD, 50));
        visual.setForeground(Color.RED);
        gridConstraints.gridwidth = GridBagConstraints.REMAINDER;
        grid.add(visual, gridConstraints);

        buttonsGrid = new JPanel();
        buttonsGrid.setLayout(new GridLayout(0, 2));

        if (MAX_LEVEL % 2 == 0) {
            JButton uselessButton = new JButton();
            uselessButton.setEnabled(false);
            // uselessButton.setVisible(false);
            buttonsGrid.add(uselessButton);
        }

        for (int i = MAX_LEVEL; i >= 0; i--) {
            JButton b = new JButton(Integer.toString(i));
            b.setName(Integer.toString(i));
            b.addActionListener(new InternalPanelController(panelSimulator, i));
            buttonsGrid.add(b);
            buttons[i] = b;
        }

        JButton emergency = new JButton(ButtonType.BREAK.toString());
        emergency.addActionListener(new InternalPanelController(panelSimulator, ButtonType.BREAK));
        buttonsGrid.add(emergency);
        buttons[MAX_LEVEL+1] = emergency;

        JButton reset = new JButton(ButtonType.RESET.toString());
        reset.addActionListener(new InternalPanelController(panelSimulator, ButtonType.RESET));
        buttonsGrid.add(reset);
        buttons[MAX_LEVEL+2] = reset;

        JScrollPane scroll = new JScrollPane(buttonsGrid, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setEnabled(true);
        scroll.setPreferredSize(new Dimension(200, 300));

        grid.add(scroll, gridConstraints);

        window.add(grid, BorderLayout.CENTER);
        window.setLocationByPlatform(true);
        window.pack();
        window.setVisible(true);
    }

    /**
     * To light up a button
     *
     * @param id level or MAX_LEVEL+1 for emergency, +2 for reset
     */
    public void illuminateButton(int id) {
        if (inEmergency) return;

        if (id >= buttons.length) throw new IllegalArgumentException("Unknown button ID: " + id);
        if (id == MAX_LEVEL+1)
            buttons[id].setBackground(Color.RED);
        else
            buttons[id].setBackground(Color.CYAN);
    }

    /**
     * To switch off light of a button
     *
     * @param id level or MAX_LEVEL+1 for emergency, +2 for reset
     */
    public void switchOffButton(int id) {
        if (id >= buttons.length) throw new IllegalArgumentException("Unknown button ID: " + id);
        buttons[id].setBackground(defaultButtonColor);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            for (int i = 0; i <= MAX_LEVEL; i++) {
                if (panelSimulator.getFloorLight(i))
                    illuminateButton(i);
                else
                    switchOffButton(i);

                visual.setText(panelSimulator.getMessage().replace("", " ").trim());
            }
            try {
                Thread.sleep(Configuration.FRAME_RATE_GUI);
            } catch (InterruptedException ignored) {}
        }
    }

    public static InternalPanelView getInstance() {
        return internalPanelView;
    }
}