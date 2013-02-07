/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import matrix.Matrix;

/**
 *
 * @author lasse
 */
public class UserInterface {

    private Scanner scanner;
    private HashMap<String, Matrix> variables;

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
            case "clear":
                variables.clear();
                break;
            default:
                performVariableCommand(command);
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
            System.out.print(">> ");
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

    private void performVariableCommand(String command) {
        Matrix result;
        String varName = null;
        if (command.contains("=")) {
            String[] split = command.split("=");
            if (split.length > 2) {
                System.out.println("Too many '=' in command");
                System.out.println("Command terminated.");
            }
            varName = split[0].trim();
            if (!variables.containsKey(varName)) {
                System.out.println("No variable named '" + varName + "' found! Check variables with command 'vars'.");
                System.out.println("Command terminanted.");
                return;
            }
            command = split[1];
        }
        result = calculateCommand(command);
        if (varName != null && result != null) {
            variables.put(varName, result);
        }
    }

    private Matrix calculateCommand(String command) {
        if (command.length() < 1) {
            System.out.println("Unrecoginzed command!");
            return null;
        }
        boolean print = !command.endsWith(";");
        if (!print) {
            command = command.substring(0, command.length() - 1).trim();
        }

        if (variables.containsKey(command)) {
            Matrix var = variables.get(command);
            if (print) {
                if (var != null) {
                    var.print();
                } else {
                    System.out.println("(null)");
                }
            }
            return var;
        }

        if (!command.endsWith(")")) {
            System.out.println("Unrecognized command!");
            return null;
        }
        command = command.substring(0, command.length() - 1);

        String[] split = command.split("\\(");

        if (split.length != 2) {
            System.out.println("Unrecognized command!");
            return null;
        }
        
        Matrix result = processCommand(split);

        if (print && result != null) {
            result.print();
        }
        return result;
    }

    private Matrix processCommand(String[] split) {
        split[0] = split[0].toLowerCase().trim();
        split[1] = split[1].toLowerCase().trim();
        switch (split[0]) {
            case "parse":
                return new MatrixParser().parse(split[1]);
        }
        return null;
    }

    private void printSplit(String[] split) {
        System.out.println("Split:");
        for (int i = 0; i < split.length; i++) {
            System.out.println(i + ":");
            System.out.println(split[i]);
        }
        System.out.println();
    }
}
