package fr.univ_amu;

import fr.univ_amu.model.Direction;
import fr.univ_amu.model.Movement;
import fr.univ_amu.utils.Configuration;
import fr.univ_amu.view.ExternalPanelView;
import fr.univ_amu.view.InternalPanelView;

public class Main {

    public static void main(String[] args) {


        // ---

        InternalPanelView internalPanelView = new InternalPanelView();

        new Thread(() -> {
            while (!Thread.interrupted()) {

                internalPanelView.setMovement(Movement.UP);
                for (int i = 0; i <= Configuration.MAX_LEVEL; i++) {
                    internalPanelView.setLevel(i);
                    internalPanelView.illuminateButton(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                internalPanelView.setMovement(Movement.DOWN);
                for (int i = Configuration.MAX_LEVEL; i >= 0; i--) {
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

        new Thread(() -> {
            while (!Thread.interrupted()) {
                for (int i = 0; i <= Configuration.MAX_LEVEL; i++) {
                    externalPanelView.setButtonLight(i, Direction.UP, true);
                    externalPanelView.setButtonLight(i, Direction.DOWN, true);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (int i = Configuration.MAX_LEVEL; i >= 0; i--) {
                    externalPanelView.setButtonLight(i, Direction.UP, false);
                    externalPanelView.setButtonLight(i, Direction.DOWN, false);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
