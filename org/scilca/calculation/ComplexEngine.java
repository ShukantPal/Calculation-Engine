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

import java.util.ArrayList;
import java.util.List;
import org.scilca.calculation.operators.*;
import org.scilca.calculation.functions.*;

/**
 * ComplexEngine is a more powerful calculation engine which can solve 
 * expression with complex numbers. The ComplexEngine groups numbers in 
 * a c() function allowing the combination of real numbers and imaginary  The 
 * exponent operator is not included in the engine. You can create your own 
 * exponent operator for the engine.
 * numbers.
 * @author Sukant Pal
 */
public class ComplexEngine{
    
    public static final class ComplexNumber{
        public final double r;
        public final double i;
        public int refLength;
        
        public ComplexNumber(double r, double i){
            this.r = r;
            this.i = i;
        }
        
        public ComplexNumber conjugate(){
            return new ComplexNumber(r, -i);
        }
        
        public double modulus(){
            return r*r + i*i;
        }
        
       @Override
        public String toString(){
            if(i == 0)
                return Double.toString(r);
            else if(r == 0)
                return Double.toString(i) + "i";
            else if(i > 0)
                return Double.toString(r) 
                        + "+" 
                        + Double.toString(i) 
                        + "i";
            else
                return Double.toString(r)
                        + "-"
                        + Double.toString(-1*i)
                        + "i";
        }

        public String ctoString(){
            return "c(" + toString() + ")";
        }
        
        /**
         * Removes the function declaration in 
         * a complex function. E.g - c(and ) will be 
         * removed. 
         * 
         * @param cFunc
         * @return 
         */
        public static String ripFunction(String cFunc)
        {
            String data = new String();
            for(int i = 2; i < cFunc.length() - 1; i++)
            {
                data += cFunc.charAt(i);
            }
            return data;
        }
        
        public static ComplexNumber convertExpression(String Expression)
            throws NumberFormatException{
            try{
                double c = Double.parseDouble(Expression);
                return new ComplexNumber(c, 0);
            } catch(NumberFormatException e){}
            
            if(Expression.contains("+") || Expression.contains("-")){
                int i = 0;
                for( ; i < Expression.length(); i++){
                    if(Expression.charAt(i) == '+'
                            || (Expression.charAt(i) == '-'))
                        break;
                }
                
                if(StringManipulation.isUnaryMinus(Expression, i)){
                    return new ComplexNumber(0, Double.parseDouble(StringManipulation.deleteRange(Expression, Expression.length()-1, Expression.length() - 1)));
                }
            
                String fTerm = new String();
                String sTerm = new String();
            
                 for(int j=i-1; j>=0; j--){
                    fTerm += Expression.charAt(j);
            }
            
            for(int j=i+1; j<Expression.length(); j++){
                sTerm += Expression.charAt(j);
            }
            
            boolean fTermcI = false;
            
            Double fNum;
            Double sNum;
            
            fTerm = StringManipulation.reverseString(fTerm);
            
            if(fTerm.contains("i")){
                fTermcI = true;
                fTerm = StringManipulation.deleteRange(fTerm, fTerm.length() - 1, fTerm.length() - 1);
                fNum = Double.parseDouble(fTerm);
                if(sTerm.contains("i"))
                    throw new NumberFormatException("The complex number given has duplicate imaginary values");
                else
                    sNum = Expression.charAt(i) == '-' ? -1*Double.parseDouble(sTerm) : Double.parseDouble(sTerm);
                }
                else if(sTerm.contains("i")) {
                sTerm = StringManipulation.deleteRange(sTerm, sTerm.length() - 1, sTerm.length() - 1);
                    sNum = Expression.charAt(i) == '-' ? -1*Double.parseDouble(sTerm) :  Double.parseDouble(sTerm);
                    fNum = Double.parseDouble(fTerm);
                }
                else {
                    throw new NumberFormatException("The complex number given has duplicate values.");
                }
            
                if(!fTermcI)
                    return new ComplexNumber(fNum, sNum);
                else  return new ComplexNumber(sNum, fNum);
            }
            else {
                if(Expression.contains("i")) {
                    Expression = StringManipulation.deleteRange(Expression, Expression.length() - 1, Expression.length());
                    return new ComplexNumber(0, Double.parseDouble(Expression));
                }
                else {
                    return new ComplexNumber(Double.parseDouble(Expression), 0);
                }
            }
        }
        
