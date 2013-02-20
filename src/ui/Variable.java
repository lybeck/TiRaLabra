/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import matrix.LU;
import matrix.Matrix;

/**
 *
 * @author Lasse
 */
class Variable {

    private VarType type;
    private Object object;

    Variable(Object object) {
        this.object = object;
        if (object instanceof Matrix) {
            type = VarType.MATRIX;
        } else if (object instanceof LU) {
            type = VarType.LU;
        } else if (object instanceof Double) {
            type = VarType.NUMBER;
        } else {
            throw new IllegalArgumentException("Variable must be of type Matrix, LU or Double!");
        }
    }

    VarType getType() {
        return type;
    }

    Object getObject() {
        return object;
    }

    static enum VarType {

        MATRIX, LU, NUMBER;

        @Override
        public String toString() {
            switch (this) {
                case MATRIX:
                    return "Matrix";
                case LU:
                    return "LU";
                case NUMBER:
                    return "Number";
                default:
                    return "(none)";
            }
        }
    }
}
