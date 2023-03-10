package com.example.programmercalculator.util

import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.programmercalculator.util.Constants.BINARY_STRING
import com.example.programmercalculator.util.Constants.DECIMAL_STRING
import com.example.programmercalculator.util.Constants.HEXADECIMAL_STRING
import com.example.programmercalculator.util.Constants.INVALID_RADIO_BUTTON_MSG
import com.example.programmercalculator.util.Constants.NO_SELECTION_MSG
import com.example.programmercalculator.util.Constants.NULL_TEXT_MSG
import com.example.programmercalculator.util.Constants.OCTAL_STRING

fun RadioGroup.getSelectedType(): ConvertType {
    val radioButton = findViewById<RadioButton>(checkedRadioButtonId)
    return when {
        radioButton == null -> throw NullPointerException(NO_SELECTION_MSG)
        radioButton.text == null -> throw NullPointerException(NULL_TEXT_MSG)
        radioButton.text.toString() == BINARY_STRING -> ConvertType.BINARY
        radioButton.text.toString() == DECIMAL_STRING -> ConvertType.DECIMAL
        radioButton.text.toString() == HEXADECIMAL_STRING -> ConvertType.HEXADECIMAL
        radioButton.text.toString() == OCTAL_STRING -> ConvertType.OCTAL
        else -> throw IllegalArgumentException(INVALID_RADIO_BUTTON_MSG)
    }
}