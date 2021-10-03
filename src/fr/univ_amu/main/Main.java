package fr.univ_amu.main;

import elevator.*;
import fr.univ_amu.model.Direction;
import fr.univ_amu.model.Movement;
import fr.univ_amu.model.controlCommand.Supervisor;
import fr.univ_amu.model.exceptions.UnhandledStrategyException;
import fr.univ_amu.utils.Configuration;
import fr.univ_amu.utils.ExceptionHandler;
import fr.univ_amu.utils.SatisfactionStrategy;
import fr.univ_amu.view.ElevatorRepresentation;
import fr.univ_amu.view.ExternalPanelView;
import fr.univ_amu.view.InternalPanelView;

public class Main {

    public static void main(String[] args) {
        ElevatorSimulator elevatorSimulator = new ElevatorSimulator(Configuration.MAX_LEVEL, false);
        PanelSimulator panelSimulator = new PanelSimulator(Configuration.MAX_LEVEL);

        // INIT BACKEND
        Supervisor supervisor = new Supervisor(panelSimulator, elevatorSimulator);

        // INIT FRONTEND
        InternalPanelView internalPanelView = new InternalPanelView(panelSimulator);
        ExternalPanelView externalPanelView = new ExternalPanelView(panelSimulator);
        ElevatorRepresentation elevatorRepresentation = new ElevatorRepresentation(elevatorSimulator);

        new Thread(supervisor).start();
        new Thread(internalPanelView).start();
        new Thread(externalPanelView).start();
        new Thread(elevatorRepresentation).start();
    }

    /*
    public void testInterface() throws InterruptedException {

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

        IElevatorSimulator elevatorSimulator = new ElevatorSimulator(Configuration.MAX_LEVEL, false);
        new Thread(new ElevatorRepresentation(elevatorSimulator)).start();

        elevatorSimulator.up();
        Thread.sleep(10000);
        elevatorSimulator.stopNext();
        while (elevatorSimulator.getState() != IElevator.State.STOP){
            System.out.println("elevatorSimulator.getState() = " + elevatorSimulator.getState());
        }
        elevatorSimulator.down();
    }

     */
}
