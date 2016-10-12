/*
 * Copyright (C) 2016 Sukant Pal
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.scilca.calculation.functions;

import static org.scilca.calculation.functions.ScientificFunction.getArguments;

/**
 *
 * @author Sukant Pal
 */
public class ScientificFunctions {
    
    public static enum ANGLE_UNITS{
        DEG, RAD, GRAD;
        
        public static ANGLE_UNITS parseUnit(String str){
            if(str.equals("DEG"))
                return ANGLE_UNITS.DEG;
            else if(str.equals("RAD"))
                return ANGLE_UNITS.RAD;
            else if(str.equals("GRAD"))
                return ANGLE_UNITS.GRAD;
            else throw new IllegalArgumentException(str + " is not a valid angle unit.");
        }
        
    }
    
    /**
     * Used to get the RAD value of any angle. Typically used while 
     * solving trigonometric functions.
     * @param au - The ANGLE_UNITS representation of the angle unit
     * @param value - The value of the angle
     * @return 
     */
    public static double defaultValue(ANGLE_UNITS au, double value){
        switch (au) {
            case RAD:
                return value;
            case DEG:
                return value * (Math.PI / 180);
            default:
                return value * (Math.PI / 200);
        }
    }
    
    public static final ScientificFunction[] DEFAULT_FUNCTIONS = {
        new ScientificFunction("sin") {
            @Override
            public double resultOf(String Expression) {
                String[] arguments = getArguments(Expression);
                if(arguments.length == 1)
                    return Math.sin(Double.parseDouble(arguments[0]));
                else return Math.sin(defaultValue(ANGLE_UNITS.parseUnit(arguments[1]), Double.parseDouble(arguments[0])));
            }
        }, new ScientificFunction("cos"){
            @Override
            public double resultOf(String Expression) {
                String[] arguments = getArguments(Expression);
                if(arguments.length == 1)
                    return Math.cos(Double.parseDouble(arguments[0]));
                else return Math.cos(defaultValue(ANGLE_UNITS.parseUnit(arguments[1]), Double.parseDouble(arguments[0])));
            }
        }, new ScientificFunction("tan"){
            @Override
            public double resultOf(String Expression) {
                String[] arguments = getArguments(Expression);
                if(arguments.length == 1)
                    return Math.tan(Double.parseDouble(arguments[0]));
                else return Math.tan(defaultValue(ANGLE_UNITS.parseUnit(arguments[1]), Double.parseDouble(arguments[0])));
            }
        }, new ScientificFunction("sinh"){
            @Override
            public double resultOf(String Expression) {
                String[] arguments = getArguments(Expression);
                if(arguments.length == 1)
                    return Math.sinh(Double.parseDouble(arguments[0]));
                else return Math.sinh(defaultValue(ANGLE_UNITS.parseUnit(arguments[1]), Double.parseDouble(arguments[0])));
            }
        }, new ScientificFunction("cosh"){
            @Override
            public double resultOf(String Expression) {
                String[] arguments = getArguments(Expression);
                if(arguments.length == 1)
                    return Math.cosh(Double.parseDouble(arguments[0]));
                else return Math.cosh(defaultValue(ANGLE_UNITS.parseUnit(arguments[1]), Double.parseDouble(arguments[0])));
            }
        }, new ScientificFunction("tanh"){
            @Override
            public double resultOf(String Expression) {
                String[] arguments = getArguments(Expression);
                if(arguments.length == 1)
                    return Math.tanh(Double.parseDouble(arguments[0]));
                else return Math.tanh(defaultValue(ANGLE_UNITS.parseUnit(arguments[1]), Double.parseDouble(arguments[0])));
            }
        }, new ScientificFunction("asin"){
            @Override
            public double resultOf(String Expression) {
                return Math.asin(Double.parseDouble(Expression));
            }
        }, new ScientificFunction("acos"){
            @Override
            public double resultOf(String Expression) {
                return Math.acos(Double.parseDouble(Expression));
            }
        }, new ScientificFunction("atan"){
            @Override
            public double resultOf(String Expression) {
                return Math.atan(Double.parseDouble(Expression));
            }
        }, new ScientificFunction("log"){
            @Override
            public double resultOf(String Expression) {
                String[] arguments = getArguments(Expression);
                if(arguments.length == 1)
                    return Math.log10(Double.parseDouble(arguments[0]));
                else return Math.log(Double.parseDouble(arguments[0])) / Math.log(Double.parseDouble(arguments[1]));
            }
        }, new ScientificFunction("log10"){
            @Override
            public double resultOf(String Expression) {
                return Math.log10(Double.parseDouble(Expression));
            }
        }, new ScientificFunction("ln"){
            @Override
            public double resultOf(String Expression){
                return Math.log(Double.parseDouble(Expression));
            }
        }
    };
}
