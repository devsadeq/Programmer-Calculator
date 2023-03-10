package com.example.programmercalculator

import com.example.programmercalculator.util.Constants.BINARY
import com.example.programmercalculator.util.Constants.HEXADECIMAL
import com.example.programmercalculator.util.Constants.OCTAL
import com.example.programmercalculator.util.ConvertType
import java.math.BigInteger

class ProgrammerConverter : Converter {
    override fun convert(input: String, from: ConvertType, to: ConvertType): String {
        if (from == to) return input
        val decimal = when (from) {
            ConvertType.BINARY -> BigInteger(input, BINARY)
            ConvertType.DECIMAL -> BigInteger(input)
            ConvertType.HEXADECIMAL -> BigInteger(input, HEXADECIMAL)
            ConvertType.OCTAL -> BigInteger(input, OCTAL)
        }
        return when (to) {
            ConvertType.BINARY -> decimal.toString(BINARY)
            ConvertType.DECIMAL -> decimal.toString()
            ConvertType.HEXADECIMAL -> decimal.toString(HEXADECIMAL).uppercase()
            ConvertType.OCTAL -> decimal.toString(OCTAL)
        }
    }

}