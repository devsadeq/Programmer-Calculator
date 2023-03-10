package com.example.programmercalculator

import com.example.programmercalculator.util.ConvertType

interface Converter {
    fun convert(input: String, from: ConvertType, to: ConvertType): String
}