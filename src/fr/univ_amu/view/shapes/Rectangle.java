package fr.univ_amu.view.shapes;

import fr.univ_amu.utils.Configuration;

import javax.swing.*;
import java.awt.*;

public class Rectangle extends JPanel {

    @Override
    public void paint(Graphics g) {
        Dimension dimension = this.getSize();

        g.setColor(Color.BLACK);
        g.fillRect(0, dimension.height - (dimension.height/(Configuration.MAX_LEVEL+1)), dimension.width, dimension.height / (Configuration.MAX_LEVEL+1));
    }

    public void marginFromBottom(double y) {
        Dimension dimension = this.getSize();

        double newHeight = y * dimension.getHeight() / (Configuration.MAX_LEVEL+1);
        this.setLocation(this.getX(), (int) -newHeight);
    }
}
