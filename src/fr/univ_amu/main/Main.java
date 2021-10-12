package fr.univ_amu.main;

import elevator.ElevatorSimulator;
import elevator.PanelSimulator;
import fr.univ_amu.model.controlCommand.Supervisor;
import fr.univ_amu.utils.Configuration;
import fr.univ_amu.view.ElevatorRepresentation;
import fr.univ_amu.view.ExternalPanelView;
import fr.univ_amu.view.InternalPanelView;

/**
 * Main entry of the elevator emulator. <br />
 * Init the elevator and its components, and link them using ElevatorSimulator library. <br />
 * <p>
 * Configuration class available to custom the elevator as pleased.
 *
 * @author VIZCAINO Yohan
 * @see Configuration to configure elevator simulation
 */
public class Main {

    /**
     * Main entry of program
     */
    public static void main(String[] args) {
        System.out.println("INIT");

        ElevatorSimulator elevatorSimulator = new ElevatorSimulator(Configuration.MAX_LEVEL, false);
        PanelSimulator panelSimulator = new PanelSimulator(Configuration.MAX_LEVEL);

        // INIT BACKEND
        Supervisor supervisor = new Supervisor(panelSimulator, elevatorSimulator);

        // INIT FRONTEND
        InternalPanelView internalPanelView = new InternalPanelView(panelSimulator);
        ExternalPanelView externalPanelView = new ExternalPanelView(panelSimulator);
        ElevatorRepresentation elevatorRepresentation = new ElevatorRepresentation(elevatorSimulator);

        // Init Threads
        Thread s = new Thread(supervisor);
        s.setName("Supervisor");

        Thread ipv = new Thread(internalPanelView);
        ipv.setName("Internal Panel View");

        Thread epv = new Thread(externalPanelView);
        epv.setName("External Panel View");

        Thread er = new Thread(elevatorRepresentation);
        er.setName("Elevator Representation");

        s.start();
        ipv.start();
        epv.start();
        er.start();

        System.out.println("INIT COMPLETE");
    }
}
