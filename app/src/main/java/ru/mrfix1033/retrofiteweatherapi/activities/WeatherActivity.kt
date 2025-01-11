package ru.mrfix1033.retrofiteweatherapi.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mrfix1033.retrofiteweatherapi.R
import ru.mrfix1033.retrofiteweatherapi.data.CityData
import ru.mrfix1033.retrofiteweatherapi.enumerations.Key
import ru.mrfix1033.retrofiteweatherapi.retrofit.RetrofitInstance

class WeatherActivity : AppCompatActivity() {
    private lateinit var textViewCity: TextView
    private lateinit var textViewTemperature: TextView
    private lateinit var imageView: ImageView
    private lateinit var textViewWindDirection: TextView
    private lateinit var textViewWindSpeed: TextView
    private lateinit var textViewAtmosphericPressure: TextView
    private lateinit var textViewTemperatureMinimum: TextView
    private lateinit var textViewTemperatureMaximum: TextView
    private lateinit var textViewHumidity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weather)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(findViewById(R.id.toolbar))

        textViewCity = findViewById(R.id.textViewCity)
        textViewTemperature = findViewById(R.id.textViewTemperature)
        imageView = findViewById(R.id.imageView)
        textViewWindDirection = findViewById(R.id.textViewWindDirection)
        textViewWindSpeed = findViewById(R.id.textViewWindSpeed)
        textViewAtmosphericPressure = findViewById(R.id.textViewAtmosphericPressure)
        textViewTemperatureMinimum = findViewById(R.id.textViewTemperatureMinimum)
        textViewTemperatureMaximum = findViewById(R.id.textViewTemperatureMaximum)
        textViewHumidity = findViewById(R.id.textViewHumidity)

        getCurrentWeather()
    }

    private fun getCurrentWeather() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                val cityData = intent.getParcelableExtra(Key.cityData, CityData::class.java)!!
                if (intent.getStringExtra(Key.cityMode) == Key.cityModeByName) {
                RetrofitInstance.api.getCurrentWeatherByCityName(
                    cityData.cityName!!,
                    getString(R.string.api_key),
                    "metric"
                )} else RetrofitInstance.api.getCurrentWeatherByCoords(
                    cityData.lat!!,
                    cityData.lon!!,
                    getString(R.string.api_key),
                    "metric"
                )
            } catch (exception: Exception) {
                Toast.makeText(this@WeatherActivity, "Ошибка!\n$exception", Toast.LENGTH_LONG).show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val data = response.body()!!

                    val iconId = data.weather[0].icon
                    val imageUrl = "https://openweathermap.org/img/wn/$iconId@4x.png"
                    Picasso.get().load(imageUrl).into(imageView)

                    textViewCity.text = data.name
                    textViewTemperature.text = "${data.main.temp}°C"
                    textViewWindDirection.text = "${data.wind.deg}°"
                    textViewWindSpeed.text = "${data.wind.speed}м/с"
                    textViewTemperatureMinimum.text = "${data.main.temp_min}°C"
                    textViewTemperatureMaximum.text = "${data.main.temp_max}°C"
                    textViewHumidity.text = "${data.main.humidity}%"
                    val atmosphericPressure = (data.main.pressure / 1.33).toInt()
                    textViewAtmosphericPressure.text = "$atmosphericPressure mm Hg"
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemExit) finishAffinity()
        return super.onOptionsItemSelected(item)
    }
}