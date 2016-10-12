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
package org.scilca.calculation.operators;

/**
 *
 * @author Sukant Pal
 */
public class ScientificOperators {
    
    /**
     * List of all the default operators. These are used in 
     * normal expressions. The priorities of the default operators 
     * are - 
     * <p>
     *      '+' and '-' - 3 
     * </p>
     * <p>
     *      '*' (also 'x') and '/' - 5
     * </p>
     * <p>
     *      '^' - 7
     * </p>
     */
    public static final ScientificOperator[] OPERATORS = {
        new ScientificOperator('+', 3) {   
            @Override
            public double resultOf(double n1, double n2){
               return n1 + n2;
            }
        }, new ScientificOperator('-', 3){
            @Override
            public double resultOf(double n1, double n2){
                return n1 - n2;
            }
        }, new ScientificOperator('*', 5){
            @Override
            public double resultOf(double n1, double n2){
                return n1 * n2;
            }
        }, new ScientificOperator('x', 5){
            @Override
            public double resultOf(double n1, double n2) {
                return n1 * n2;
            }
        }, new ScientificOperator('/', 5){
            @Override
            public double resultOf(double n1, double n2){
                return n1 / n2;
            }
        }, new ScientificOperator('^', 7){
            @Override
            public double resultOf(double n1, double n2) {
                return Math.pow(n1, n2);
            }
        }
    };
    
    /**
     * Used while given the custom operators to the calculation engine. This allows 
     * the default operators to also be solved in the Expression. If you have a 
     * smaller subset of operators and are sure that only those operators will be used 
     * then you can send those operators to the calculation engine to cut of solving 
     * time.
     * @param customOperators
     * @return 
     */
    public static ScientificOperator[] appendCustomOperators(ScientificOperator[] customOperators){
        ScientificOperator[] appendedOperators = new ScientificOperator[OPERATORS.length + customOperators.length];
        
        for(int i=0; i<OPERATORS.length; i++){
            appendedOperators[i] = OPERATORS[i];
        }
        
        for(int j=OPERATORS.length, k=0;
                j<appendedOperators.length && k<customOperators.length; j++, k++){
            appendedOperators[j] = customOperators[k];
        }
        
        return appendedOperators;
    }
}
