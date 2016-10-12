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

import org.scilca.calculation.ComplexEngine.ComplexNumber;

/**
 * Built for customizing the engine for solving unary results.
 * @author Sukant Pal
 */
public abstract class ComplexFunction {
    public final String Name;
    
    public ComplexFunction(String Name){
        this.Name = Name;
    }
    
    static String[] getArguments(String Expression){
        return ScientificFunction.getArguments(Expression);
    }
    
    public abstract ComplexNumber resultOf(String Expression);
}
