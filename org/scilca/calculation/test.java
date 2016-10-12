package org.scilca.calculation;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sukant Pal
 */
public class test {
    
    public static void main(String[] args){
        String Expression = "2+3";
        
        System.out.println(new ComplexEngine().Calculate(Expression) + " ");
    }
}
