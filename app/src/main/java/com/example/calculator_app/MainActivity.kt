package com.example.calculator_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator_app.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false


    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun onAllClearClick(view: View) {

        binding.inputText.text = ""
        binding.outputText.text = ""
        stateError = false
        lastNumeric = false

    }

    fun onEqualClick(view: View) {

        onEqual()

    }

    fun onDigitClick(view: View) {

        if (stateError) {

            binding.inputText.text = (view as Button).text
            stateError = false

        }else{

            binding.inputText.append((view as Button).text)

        }

        lastNumeric = true
    }
    fun onOperatorClick(view: View) {

        if (!stateError && lastNumeric){

            binding.inputText.append((view as Button).text)
            lastNumeric = false

        }
    }

    fun onEqual(){

        if (lastNumeric && !stateError){

            val txt = binding.inputText.text.toString()

            expression = ExpressionBuilder(txt).build()

            try{

                val output = expression.evaluate()

                val decimalFormat = DecimalFormat("#.##########")

                binding.outputText.visibility = View.VISIBLE
                binding.outputText.text = "=" + decimalFormat.format(output)

            }catch (ex : ArithmeticException) {

                Log.e("evaluate error", ex.toString())
                binding.outputText.text = "Error"
                stateError = true
                lastNumeric = false

            }
        }
    }
}




