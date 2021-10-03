package fr.univ_amu.view;

import elevator.IElevatorSimulator;
import fr.univ_amu.utils.Configuration;
import fr.univ_amu.view.shapes.HorizontalLine;
import fr.univ_amu.view.shapes.Rectangle;

import javax.swing.*;
import java.awt.*;

/**
 * Representation of moving elevator, by using simulation handler
 * @author VIZCAINO Yohan
 */
public class ElevatorRepresentation implements Runnable {

    private static JFrame window;
    private JPanel grid;
    private IElevatorSimulator elevatorSimulator;
    private Rectangle elevator;

    public ElevatorRepresentation(IElevatorSimulator elevatorSimulator) {
        this.elevatorSimulator = elevatorSimulator;

        window = new JFrame("Elevator simulator");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());

        grid = new JPanel();
        grid.setLayout(new GridLayout(0, 2));

        GridLayout gridLayout = new GridLayout(0, 1);
        JPanel lines = new JPanel();
        lines.setLayout(gridLayout);

        for (int i = 0; i <= Configuration.MAX_LEVEL; i++) {
            lines.add(new HorizontalLine(Color.BLACK));
            lines.add(new JLabel(String.valueOf(Configuration.MAX_LEVEL-i)));
        }

        grid.add(lines);

        elevator = new Rectangle();
        grid.add(elevator);

        window.add(grid, BorderLayout.CENTER);
        window.setLocationByPlatform(true);
        window.setPreferredSize(new Dimension(200, 400));
        window.pack();
        window.setVisible(true);
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            elevator.marginFromBottom(elevatorSimulator.getLevel());
            try {
                Thread.sleep(1000/Configuration.FRAME_RATE_GUI);
            } catch (InterruptedException ignored) { }
        }
    }
}
