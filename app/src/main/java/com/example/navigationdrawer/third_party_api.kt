package com.example.navigationdrawer

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import org.json.JSONObject
import java.net.URL

class third_party_api : AppCompatActivity() {
    var CITY: String = "paris,france"
    val API: String = "885ee9409991215b908f9decc1dbf7d7" // Use your API key
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_party_api)
        searchView = findViewById(R.id.searchView)
        searchView.requestFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    CITY = query
                    Log.d("SearchView", "Query submitted: $query")
                    weatherTask().execute()
                    return true
                } else {
                    Log.d("SearchView", "Empty or null query")
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text changes if needed
                return false
            }
        })
        // Initial weather update with default CITY
        weatherTask().execute()
    }

    inner class weatherTask : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("WeatherTask", "Pre-execution: Showing loader")
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response: String?
            try {
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").
                readText(Charsets.UTF_8)
            } catch (e: Exception) {
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                // Extracting JSON returns from the API
                val jsonObj = JSONObject(result)
                Log.d("WeatherTask", "Weather data fetched successfully")

                // Updating the UI with the weather information
                updateWeatherUI(jsonObj)

            } catch (e: Exception) {
                // Handle exception and show error message
                Log.e("WeatherTask", "Error: ${e.message}")
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }
        }

        private fun updateWeatherUI(jsonObj: JSONObject) {
            try {
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt: Long = jsonObj.getLong("dt")
                val temp = main.getString("temp") + "°C"
                val tempMin = "Min Temp: " + main.getString("temp_min") + "°C"
                val tempMax = "Max Temp: " + main.getString("temp_max") + "°C"
                val sunrise = "Sunrise: " + formatTime(sys.getLong("sunrise"))
                val sunset = "Sunset: " + formatTime(sys.getLong("sunset"))
                val windSpeed = "Wind: " + wind.getString("speed") + " m/s"
                val humidity = "Humidity: " + main.getString("humidity") + "%"
                val pressure = "Pressure: " + main.getString("pressure") + " hPa"

                // Example TextViews, replace with your actual TextViews
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.temp_min).text = tempMin
                findViewById<TextView>(R.id.temp_max).text = tempMax
                findViewById<TextView>(R.id.sunrise).text = sunrise
                findViewById<TextView>(R.id.sunset).text = sunset
                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.humidity).text = humidity
                findViewById<TextView>(R.id.pressure).text = pressure
                findViewById<TextView>(R.id.status).text = weather.getString("description")

                // Views populated, Hiding the loader, Showing the main design
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE
            } catch (e: Exception) {
                // Handle exception and show error message
                Log.e("WeatherTask", "Error updating UI: ${e.message}")
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }
        }

        private fun formatTime(timestamp: Long): String {
            // You can use SimpleDateFormat or other date formatting methods
            // Here, a simple implementation is used for demonstration purposes
            return android.text.format.DateFormat.format("hh:mm a", timestamp * 1000).toString()
        }
    }
}
