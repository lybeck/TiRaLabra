/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.HashMap;

/**
 *
 * @author Lasse
 */
class CommandProcesser {

    private OperationsParser parser;

    CommandProcesser(HashMap<String, Variable> variables) {
        parser = new OperationsParser(variables);
    }
    
    
    
    Variable processCommand(String[] split) {
        String operation = split[0].toLowerCase().trim();
        String params = split[1].trim();
        try {
            switch (operation.toLowerCase()) {
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
                case "add":
                    return parser.add(params);
                case "sub":
                    return parser.sub(params);
                case "mul":
                    return parser.mul(params);
                case "muln":
                    return new Variable(parser.mulN(params));
                case "muls":
                    return new Variable(parser.mulS(params));
                case "pow":
                    return parser.pow(params);
                case "transpose":
                    return new Variable(parser.transpose(params));
                case "det":
                    return new Variable(parser.det(params));
                case "inv":
                    return new Variable(parser.inv(params));
                case "lu":
                    return new Variable(parser.lu(params));
                case "getl":
                    return new Variable(parser.getL(params));
                case "getu":
                    return new Variable(parser.getU(params));
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
