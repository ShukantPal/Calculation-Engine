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

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a function that can solve expressions in its own 
 * way. Functions are always called before operators and are 
 * unary. Function names must not start with numbers and should start 
 * with alphabets, while the end should also contains alphabet or a 
 * number.
 * @author Sukant Pal
 */
public abstract class ScientificFunction {
    
    public final String Name;
    
    public ScientificFunction(String Name){
        this.Name = Name;
    }
    
    /**
     * Allows the function to get its arguments separated by commas into 
     * a array of strings. E.g. - The arithmetic mean function takes multiple 
     * numbers as arguments.
     * @param Expression - The expression embedded with arguments
     * @return - Array of arguments
     */
    protected synchronized static String[] getArguments(String Expression){
        List<String> args = new ArrayList<>();
        
        String argBuild = new String();
        for(int i=0; i<Expression.length(); i++){
            char ct = Expression.charAt(i);
            if(ct == ','){
                args.add(argBuild);
                argBuild = new String();
            } else if(i == Expression.length() - 1){
                args.add(argBuild + ct);
            } else 
                argBuild += ct;
        }
        
        return args.toArray(new String[args.size()]);
    }
    
    public abstract double resultOf(String Expression);
}