        public static ComplexNumber add(ComplexNumber a, ComplexNumber b){
            return new ComplexNumber(a.r + b.r, a.i + b.i);
        }
        
        public static ComplexNumber subtract(ComplexNumber a, ComplexNumber b){
            return new ComplexNumber(a.r - b.r, a.i - b.i);
        }
        
        public static ComplexNumber multiply(ComplexNumber a, ComplexNumber b){
            return new ComplexNumber(a.r*b.r - a.i*b.i, a.r*b.i + b.r*a.i);
        }
        
        public static ComplexNumber divide(ComplexNumber a, ComplexNumber b){
            ComplexNumber m = multiply(a, b.conjugate());
            return new ComplexNumber(m.r/b.modulus(), m.i/b.modulus());
        }
    }
     
     ComplexStructuredOperator searchForOperators(String Expression, ComplexOperator[] o){
        for(int j=0; j<Expression.length(); j++){
            char tc = Expression.charAt(j);
            for(ComplexOperator v : o){
                if(tc == v.Name && (tc != '-' || !StringManipulation.isUnaryMinus(Expression, j)) && isOperatorValid(Expression, j)) {
                    return new ComplexStructuredOperator(j, v);
                }
            }
        }
        return new ComplexStructuredOperator(-1, null);
    }
     
    protected static ComplexNumber nbOperator(String Expression, int index){
        String firstComplexNumber = new String();
        
        for(int k=index-1; k>=0; k--)
        {
            firstComplexNumber += Expression.charAt(k);
            if(Expression.charAt(k) == 'c')
                break;
        }
        int fNumber = firstComplexNumber.length();
        
        firstComplexNumber = StringManipulation.reverseString(firstComplexNumber);
        firstComplexNumber = ComplexNumber.ripFunction(firstComplexNumber);        
        ComplexNumber fC = ComplexNumber.convertExpression(firstComplexNumber);
        fC.refLength = fNumber;
        return fC;
    }
    
    protected static ComplexNumber naOperator(String Expression, int index)
    {
        String secondComplexNumber = "c(";
        
        int bracketbalance = 1;
        for(int k=index+3; k<Expression.length() && bracketbalance > 0; k++)
        {
            secondComplexNumber += Expression.charAt(k);
            if(Expression.charAt(k) == '(')
            {
                bracketbalance += 1;
            }
            else if(Expression.charAt(k) == ')')
            {
                bracketbalance -= 1;
            }
        }
        
       int sNumber = secondComplexNumber.length();
        
       secondComplexNumber = ComplexNumber.ripFunction(secondComplexNumber);
       ComplexNumber sC = ComplexNumber.convertExpression(secondComplexNumber);
       sC.refLength = sNumber;
       return sC;
    }
    
    boolean isOperatorValid(String Expression, int index){
        for(int i=index; i>0; i--){
            if(Expression.charAt(i) == ')')
                return true;
            else if(Expression.charAt(i) == 'c')
                return false;
        }
        
        return false;
    }
    
    static boolean isOpenerValid(String Expression, int index){
        if(index != 0 && (Character.isDigit(Expression.charAt(index - 1)) || Character.isAlphabetic(Expression.charAt(index - 1))))
            return false;
        else return true;
    }

    String insertCFuncs(String Expression){
        String pmTerm = new String();
        for(int i = 0; i < Expression.length(); i++){
            if((i == Expression.length() - 1) && Expression.charAt(i) != ')'){
                pmTerm += Expression.charAt(i);
                Expression = StringManipulation.deleteRange(Expression, i - pmTerm.length() + 1, i);
                Expression = StringManipulation.insertRange(Expression, "c(" + pmTerm + ")", i - pmTerm.length() + 1);
                break;
            }
            else if(Character.isDigit(Expression.charAt(i)) || Expression.charAt(i) == '.'
                    || Expression.charAt(i) == 'E' || Expression.charAt(i) == 'i' 
                    || StringManipulation.isUnaryMinus(Expression, i)){
                pmTerm += Expression.charAt(i); 
            }
            else if (pmTerm.length() > 0){
                Expression = StringManipulation.deleteRange(Expression, i - pmTerm.length(), i - 1);
                Expression = StringManipulation.insertRange(Expression, "c(" + pmTerm + ")", i - pmTerm.length());
                pmTerm = new String();
                i+=3;
            }
        }
        return Expression;
    }
    
