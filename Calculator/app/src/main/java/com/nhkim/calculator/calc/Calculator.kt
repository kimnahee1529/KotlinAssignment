package com.nhkim.calculator.calc

import com.nhkim.calculator.abs.AbstractOperation

class Calculator(private val operator: AbstractOperation){
    fun operator(num1: Int, num2: Int): Double{
        return operator.operate(num1, num2)
    }
}

