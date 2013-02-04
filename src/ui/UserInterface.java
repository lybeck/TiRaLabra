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

        split[0] = split[0].toLowerCase().trim();
        split[1] = split[1].toLowerCase().trim();

        Matrix result = null;

        switch (split[0]) {
            case "parse":
                result = parseMatrix(split[1]);
                break;
        }

        if (print && result != null) {
            result.print();
        }
        return result;
    }

    private Matrix parseMatrix(String string) {
        if (!string.startsWith("[") || !string.endsWith("]")) {
            System.out.println("Unparsable string!");
            return null;
        }
        string = string.substring(1, string.length() - 1).trim();
        String[] split = string.split(";");
        int rows = split.length;
        int cols = countColumns(split);
        if (cols == -1) {
            System.out.println("Unparsable string!");
            return null;
        }
        double[][] m = new double[rows][cols];
        try {
            for (int i = 0; i < rows; i++) {
                split[i] = split[i].replaceAll(",", " ");
                Scanner row = new Scanner(split[i]);
                for (int j = 0; j < cols; j++) {
                    m[i][j] = row.nextDouble();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Unparsable string!");
            return null;
        }
        return new Matrix(m);
    }

    private int countColumns(String[] split) {
        String regex = ",|\\s";
        int cols = split[0].split(regex).length;
        for (int i = 0; i < split.length; i++) {
            if (cols != split[i].split(regex).length) {
                return -1;
            }
        }
        return cols;
    }

    private void printSplit(String[] split) {
        System.out.println("Split:");
        for (int i = 0; i < split.length; i++) {
            System.out.println(i + ":");
            System.out.println(split[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new UserInterface().performVariableCommand("parse([0 2 3;2 1 1;5 2 5])");
    }
}
