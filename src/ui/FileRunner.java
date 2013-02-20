/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lasse
 */
public class FileRunner {

    private Scanner scanner;
    private String path;

    public FileRunner() {
        scanner = new Scanner(System.in);
        initPath();
        Locale.setDefault(Locale.US);
    }

    public void run() {
        while (newCommand());
    }

    private void initPath() {
        path = FileRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (path.endsWith(".jar")) {
            // Change path to the folder with the jar.
            String[] split = path.split("/");
            int nameLen = split[split.length - 1].length();
            int endIndex = path.length() - nameLen;
            path = path.substring(0, endIndex);
        }
        path += "files/";
    }

    private void printFiles() {
        File folder = new File(path);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("The directory " + path + " does not exist!");
            return;
        }
        System.out.println();
        System.out.println("Available files:");
        int files = printFilesInFolder(folder);
        System.out.println("A total of " + files + " files found.");
        System.out.println();
    }

    private int printFilesInFolder(File folder) {
        int files = 0;
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                files += printFilesInFolder(file);
            } else {
                String pathToFile = file.getPath();
                if (pathToFile.endsWith(".txt")) {
                    pathToFile = pathToFile.substring(path.length() - 1).replaceAll("\\\\", "/");
                    System.out.println("\t" + pathToFile);
                    ++files;
                }
            }
        }
        return files;
    }

    private boolean newCommand() {
        System.out.println();
        System.out.println("Select file to run or type 'files' to print out available files. Empty line exits.");
        System.out.print(">> ");
        String line = scanner.nextLine().trim().toLowerCase();
        if (line.isEmpty()) {
            return false;
        }
        if (line.equals("files")) {
            printFiles();
        } else {
            if (existsFile(line)) {
                runFile(line);
            }
        }
        return true;
    }

    private boolean existsFile(String line) {
        if (!line.endsWith(".txt")) {
            System.err.println("File must have a .txt ending!");
            return false;
        }
        String absPath = path + line;
        File file = new File(absPath);
        if (!file.exists()) {
            System.err.println("File " + line + " does not exist!");
            return false;
        }
        return true;
    }

    private void runFile(String pathToFile) {
        String absPath = path + pathToFile;
        Scanner file;
        try {
            file = new Scanner(new File(absPath));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileRunner.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        System.out.println();
        System.out.println("Run started.");
        System.out.println("--------------------------------------------------");
        System.out.println();

        double time = executeFile(file);

        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("Run finished. Total time " + time + "s.");
        System.out.println();
    }

    private double executeFile(Scanner file) {
        CommandParser parser = new CommandParser();
        long start = System.currentTimeMillis();
        String line;
        int lineNumber = 0;
        while (file.hasNextLine()) {
            ++lineNumber;
            line = file.nextLine().trim();
            if (!(line.isEmpty() || line.charAt(0) == '#')) {
                if (!parser.performVariableCommand(line)) {
                    System.err.println("Error on line " + lineNumber + ". Run terminated.");
                    break;
                }
            }
        }
        return (System.currentTimeMillis() - start) * 1.0 / 1000;
    }
}
