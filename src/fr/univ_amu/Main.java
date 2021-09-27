package fr.univ_amu;

import fr.univ_amu.utils.Configuration;
import fr.univ_amu.utils.TextTransformation;
import fr.univ_amu.view.ExternalPanelView;
import fr.univ_amu.view.InternalPanelView;

public class Main {

    public static void main(String[] args) {
        InternalPanelView internalPanelView = new InternalPanelView();

        new Thread(() -> {
            while (!Thread.interrupted()) {
                for (int i = 0; i < Configuration.MAX_LEVEL; i++) {
                    internalPanelView.setScreenText("↑" + TextTransformation.intTwoDigits(i));
                    internalPanelView.illuminateButton(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (int i = Configuration.MAX_LEVEL; i >= 0; i--) {
                    internalPanelView.setScreenText("↓" + TextTransformation.intTwoDigits(i));
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
