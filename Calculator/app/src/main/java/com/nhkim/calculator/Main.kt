package com.nhkim.calculator

fun main() {
    println("연산식을 입력해주세요")

    val operators = listOf("+", "-", "*", "/", "%")

    while (true) {
        val inputOperation = readLine()?.replace(" ", "")

        var operator: String? = null
        for (op in operators) {
            if (inputOperation?.contains(op) == true) {
                operator = op
                break
            }
        }

        println("operator: $operator")

        if (operator != null) {
            val operationList = inputOperation!!.split(operator)
            try {
                val firstOperand = operationList[0].toInt()
                val secondOperand = operationList[1].toInt()

                val calc: Calculator = when (operator) {
                    "+" -> AddOperation()
                    "-" -> SubtractOperation()
                    "*" -> MultiplyOperation()
                    "/" -> DivideOperation()
                    "%" -> ModOperation()
                    else -> throw IllegalArgumentException("Unsupported operator")
                }

                println("calc: $calc")

                val result = calc.calculate(firstOperand, secondOperand)
                println("$firstOperand $operator $secondOperand 결과는 : $result 입니다")
            } catch (e: NumberFormatException) {
                println("숫자 형식이 올바르지 않습니다.")
            }
        } else {
            println("올바른 연산자를 입력해주세요.")
        }
    }
}
