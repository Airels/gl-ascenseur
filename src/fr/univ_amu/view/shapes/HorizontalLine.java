package fr.univ_amu.view.shapes;

import javax.swing.*;
import java.awt.*;

/**
 * Graphical horizontal line
 *
 * @author VIZCAINO Yohan
 */
public class HorizontalLine extends JPanel {

    private final Color color;

    /**
     * Default constructor
     *
     * @param color of horizontal line
     */
    public HorizontalLine(Color color) {
        super(true);
        this.color = color;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Dimension dimension = this.getSize();

        g.setColor(color);
        g.drawLine(0, 0, dimension.width, 0);
    }
}
