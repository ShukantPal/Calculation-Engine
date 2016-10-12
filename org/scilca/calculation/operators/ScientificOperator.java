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
 * Provides the way to make custom operators. The operators should not have same 
 * Names. If so the one with higher priority will be used. The Name of the operator 
 * should not be a number or a alphabet (may be a alphabet reliably in some conditions) 
 * else unreliable result will outcome.
 * @author Sukant Pal
 */
public abstract class ScientificOperator {
    
    char Name;
    int priority;
    
    public ScientificOperator(char Name){
        this.Name = Name;
        this.priority = 5;
    }
    
    public ScientificOperator(char Name, int priority){
        this(Name);
        this.priority = priority;
    }
    
    public char Name(){
        return Name;
    }
    
    public int Priority(){
        return priority;
    }
    
    public abstract double resultOf(double n1, double n2);
    
}
