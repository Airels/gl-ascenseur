package fr.univ_amu.view;

import fr.univ_amu.controller.InternalPanelController;
import fr.univ_amu.model.ButtonType;
import fr.univ_amu.model.Movement;
import fr.univ_amu.utils.TextTransformation;

import javax.swing.*;

import java.awt.*;

import static fr.univ_amu.utils.Configuration.MAX_LEVEL;

/**
 * Visual representation of the internal panel command control of the elevator
 * @author VIZCAINO Yohan
 */
public class InternalPanelView {

    public static final Color defaultButtonColor = new JButton().getBackground();

    private static JFrame window;
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

        visual = new JLabel("-00");
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
            b.addActionListener(new InternalPanelController(i));
            buttonsGrid.add(b);
            buttons[i] = b;
        }

        JButton emergency = new JButton(ButtonType.BREAK.toString());
        emergency.addActionListener(new InternalPanelController(ButtonType.BREAK));
        buttonsGrid.add(emergency);
        buttons[MAX_LEVEL+1] = emergency;

        JButton reset = new JButton(ButtonType.RESET.toString());
        reset.addActionListener(new InternalPanelController(ButtonType.RESET));
        buttonsGrid.add(reset);
        buttons[MAX_LEVEL+2] = reset;

        JScrollPane scroll = new JScrollPane(buttonsGrid, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setEnabled(true);
        scroll.setPreferredSize(new Dimension(200, 300));

        grid.add(scroll, gridConstraints);

        window.add(grid, BorderLayout.CENTER);
        // window.setLocationRelativeTo(null);
        window.pack();
        window.setVisible(true);
    }

    /**
     * Update level shown by internal screen
     * @param level level to show
     */
    public void setLevel(int level) {
        if (level > MAX_LEVEL) throw new IllegalArgumentException("Given level exceed maximum level");

        String newScreenInfo = visual.getText().toCharArray()[0] + TextTransformation.intTwoDigits(level);
        visual.setText(newScreenInfo.replace("", " ").trim());
    }

    /**
     * Update elevator movement on screen
     * @param movement movement to show
     */
    public void setMovement(Movement movement) {
        String movementStr = switch (movement) {
            case UP -> "↑";
            case IDLE -> "-";
            case DOWN -> "↓";
        };

        String newScreenInfo = movementStr + visual.getText().substring(1);
        visual.setText(newScreenInfo.replace("", " ").trim());
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

    /**
     * Getter of current internal panels window
     * @return JFrame of internal view
     */
    public static JFrame getJFrame() {
        return window;
    }
}