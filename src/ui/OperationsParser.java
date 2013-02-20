/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.HashMap;
import matrix.Matrix;

/**
 *
 * @author Lasse
 */
class OperationsParser {

    private HashMap<String, Matrix> variables;

    OperationsParser() {
        variables = UserInterface.getInstance().getVariables();
    }

    private boolean variablesFound(String[] vars) {
        for (String var : vars) {
            if (!variables.containsKey(var)) {
                System.err.println("Cannot find variable '" + var + "'!");
                return false;
            }
        }
        return true;
    }

    private boolean variableFound(String var) {
        return variablesFound(new String[]{var});
    }

    Matrix scale(String params) {
        String[] split = ParseUtils.getParameterSplit(params);
        if (split.length != 2) {
            ParseUtils.wrongNumberOfParametersInFunction("scale");
        }

        Matrix m;
        double k;
        if (ParseUtils.isDouble(split[0]) && variables.containsKey(split[1])) {
            m = variables.get(split[1]);
            k = ParseUtils.parseDouble(split[0]);
        } else if (ParseUtils.isDouble(split[1]) && variables.containsKey(split[0])) {
            m = variables.get(split[0]);
            k = ParseUtils.parseDouble(split[1]);
        } else {
            if (!ParseUtils.isDouble(split[0]) && !variables.containsKey(split[0])) {
                System.err.println("Parameter 1 not a variable or number!");
            } else if (!ParseUtils.isDouble(split[1]) && !variables.containsKey(split[1])) {
                System.err.println("Parameter 2 not a variable or number!");
            } else if (!ParseUtils.isDouble(split[0]) && !ParseUtils.isDouble(split[1])) {
                System.err.println("None of the parameters is a number!");
            } else if (!variables.containsKey(split[0]) && !variables.containsKey(split[1])) {
                System.err.println("None of the parameters is a variable!");
            }
            return null;
        }
        return m.scale(k);
    }

    Matrix add(String params) {
        String[] split = ParseUtils.getParameterSplit(params);
        if (split.length != 2) {
            ParseUtils.wrongNumberOfParametersInFunction("add");
        }
        if (!variablesFound(split)) {
            return null;
        }
        Matrix m1 = variables.get(split[0]);
        Matrix m2 = variables.get(split[1]);

        return m1.add(m2);
    }

    Matrix sub(String params) {
        String[] split = ParseUtils.getParameterSplit(params);
        if (split.length != 2) {
            ParseUtils.wrongNumberOfParametersInFunction("sub");
        }
        if (!variablesFound(split)) {
            return null;
        }
        Matrix m1 = variables.get(split[0]);
        Matrix m2 = variables.get(split[1]);

        return m1.sub(m2);
    }

    Matrix mul(String params) {
        String[] split = ParseUtils.getParameterSplit(params);
        if (split.length != 2) {
            ParseUtils.wrongNumberOfParametersInFunction("mul");
        }
        if (!variablesFound(split)) {
            return null;
        }
        Matrix m1 = variables.get(split[0]);
        Matrix m2 = variables.get(split[1]);

        return m1.mul(m2);
    }

    Matrix mulN(String params) {
        String[] split = ParseUtils.getParameterSplit(params);
        if (split.length != 2) {
            ParseUtils.wrongNumberOfParametersInFunction("mulN");
        }
        if (!variablesFound(split)) {
            return null;
        }
        Matrix m1 = variables.get(split[0]);
        Matrix m2 = variables.get(split[1]);

        return m1.mulNaive(m2);
    }

    Matrix mulS(String params) {
        String[] split = ParseUtils.getParameterSplit(params);
        if (split.length != 2) {
            ParseUtils.wrongNumberOfParametersInFunction("mulS");
        }
        if (!variablesFound(split)) {
            return null;
        }
        Matrix m1 = variables.get(split[0]);
        Matrix m2 = variables.get(split[1]);

        return m1.mulStrassen(m2);
    }

    Matrix pow(String params) {
        String[] split = ParseUtils.getParameterSplit(params);
        if (split.length != 2) {
            ParseUtils.wrongNumberOfParametersInFunction("pow");
        }
        if (!variableFound(split[0])) {
            return null;
        }
        if (!ParseUtils.isNonNegativeInteger(split[1])) {
            System.err.println("Parameter 2 not a positive integer!");
            return null;
        }
        Matrix m = variables.get(split[0]);
        int e = Integer.parseInt(split[1]);
        return m.pow(e);
    }

    Matrix transpose(String params) {
        String[] split = ParseUtils.getParameterSplit(params);
        if (split.length != 1) {
            ParseUtils.wrongNumberOfParametersInFunction("transpose");
        }
        if (!variableFound(split[0])) {
            return null;
        }
        return variables.get(split[0]).transpose();
    }

    Matrix det(String params) {
        String[] split = ParseUtils.getParameterSplit(params);
        if (split.length != 1) {
            ParseUtils.wrongNumberOfParametersInFunction("det");
        }
        if (!variableFound(split[0])) {
            return null;
        }
        double det = variables.get(split[0]).det();
        return new Matrix(new double[][]{{det}});
    }

    Matrix inv(String params) {
        String[] split = ParseUtils.getParameterSplit(params);
        if (split.length != 1) {
            ParseUtils.wrongNumberOfParametersInFunction("inv");
        }
        if (!variableFound(split[0])) {
            return null;
        }
        return variables.get(split[0]).inv();
    }

    Matrix lu(String params) {
        String[] split = ParseUtils.getParameterSplit(params);
        if (split.length != 2) {
            ParseUtils.wrongNumberOfParametersInFunction("lu");
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
