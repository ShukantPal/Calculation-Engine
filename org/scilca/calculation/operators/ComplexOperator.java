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
public abstract class ComplexOperator {
    public final char Name;
    public final int priority;
    
    public ComplexOperator(char Name){
        this.Name = Name;
        this.priority = 5;
    }
    
    public ComplexOperator(char Name, int priority){
        this.Name = Name;
        this.priority = priority;
    }
    
    public abstract ComplexNumber resultOf(ComplexNumber c1, ComplexNumber c2);
}
