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

import org.scilca.calculation.ComplexEngine.ComplexNumber;

/**
 *
 * @author Sukant Pal
 */
public class ComplexOperators {
    
    public static final ComplexOperator[] DEFAULT_OPERATORS = {
        new ComplexOperator('+',3){
            @Override
            public ComplexNumber resultOf(ComplexNumber c1, ComplexNumber c2) {
                return ComplexNumber.add(c1, c2);
            }
        }, new ComplexOperator('-',3){
            @Override
            public ComplexNumber resultOf(ComplexNumber c1, ComplexNumber c2) {
                return ComplexNumber.subtract(c1, c2);
            }
        }, new ComplexOperator('*',5){
            @Override
            public ComplexNumber resultOf(ComplexNumber c1, ComplexNumber c2) {
                return ComplexNumber.multiply(c1, c2);
            }
        }, new ComplexOperator('x',5){
            @Override
            public ComplexNumber resultOf(ComplexNumber c1, ComplexNumber c2) {
                return ComplexNumber.multiply(c1, c2);
            }
        }, new ComplexOperator('/',5){
            @Override
            public ComplexNumber resultOf(ComplexNumber c1, ComplexNumber c2) {
                return ComplexNumber.divide(c1, c2);
            }
        }
    };

}
