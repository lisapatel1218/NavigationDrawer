package com.example.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class currency_converter1 : AppCompatActivity() {

    var baseCurrency = "EUR"
    var convertedToCurrency = "USD"
    var conversionRate = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_converter1)

        spinnerSetup()
        textChangedStuff()
    }

    private fun textChangedStuff() {
        val etFirstConversion = findViewById<EditText>(R.id.et_firstConversion)
        etFirstConversion.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Handle text changed event
                getApiResult(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("CurrencyConverter", "Before Text Changed")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("CurrencyConverter", "On Text Changed")
            }
        })
    }

    private fun getApiResult(inputValue: String) {
        if (inputValue.isNotEmpty() && inputValue.isNotBlank()) {
            var API = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_qU2njLy3Taz6yy8cQZfJofDi8COjUI4BqKsg19my"

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val apiResult = URL(API).readText()
                    val jsonObject = JSONObject(apiResult)

                    // Extract conversion rate from nested JSON response
                    val dataObject = jsonObject.getJSONObject("data")
                    conversionRate = dataObject.getDouble(convertedToCurrency).toFloat()

                    Log.d("Main", "$conversionRate")
                    Log.d("Main", apiResult)

                    withContext(Dispatchers.Main) {
                        val text = (inputValue.toFloat() * conversionRate).toString()
                        findViewById<EditText>(R.id.et_secondConversion)?.setText(text)
                    }
                } catch (e: Exception) {
                    Log.e("Main", "$e")
                    displayToast("Failed to fetch data. Please try again later.")
                }
            }
        }
    }

    private fun spinnerSetup() {
        val spinnerFirstConversion = findViewById<Spinner>(R.id.spinner_firstConversion)
        val spinnerSecondConversion = findViewById<Spinner>(R.id.spinner_secondConversion)

        ArrayAdapter.createFromResource(
            this,
            R.array.currencies,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFirstConversion.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.currencies2,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSecondConversion.adapter = adapter
        }

        spinnerFirstConversion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                baseCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult(findViewById<EditText>(R.id.et_firstConversion)?.text.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle nothing selected if needed
            }
        }

        spinnerSecondConversion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertedToCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult(findViewById<EditText>(R.id.et_firstConversion)?.text.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle nothing selected if needed
            }
        }
    }

    private fun displayToast(message: String) {
        runOnUiThread {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }
}
