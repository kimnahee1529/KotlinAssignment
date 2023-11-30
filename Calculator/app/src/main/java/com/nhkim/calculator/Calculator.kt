package com.nhkim.calculator

interface Calculator {
    fun calculate(a: Int, b: Int): Int
}

class AddOperation : Calculator {
    override fun calculate(a: Int, b: Int): Int {
        return a + b
    }
}

class SubtractOperation : Calculator {
    override fun calculate(a: Int, b: Int): Int {
        return a - b
    }
}

class MultiplyOperation : Calculator {
    override fun calculate(a: Int, b: Int): Int {
        return a * b
    }
}

class DivideOperation : Calculator {
    override fun calculate(a: Int, b: Int): Int {
        return a / b
    }
}

class ModOperation : Calculator {
    override fun calculate(a: Int, b: Int): Int {
        return a % b
    }
}