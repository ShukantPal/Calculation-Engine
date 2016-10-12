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
package org.scilca.calculation;

import java.util.*;
import java.math.*;
import org.scilca.calculation.operators.*;
import org.scilca.calculation.functions.*;

/**
 *
 * @author Sukant Pal
 */
public final class ScientificEngine {
    
     int searchForOperators(String Expression, char o[]){
        for(int j=0; j<Expression.length(); j++){
            char tc = Expression.charAt(j);
            for(char v : o){
                if(tc == v && (tc != '(' || !StringManipulation.isValidFunction(Expression, j))) {
                    return j;
                }
            }
        }
        return -1;
    }
     
     ScientificStructuredOperator searchForOperators(String Expression, ScientificOperator[] o){
        for(int j=0; j<Expression.length(); j++){
            char tc = Expression.charAt(j);
            for(ScientificOperator v : o){
                if(tc == v.Name() && (tc != '-' || !StringManipulation.isUnaryMinus(Expression, j))) {
                    return new ScientificStructuredOperator(j, v);
                }
            }
        }
        return new ScientificStructuredOperator(-1, null);
    }
    
     double[] getNumbersForOperator(String Expression, int index){
        String[] bothNumbers = new String[2];
        bothNumbers[0] = new String();
        bothNumbers[1] = new String();
        
        char nextChar = Expression.charAt(index - 1);
        
        for(int j=index-1; j>=0 && (Character.isDigit(nextChar) 
                 || nextChar == 'E' || nextChar == '.' || StringManipulation.isUnaryMinus(Expression, index));j--){
            bothNumbers[0] += nextChar;
            if(j == 0)
                break;
            nextChar = Expression.charAt(j - 1);
        }
        
        bothNumbers[0] = StringManipulation.reverseString(bothNumbers[0]);
        
        nextChar = Expression.charAt(index + 1);
        for(int j=index+1; j<Expression.length() && (Character.isDigit(nextChar)
                || nextChar == 'E' || nextChar == '.' || StringManipulation.isUnaryMinus(Expression, j)); j++) {
            bothNumbers[1] += nextChar;
            if(j == Expression.length() - 1)
                break;
            nextChar = Expression.charAt(j + 1);
        }
        
        double[] parsedNumbers = new double[4];
        parsedNumbers[0] = Double.parseDouble(bothNumbers[0]);
        parsedNumbers[1] = Double.parseDouble(bothNumbers[1]);
        parsedNumbers[2] = bothNumbers[0].length();
        parsedNumbers[3] = bothNumbers[1].length();
        
        return parsedNumbers;
    }
    
     final char[] POPERATORS = {'('};
      String finishParenthesis(String Expression){
        int i = searchForOperators(Expression, POPERATORS);
        if(i == -1)
            return Expression;
        
        int pb = 1;
        String sExp = new String();
        for(int j=i+1; j<Expression.length(); j++){
            switch(Expression.charAt(j)){
                case '(': 
                    pb += 1;
                    sExp += Expression.charAt(j);
                    break;
                case ')':
                    pb -= 1;
                    if(pb == 0){
                        break;
                    }
                default: 
                    sExp += Expression.charAt(j);
            }
        }
        
        int l = sExp.length();
        sExp = Calculate(sExp);
        
        Expression = StringManipulation.deleteRange(Expression, i, i + l + 1);
        Expression = StringManipulation.insertRange(Expression, sExp, i);
        
        return finishParenthesis(Expression);
    }
    
     String finishPriority(String Expression, ScientificOperator[] o){
        ScientificStructuredOperator i = searchForOperators(Expression, o);
        if(i.pos == -1)
            return Expression;
        
        double[] bothNumbers = getNumbersForOperator(Expression, i.pos);
        String resultStr = 
                Double.toString(StringManipulation.round(i.o.resultOf(bothNumbers[0], bothNumbers[1]), 6));
        
        Expression = StringManipulation.deleteRange(Expression, i.pos - (int) bothNumbers[2], i.pos + (int) bothNumbers[3]);
        Expression = StringManipulation.insertRange(Expression, resultStr, i.pos - (int) bothNumbers[2]);
        
        return finishPriority(Expression, o);
    }
    
     String finishOperators(String Expression, ScientificOperator[] o){
        for(int i=10; i>=1; i--){ // is the priority
            List<ScientificOperator> p = new ArrayList<>();
            for(ScientificOperator t : o){
                if(t.Priority() == i)
                    p.add(t);
            }
            
            if(p.size() > 0)
                Expression = finishPriority(Expression, p.toArray(new ScientificOperator[p.size()]));
        }
        
        return Expression;
    }
    
     String finishFunctions(String Expression, ScientificFunction[] f){
         for(int i=0; i<f.length; i++){
             ScientificFunction ft = f[i];
             int len = ft.Name.length();
             
             for(int j=0; j<Expression.length(); j++){
                 if(StringManipulation.getRange(Expression,j, j+len).equals(ft.Name)){
                     String funcExp = new String();
                     int pb = 1;
                     int o = j;
                     j += len + 1; // Skip function name and parenthesis
                     for( ; j<Expression.length() && pb != 0; j++){
                         char ct = Expression.charAt(j);
                         switch(ct){
                             case '(':
                                 funcExp += ct;
                                 pb += 1;
                             case ')':
                                 pb += -1;
                                 if(pb != 0)
                                     funcExp += ct;
                             default:
                                 if(ct != ')' || pb!=0)
                                    funcExp += ct;
                         }
                     }
                    
                    Expression = StringManipulation.deleteRange(Expression, o, j);
                    Expression = StringManipulation.insertRange(Expression, Double.toString(StringManipulation.round(ft.resultOf(funcExp), 3)), o);
                 }
             }
         }
         
         return Expression;
     }
     
    /**
     * Calculates the mathematical expression with real numbers. This method does not 
     * support matrix functions or complex numbers. It allows normalized expression with 
     * trigonometric and log function support. Custom functions and operators may used with other 
     * overloads of the Calculate method. Each function and operator is prioritized
     * @param Expression
     * @return 
     */
    public  String Calculate(String Expression){
        Expression = finishParenthesis(Expression);
        Expression = finishFunctions(Expression, ScientificFunctions.DEFAULT_FUNCTIONS);
        Expression = finishOperators(Expression, ScientificOperators.OPERATORS);
        return Expression;
    }
}