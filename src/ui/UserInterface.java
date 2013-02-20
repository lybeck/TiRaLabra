/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author lasse
 */
public class UserInterface {

    private Scanner scanner;
    private CommandParser parser;

    private UserInterface() {
        scanner = new Scanner(System.in);
        parser = new CommandParser();
        Locale.setDefault(Locale.US);
    }

    public static UserInterface getInstance() {
        return UserInterfaceHolder.INSTANCE;
    }

    private static class UserInterfaceHolder {

        private static final UserInterface INSTANCE = new UserInterface();
    }

    HashMap<String, Variable> getVariables() {
        return parser.getVariables();
    }

    public void run() {
        parser.ClearVariables();
        printWelcome();
        while (chooseAction());
        printExit();
    }

    private void printWelcome() {
        System.out.println("Welcome! For instructions type \"help\".");
    }

    private void printExit() {
        System.out.println("Bye bye!");
    }

    private boolean chooseAction() {
        System.out.print(">> ");
        String command = scanner.nextLine().trim();
        if (isExitCommand(command.toLowerCase())) {
            return false;
        }

        parser.parseCommand(command);

        return true;
    }

    private boolean isExitCommand(String command) {
        return command.equals("exit") || command.equals("quit");
    }
}
