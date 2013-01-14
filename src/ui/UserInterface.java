/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.Scanner;

/**
 *
 * @author lasse
 */
public class UserInterface {
    
    private Scanner scanner;
    
    public UserInterface() {
        scanner = new Scanner(System.in);
    }
    
    public void run() {
        printWelcome();
        while (chooseAction());
    }

    private void printWelcome() {
        
    }
    
    private void printHelp() {
        
    }

    private boolean chooseAction() {
        String command = scanner.nextLine();
        if(isExitCommand(command)){
            return false;
        }
        
        return true;
    }

    private boolean isExitCommand(String command) {
        command = command.toLowerCase().trim();
        return command.equals("exit") || command.equals("quit");
    }
}
