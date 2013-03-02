/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra;

import ui.FileRunner;
import java.util.Scanner;
import speedTests.StrassenComparison;
import ui.UserInterface;
import ui.Solver;

/**
 *
 * @author llybeck
 */
class TaskSelector {

    private Scanner scanner;

    public TaskSelector() {
        scanner = new Scanner(System.in);
    }

    public void run() {
        while (newTaskSelected());
    }

    private boolean newTaskSelected() {
        String input;
        while (true) {
            System.out.println();

            System.out.println("Available programs:");

            System.out.println();
            System.out.println("\t1: Matrix calculator");
            System.out.println("\t2: Run file");
            System.out.println("\t3: Equation solver");
            System.out.println();
            System.out.println("\t0: Quits the program");
            System.out.println();

            System.out.println("Select program to run:");

            System.out.print(">> ");
            input = scanner.nextLine();

            switch (input) {
                case "1":
                    UserInterface.getInstance().run();
                    return true;
                case "2":
                    new FileRunner().run();
                    return true;
                case "3":
                    new Solver().run();
                    return true;
                case "0":
                    return false;
                default:
                    System.err.println("Unrecognised command: '" + input + "'");
            }

        }
    }
}
