package com.example.programmercalculator

import com.example.programmercalculator.util.Constants.RADIX_BINARY
import com.example.programmercalculator.util.Constants.RADIX_HEXADECIMAL
import com.example.programmercalculator.util.Constants.RADIX_OCTAL
import com.example.programmercalculator.util.ConvertType
import java.math.BigInteger

class ProgrammerConverter : Converter {
    override fun convert(input: String, inputType: ConvertType, outputType: ConvertType): String {
        if (inputType == outputType) return input
        val decimal = when (inputType) {
            ConvertType.BINARY -> BigInteger(input, RADIX_BINARY)
            ConvertType.DECIMAL -> BigInteger(input)
            ConvertType.HEXADECIMAL -> BigInteger(input, RADIX_HEXADECIMAL)
            ConvertType.OCTAL -> BigInteger(input, RADIX_OCTAL)
        }
        return when (outputType) {
            ConvertType.BINARY -> decimal.toString(RADIX_BINARY)
            ConvertType.DECIMAL -> decimal.toString()
            ConvertType.HEXADECIMAL -> decimal.toString(RADIX_HEXADECIMAL).uppercase()
            ConvertType.OCTAL -> decimal.toString(RADIX_OCTAL)
        }
    }

}