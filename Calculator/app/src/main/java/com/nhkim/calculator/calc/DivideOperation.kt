package com.nhkim.calculator.calc

import com.nhkim.calculator.abs.AbstractOperation
import kotlin.jvm.Throws

class DivideOperation: AbstractOperation() {
    @Throws(ArithmeticException::class)
    override fun operate(num1: Int, num2: Int): Double {
        require(num2 != 0){
            ArithmeticException("Divicde by Zero")
        }
        return (num1 / num2).toDouble()
    }
}