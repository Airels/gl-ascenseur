package fr.univ_amu.view;

import javax.swing.*;

import java.awt.*;

import static fr.univ_amu.utils.Configuration.MAX_LEVEL;

/**
 * Visual representation of the internal panel command control of the elevator
 * @author VIZCAINO Yohan
 */
public class InternalPanelView {

    public static final Color defaultButtonColor = new JButton().getBackground();

    private JFrame window;
    private JPanel grid, buttonsGrid;
    private JLabel visual;
    private JButton[] buttons;


    /**
     * Default constructor
     */
    public InternalPanelView() {
        buttons = new JButton[MAX_LEVEL+3];

        window = new JFrame("Internal Panel");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());

        grid = new JPanel();
        GridBagConstraints gridConstraints = new GridBagConstraints();
        grid.setLayout(new GridBagLayout());

        visual = new JLabel("- 0 0");
        visual.setHorizontalAlignment(SwingConstants.CENTER);
        visual.setFont(new Font("Consolas", Font.BOLD, 50));
        visual.setForeground(Color.RED);
        gridConstraints.gridwidth = GridBagConstraints.REMAINDER;
        grid.add(visual, gridConstraints);

        buttonsGrid = new JPanel();
        buttonsGrid.setLayout(new GridLayout((MAX_LEVEL+3)/2, 2));

        for (int i = MAX_LEVEL; i >= 0; i--) {
            JButton b = new JButton(Integer.toString(i));
            b.setName(Integer.toString(i));
            b.addActionListener((e) -> System.out.println("Hello " + b.getName()));
            buttonsGrid.add(b);
            buttons[i] = b;
        }

        JButton emergency = new JButton("BREAK");
        emergency.setName("BREAK");
        emergency.addActionListener((e) -> System.out.println("Hello " + emergency.getName()));
        buttonsGrid.add(emergency);
        buttons[MAX_LEVEL+1] = emergency;

        JButton reset = new JButton("RESET");
        reset.setName("RESET");
        reset.addActionListener((e) -> System.out.println("Hello " + reset.getName()));
        buttonsGrid.add(reset);
        buttons[MAX_LEVEL+2] = reset;

        grid.add(buttonsGrid, gridConstraints);

        window.add(grid, BorderLayout.CENTER);
        window.setLocationRelativeTo(null);
        window.pack();
        window.setVisible(true);
    }

    /**
     * Update screen text
     * @param message
     */
    public void setScreenText(String message) {
        if (message.length() > 3) throw new IllegalArgumentException("Message length can't exceed 3 characters");
        visual.setText(message.replace("", " ").trim());
    }

    /**
     * To light up a button
     *
     * @param id level or MAX_LEVEL+1 for emergency, +2 for reset
     */
    public void illuminateButton(int id) {
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

}