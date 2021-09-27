package fr.univ_amu;

import fr.univ_amu.model.Movement;
import fr.univ_amu.utils.Configuration;
import fr.univ_amu.utils.TextTransformation;
import fr.univ_amu.view.ExternalPanelView;
import fr.univ_amu.view.InternalPanelView;

public class Main {

    public static void main(String[] args) {
        InternalPanelView internalPanelView = new InternalPanelView();

        new Thread(() -> {
            while (!Thread.interrupted()) {
                for (int i = 0; i <= Configuration.MAX_LEVEL; i++) {
                    internalPanelView.setMovement(Movement.UP);
                    internalPanelView.setLevel(i);
                    internalPanelView.illuminateButton(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (int i = Configuration.MAX_LEVEL; i >= 0; i--) {
                    internalPanelView.setMovement(Movement.DOWN);
                    internalPanelView.setLevel(i);
                    internalPanelView.switchOffButton(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        ExternalPanelView externalPanelView = new ExternalPanelView();
    }
}
