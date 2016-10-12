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

import java.math.BigDecimal;

/**
 *
 * @author Sukant Pal
 */
class StringManipulation {
     // Common Functions
        
     synchronized static String reverseString(String DataHandle){
        String newData = new String();
        for(int i=DataHandle.length() - 1; i>=0; i--) 
            newData += DataHandle.charAt(i);
        return newData;
    }
    
     synchronized static String deleteRange(String DataHandle, int pos1, int pos2){
        String newData = new String();
        for(int i=0; i<pos1; i++)
            newData+=DataHandle.charAt(i);
        for(int i=pos2+1; i<DataHandle.length(); i++)
            newData+=DataHandle.charAt(i);
        return newData;
    }
    
     synchronized static String insertRange(String DataHandle, String range, int pos){
        String newData = new String();
        for(int i=0; i<pos && i<DataHandle.length(); i++) 
            newData+=DataHandle.charAt(i);
        newData+=range;
        for(int i=pos; i<DataHandle.length(); i++)
            newData+=DataHandle.charAt(i);
        return newData;
    }
    
     synchronized static String getRange(String DataHandle, int pos1, int pos2){
        String newData = new String();
        for(int i=pos1; i<pos2; i++) {
           try{ 
               newData+=DataHandle.charAt(i);
           }
           catch(IndexOutOfBoundsException e){
               return newData;
           }
        }
        return newData;
    }
     
     synchronized static boolean isValidFunction(String Expression, int i){
         char ct = Expression.charAt(i);
         if(ct != '(')
             return false;
         
         char pc = Expression.charAt(i - 1);
         if(Character.isAlphabetic(pc) || Character.isDigit(pc))
             return true;
         else return false;
     }
    
     synchronized static double round(double x, int n){
        return round(x, n, BigDecimal.ROUND_HALF_UP);
     }

     synchronized static double round(double fNum, int n, int roundingMethod) {
        try {
            return (new BigDecimal(Double.toString(fNum)).setScale(n, roundingMethod)).doubleValue();
        } catch (NumberFormatException ex) {
            if (Double.isInfinite(fNum)) {
                return fNum;
            } else {
                return Double.NaN;
            }
        }
    }
    
     synchronized static boolean isUnaryMinus(String Expression, int index){
        char op = Expression.charAt(index);
        if(op != '-')
            return false;
        else if(index == 0)
            return true;
        
        char pp = Expression.charAt(index - 1);
        if(Character.isDigit(pp) || pp == 'E' || pp == 'i') // i for complex number and E for exp form
            return false;
        else return true;
    }
}
