package com.nhkim.calculator.calc

import com.nhkim.calculator.abs.AbstractOperation

class ModOperation : AbstractOperation() {
    override fun operate(num1: Int, num2: Int): Double = (num1 % num2).toDouble()
}