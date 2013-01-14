/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import matrix.Matrix;

/**
 *
 * @author lasse
 */
public class UserInterface {

    private Scanner scanner;
    private HashMap<String, Matrix> variables;
    private static final String SPLIT_PATTERN = "[\\s\\+\\-\\*\\=]";

    public UserInterface() {
        scanner = new Scanner(System.in);
        variables = new HashMap<>();
    }

    public void run() {
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

    private void printHelp() {
        System.out.println("Help is not yet implemented...");
    }

    private boolean chooseAction() {
        System.out.print(">> ");
        String command = scanner.nextLine().trim().toLowerCase();
        if (isExitCommand(command)) {
            return false;
        }

        switch (command) {
            case "help":
                printHelp();
                break;
            case "new":
                initNewVariable();
                break;
            case "vars":
                printVariableNames();
                break;
            default:
                String[] split = command.split("\\s");
                if (variables.containsKey(split[0])) {
                    performVariableCommand(split);
                } else {
                    System.out.println("Unrecognised command!");
                }
        }

        return true;
    }

    private boolean isExitCommand(String command) {
        return command.equals("exit") || command.equals("quit");
    }

    private void initNewVariable() {
        String varName;
        while (true) {
            System.out.println("Input name for new variable:");
            varName = scanner.nextLine();
            if (varName.length() < 1) {
                System.out.println("Did not initialize new variable...");
            } else if (variables.containsKey(varName)) {
                System.out.println("Variable name already in use!");
            } else if (!isValidVariableName(varName)) {
                System.out.println("Not a valid variable name!");
            } else {
                variables.put(varName, null);
                System.out.println("Initialized new variable with name \"" + varName + "\"");
                break;
            }
        }
    }

    private boolean isValidVariableName(String varName) {
        if (!Character.isLetter(varName.charAt(0))) {
            return false;
        }
        for (int i = 1; i < varName.length(); i++) {
            if (!Character.isLetterOrDigit(varName.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private void printVariableNames() {
        if (variables.isEmpty()) {
            System.out.println("No variables in use.");
            return;
        }
        ArrayList<String> list = new ArrayList<>(variables.keySet());
        Collections.sort(list);
        System.out.println("Variables in use:");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));
            if (variables.get(list.get(i)) == null) {
                System.out.println("\t\t(null)");
            } else {
                System.out.println();
            }
        }
    }

    private void performVariableCommand(String[] split) {
        Matrix var1 = variables.get(split[0]);
        if (split.length == 1) {
            if (var1 != null) {
                var1.print();
            } else {
                System.out.println("(null)");
            }
            return;
        }

        if (!split[1].equals("=")) {
            System.out.println("Variable \"" + split[0] + "\" has a null value! Cannot perform command!");
        }
    }
}
