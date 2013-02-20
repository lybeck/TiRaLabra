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
        if (object instanceof Matrix) {
            type = VarType.MATRIX;
        } else if (object instanceof LU) {
            type = VarType.LU;
        } else if (object instanceof Double) {
            type = VarType.NUMBER;
        } else {
            type = null;
        }
        this.object = object;
    }

    boolean isMatrix() {
        return type == VarType.MATRIX;
    }

    boolean isNumber() {
        return type == VarType.NUMBER;
    }

    boolean isLU() {
        return type == VarType.LU;
    }

    Matrix getMatrix() {
        return (Matrix) object;
    }

    Double getNumber() {
        return (Double) object;
    }

    LU getLU() {
        return (LU) object;
    }

    VarType getType() {
        return type;
    }

    Object getObject() {
        return object;
    }

    void print() {
        if (type == VarType.MATRIX) {
            ((Matrix) object).print();
        } else if (type == VarType.LU) {
            ((LU) object).print();
        } else if (type == VarType.NUMBER) {
            new Matrix((Double) object).print();
        }
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
                    return "null";
            }
        }
    }
}
