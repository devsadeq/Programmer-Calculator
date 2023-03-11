package com.example.programmercalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.programmercalculator.util.Constants.ACTION_BACKSPACE
import com.example.programmercalculator.util.Constants.ACTION_CLEAR
import com.example.programmercalculator.util.Constants.ACTION_EQUAL
import com.example.programmercalculator.util.Constants.CONVERSION_TYPE_MISSING_MSG
import com.example.programmercalculator.util.Constants.GENERIC_ERROR_MSG
import com.example.programmercalculator.util.Constants.MAX_INPUT_LENGTH
import com.example.programmercalculator.util.Constants.MAX_INPUT_LENGTH_REACHED_MSG
import com.example.programmercalculator.util.ConvertType
import com.example.programmercalculator.util.getSelectedType


class MainActivity : AppCompatActivity() {
    private val converter: ProgrammerConverter by lazy { ProgrammerConverter() }
    private val inputTypeRadioGroup: RadioGroup by lazy { findViewById(R.id.input_type_radio_group) }
    private val outputTypeRadioGroup: RadioGroup by lazy { findViewById(R.id.output_type_radio_group) }
    private val inputTextView: TextView by lazy { findViewById(R.id.tv_input) }
    private val outputTextView: TextView by lazy { findViewById(R.id.tv_output) }
    private val inputs: List<Button> by lazy {
        listOf(
            findViewById(R.id.btn_0),
            findViewById(R.id.btn_1),
            findViewById(R.id.btn_2),
            findViewById(R.id.btn_3),
            findViewById(R.id.btn_4),
            findViewById(R.id.btn_5),
            findViewById(R.id.btn_6),
            findViewById(R.id.btn_7),
            findViewById(R.id.btn_8),
            findViewById(R.id.btn_9),
            findViewById(R.id.btn_a),
            findViewById(R.id.btn_b),
            findViewById(R.id.btn_c),
            findViewById(R.id.btn_d),
            findViewById(R.id.btn_e),
            findViewById(R.id.btn_f),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        disableInputButtons()
    }

    fun onDigitClick(view: View) {
        if (inputTextView.text.length == MAX_INPUT_LENGTH) return showToast(
            MAX_INPUT_LENGTH_REACHED_MSG
        )
        val button = view as Button
        inputTextView.append(button.text)
    }

    fun onActionClick(view: View) {
        when ((view as Button).text.toString()) {
            ACTION_EQUAL -> onConvert()
            ACTION_CLEAR -> clearAll()
            ACTION_BACKSPACE -> onBackspaceClick()
        }
    }

    fun onInputTypeRadioButtonClick(view: View) {
        clearAll()
        disableInvalidButtons(inputTypeRadioGroup.getSelectedType())
    }

    private fun clearAll() {
        inputTextView.text = ""
        outputTextView.text = ""
    }

    private fun onBackspaceClick() {
        val input = inputTextView.text.toString()
        input.takeIf { it.isNotEmpty() }?.let {
            inputTextView.text = input.substring(0, input.length - 1)
        }
    }

    private fun onConvert() {
        try {
            if (!areBothTypeRadioGroupsSelected()) return showToast(CONVERSION_TYPE_MISSING_MSG)
            inputTextView.text.toString().takeIf { it.isNotEmpty() }?.let {
                val inputType = inputTypeRadioGroup.getSelectedType()
                val outputType = outputTypeRadioGroup.getSelectedType()
                outputTextView.text =
                    converter.convert(input = it, inputType = inputType, outputType = outputType)
            }
        } catch (e: Exception) {
            clearAll()
            showToast(GENERIC_ERROR_MSG)
        }
    }

    private fun disableInvalidButtons(from: ConvertType) {
        enableInputButtons()

        val validInputs: List<String> = when (from) {
            ConvertType.BINARY -> (0..1).map { it.toString() }
            ConvertType.DECIMAL -> (0..9).map { it.toString() }
            ConvertType.OCTAL -> (0..7).map { it.toString() }
            ConvertType.HEXADECIMAL -> ((0..9) + ('A'..'F')).map { it.toString() }
        }

        inputs.filterNot { validInputs.contains(it.text.toString()) }
            .forEach { it.isEnabled = false }
    }

    private fun enableInputButtons() = inputs.forEach { it.isEnabled = true }

    private fun disableInputButtons() = inputs.forEach { it.isEnabled = false }

    private fun areBothTypeRadioGroupsSelected(): Boolean =
        inputTypeRadioGroup.checkedRadioButtonId != -1 && outputTypeRadioGroup.checkedRadioButtonId != -1

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}