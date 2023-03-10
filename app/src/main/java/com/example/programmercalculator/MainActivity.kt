package com.example.programmercalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.programmercalculator.util.Constants.BACKSPACE
import com.example.programmercalculator.util.Constants.CHOOSE_MSG
import com.example.programmercalculator.util.Constants.CLEAR
import com.example.programmercalculator.util.Constants.EQUAL
import com.example.programmercalculator.util.Constants.OOPS_MSG
import com.example.programmercalculator.util.ConvertType
import com.example.programmercalculator.util.getSelectedType


class MainActivity : AppCompatActivity() {
    private val converter: ProgrammerConverter by lazy { ProgrammerConverter() }
    private val fromRadioGroup: RadioGroup by lazy { findViewById(R.id.from_radio_group) }
    private val toRadioGroup: RadioGroup by lazy { findViewById(R.id.to_radio_group) }
    private val tvInput: TextView by lazy { findViewById(R.id.tv_input) }
    private val tvOutput: TextView by lazy { findViewById(R.id.tv_output) }
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
        disableAllButtons()
    }

    fun onNumberClick(view: View) {
        if (tvInput.text.length == 12) return showToaster("Max length reached")
        val button = view as Button
        tvInput.append(button.text)
    }

    fun onOperationClick(view: View) {
        when ((view as Button).text.toString()) {
            EQUAL -> onConvert()
            CLEAR -> clearAll()
            BACKSPACE -> onBackspaceClick()
        }
    }

    fun onFromRadioButtonClick(view: View) {
        clearAll()
        enableDisableButtons(fromRadioGroup.getSelectedType())
    }

    private fun clearAll() {
        tvInput.text = ""
        tvOutput.text = ""
    }

    private fun onBackspaceClick() {
        val input = tvInput.text.toString()
        input.takeIf { it.isNotEmpty() }?.let {
            tvInput.text = input.substring(0, input.length - 1)
        }
    }

    private fun onConvert() {
        try {
            if (!isBothRadioGroupSelected()) return showToaster(CHOOSE_MSG)
            tvInput.text.toString().takeIf { it.isNotEmpty() }?.let {
                val fromType = fromRadioGroup.getSelectedType()
                val toType = toRadioGroup.getSelectedType()
                tvOutput.text = converter.convert(input = it, from = fromType, to = toType)
            }
        } catch (e: Exception) {
            clearAll()
            showToaster(OOPS_MSG)
            throw e
        }
    }

    private fun enableDisableButtons(from: ConvertType) {
        enableAllButtons()

        val validInputs: List<String> = when (from) {
            ConvertType.BINARY -> (0..1).map { it.toString() }
            ConvertType.DECIMAL -> (0..9).map { it.toString() }
            ConvertType.OCTAL -> (0..7).map { it.toString() }
            ConvertType.HEXADECIMAL -> ((0..9) + ('A'..'F')).map { it.toString() }
        }

        inputs.filterNot { validInputs.contains(it.text.toString()) }
            .forEach { it.isEnabled = false }
    }

    private fun enableAllButtons() = inputs.forEach { it.isEnabled = true }

    private fun disableAllButtons() = inputs.forEach { it.isEnabled = false }

    private fun isBothRadioGroupSelected(): Boolean =
        fromRadioGroup.checkedRadioButtonId != -1 && toRadioGroup.checkedRadioButtonId != -1

    private fun showToaster(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}