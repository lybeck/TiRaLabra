/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.Scanner;
import matrix.Matrix;

/**
 *
 * @author llybeck
 */
class MatrixParser {

    Variable parseString(String string) {
        string = string.trim();
        String[] split = getParseSplit(string);
        int rows = split.length;
        int cols = countColumns(split);
        if (cols == -1) {
            System.err.println("Unparsable string!");
            return null;
        }
        if (rows == 1 && cols == 1) {
            return new Variable(ParseUtils.parseDouble(split[0]));
        }
        double[][] m = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            split[i] = split[i].replaceAll(",", " ");
            Scanner row = new Scanner(split[i]);
            for (int j = 0; j < cols; j++) {
                m[i][j] = ParseUtils.parseDouble(row.next());
            }
        }
        return new Variable(new Matrix(m));
    }

    private String[] getParseSplit(String string) {
        String[] split = string.split(";");
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].replaceAll("(,|\\s)+", " ");
            split[i] = split[i].trim();
        }
        return split;
    }

    private int countColumns(String[] split) {
        String regex = "\\s";
        int cols = split[0].split(regex).length;
        for (int i = 0; i < split.length; i++) {
            if (cols != split[i].split(regex).length) {
                return -1;
            }
        }
        return cols;
    }

    Variable zeros(String params) {
        int[] ints = ParseUtils.getParameterSplitAsInts(params);
        if (ints.length == 1) {
            if (ints[0] == 1) {
                return new Variable(0);
            }
            return new Variable(new Matrix(ints[0]));
        }
        if (ints.length == 2) {
            if (ints[0] == 1 && ints[1] == 1) {
                return new Variable(0);
            }
            return new Variable((new Matrix(ints[0], ints[1])));
        }
        ParseUtils.wrongNumberOfParametersInFunction("zeros");
        return null;
    }

    Variable ones(String params) {
        int[] ints = ParseUtils.getParameterSplitAsInts(params);
        if (ints.length == 1) {
            if (ints[0] == 1) {
                return new Variable(1);
            }
            return new Variable(Matrix.ones(ints[0]));
        }
        if (ints.length == 2) {
            if (ints[0] == 1 && ints[1] == 1) {
                return new Variable(1);
            }
            return new Variable(Matrix.ones(ints[0], ints[1]));
        }
        ParseUtils.wrongNumberOfParametersInFunction("ones");
        return null;
    }

    Variable eye(String params) {
        int[] ints = ParseUtils.getParameterSplitAsInts(params);
        if (ints.length == 1) {
            if (ints[0] == 1) {
                return new Variable(1);
            }
            return new Variable(Matrix.eye(ints[0]));
        }
        if (ints.length == 2) {
            if (ints[0] == 1 && ints[1] == 1) {
                return new Variable(1);
            }
            return new Variable(Matrix.eye(ints[0], ints[1]));
        }
        ParseUtils.wrongNumberOfParametersInFunction("eye");
        return null;
    }

    Variable rand(String params) {
        int[] ints = ParseUtils.getParameterSplitAsInts(params);
        Matrix m;
        if (ints.length == 1) {
            m = Matrix.rand(ints[0]);
            if (ints[0] == 1) {
                return new Variable(m.at(0, 0));
            }
            return new Variable(m);
        }
        if (ints.length == 2) {
            m = Matrix.rand(ints[0], ints[1]);
            if (ints[0] == 1 && ints[1] == 1) {
                return new Variable(m.at(0, 0));
            }
            return new Variable(m);
        }
        ParseUtils.wrongNumberOfParametersInFunction("rand");
        return null;
    }

    Variable randi(String params) {
        int[] ints = ParseUtils.getParameterSplitAsInts(params);
        Matrix m;
        if (ints.length == 2) {
            m = Matrix.randi(ints[0], ints[1]);
            if (ints[1] == 1) {
                return new Variable(m.at(0, 0));
            }
            return new Variable(m);
        }
        if (ints.length == 3) {
            m = Matrix.randi(ints[0], ints[1], ints[2]);
            if (ints[1] == 1 && ints[2] == 1) {
                return new Variable(m.at(0, 0));
            }
            return new Variable(m);
        }
        if (ints.length == 4) {
            m = Matrix.randi(ints[0], ints[1], ints[2], ints[3]);
            if (ints[2] == 1 && ints[3] == 1) {
                return new Variable(m.at(0, 0));
            }
            return new Variable(m);
        }
        ParseUtils.wrongNumberOfParametersInFunction("rand");
        return null;
    }
}
