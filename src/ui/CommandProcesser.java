/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import matrix.Matrix;

/**
 *
 * @author Lasse
 */
class CommandProcesser {

    Matrix processCommand(String[] split) {
        String operation = split[0].toLowerCase().trim();
        String params = split[1].toLowerCase().trim();
        try {
            switch (operation) {
                case "parse":
                    return new MatrixParser().parseString(params);
                case "zeros":
                    return new MatrixParser().zeros(params);
                case "ones":
                    return new MatrixParser().ones(params);
                case "eye":
                    return new MatrixParser().eye(params);
                case "rand":
                    return new MatrixParser().rand(params);
                case "randi":
                    return new MatrixParser().randi(params);
                case "scale":
                    return new OperationsParser().scale(params);
                case "add":
                    return new OperationsParser().add(params);
                case "sub":
                    return new OperationsParser().sub(params);
                case "mul":
                    return new OperationsParser().mul(params);
                case "mulN":
                    return new OperationsParser().mulN(params);
                case "mulS":
                    return new OperationsParser().mulS(params);
                case "pow":
                    return new OperationsParser().pow(params);
                case "transpose":
                    return new OperationsParser().transpose(params);
                case "det":
                    return new OperationsParser().det(params);
                case "inv":
                    return new OperationsParser().inv(params);
                case "LU":
                    return new OperationsParser().lu(params);
                default:
                    System.err.println("Unrecognized command.");
            }
        } catch (Exception e) {
            System.err.println("Command failed! Got the following error message:");
            System.err.println(e.getMessage());
        }
        return null;
    }
}