     String finishParenthesis(String Expression){
        int i = -1;
        for(int j = 0; j < Expression.length(); j++){
            if(Expression.charAt(j) == '('
                    && isOpenerValid(Expression, j)){
                i = j;
                break;
            }
        }
        if(i == -1) 
            return Expression;
 
        String internalExpression = new String();
        int bracketbalance = 1;
        
        for(int k = i + 1; k < Expression.length() && bracketbalance > 0; k++){
            switch(Expression.charAt(k)){
                case '(':
                    bracketbalance += 1;
                    internalExpression += '(';
                    continue;
                case ')':
                    bracketbalance -= 1;
                    if(bracketbalance != 0) 
                        internalExpression += ')';
                    continue;
                default: internalExpression += Expression.charAt(k);
            }
        }
        
        Expression = StringManipulation.deleteRange(Expression, i, i + 1 + internalExpression.length());
        internalExpression = Automation(internalExpression);
        Expression = StringManipulation.insertRange(Expression, internalExpression, i);
        
        return finishParenthesis(Expression);
    }
     
     String finishPriority(String Expression, ComplexOperator[] o){
        ComplexStructuredOperator i = searchForOperators(Expression, o);
        if(i.pos == -1)
            return Expression;
        
        ComplexNumber[] bothNumbers = new ComplexNumber[2];
        bothNumbers[0] = nbOperator(Expression, i.pos);
        bothNumbers[1] = naOperator(Expression, i.pos);
        
        String resultStr = i.co.resultOf(bothNumbers[0], bothNumbers[1]).ctoString();

        Expression = StringManipulation.deleteRange(Expression, i.pos - bothNumbers[0].refLength, i.pos + bothNumbers[1].refLength);
        Expression = StringManipulation.insertRange(Expression, resultStr, i.pos - bothNumbers[0].refLength);
        
        return finishPriority(Expression, o);
    }
     
     String finishOperators(String Expression, ComplexOperator[] o){
         for(int i=10; i>=0; i--){
             List<ComplexOperator> p = new ArrayList<>();
             for(ComplexOperator t : o){
                if(t.priority == i)
                    p.add(t);
             }
            
            if(p.size() > 0)
                Expression = finishPriority(Expression, p.toArray(new ComplexOperator[p.size()]));
         }
         
         return Expression;
     }
     
     String finishFunctions(String Expression, ComplexFunction[] f){
         for(int i=0; i<f.length; i++){
             ComplexFunction ft = f[i];
             int len = ft.Name.length();
             
             for(int j=0; j<Expression.length(); j++){
                 if(StringManipulation.getRange(Expression,j, j+len).equals(ft.Name)){
                     String funcExp = new String();
                     int pb = 1;
                     int o = j;
                     j += len + 1; // Skip function name and parenthesis
                     for( ; j<Expression.length() && pb != 0; j++){
                         char ct = Expression.charAt(j);
                         if(ct == '('){
                             pb += 1;
                             funcExp += ct;
                         } else if(ct == ')'){
                             pb -= 1;
                             if(pb != 0)
                                 funcExp += ')';
                         } else {
                             funcExp += ct;
                         }
                     }
                    System.out.println(funcExp + " a");
                    Expression = StringManipulation.deleteRange(Expression, o, j);
                    Expression = StringManipulation.insertRange(Expression, ft.resultOf(funcExp).ctoString(), o);
                    System.out.println(Expression);
                 }
             }
         }
         
         return Expression;
     }
    
    String Automation(String Expression){
        Expression = finishParenthesis(Expression);
        Expression = finishOperators(Expression, ComplexOperators.DEFAULT_OPERATORS);
        return Expression;
    }
    
    String Automation(String Expression, ComplexOperator[] co){
        Expression = finishParenthesis(Expression);
        Expression = finishOperators(Expression,co);
        return Expression;
    }
    
    public ComplexNumber Calculate(String Expression){
        Expression = Expression.trim();
        Expression = insertCFuncs(Expression);
        Expression = Automation(Expression);
        
        return ComplexNumber.convertExpression(ComplexNumber.ripFunction(Expression));
    }
    
    public ComplexNumber Calculate(String Expression, ComplexOperator[] co, ComplexFunction[] cf){
        Expression = Expression.trim();
        Expression = insertCFuncs(Expression);
        if(cf != null)
            Expression = finishFunctions(Expression, cf);
        Expression = Automation(Expression);
        
        return ComplexNumber.convertExpression(ComplexNumber.ripFunction(Expression));
    }
    
}
