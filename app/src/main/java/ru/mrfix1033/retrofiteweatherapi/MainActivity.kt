package ru.mrfix1033.retrofiteweatherapi

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var textViewCity: TextView
    private lateinit var textViewTemperature: TextView
    private lateinit var imageView: ImageView
    private lateinit var textViewWindDirection: TextView
    private lateinit var textViewWindSpeed: TextView
    private lateinit var textViewAtmosphericPressure: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewCity = findViewById(R.id.textViewCity)
        textViewTemperature = findViewById(R.id.textViewTemperature)
        imageView = findViewById(R.id.imageView)
        textViewWindDirection = findViewById(R.id.textViewWindDirection)
        textViewWindSpeed = findViewById(R.id.textViewWindSpeed)
        textViewAtmosphericPressure = findViewById(R.id.textViewAtmosphericPressure)

        getCurrentWeather()
    }

    private fun getCurrentWeather() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getCurrentWeather(
                    "Moscow",
                    getString(R.string.api_key),
                    "metric"
                )
            } catch (exception: Exception) {
                Toast.makeText(this@MainActivity, "Ошибка!\n$exception", Toast.LENGTH_LONG).show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val data = response.body()!!
                    textViewCity.text = data.name
                    textViewTemperature.text = "${data.main.temp}°C"
                    textViewWindDirection.text = "${data.wind.deg}°"
                    textViewWindSpeed.text = "${data.wind.speed}м/с"
                    val iconId = data.weather[0].icon
                    val imageUrl = "https://openweathermap.org/img/wn/$iconId@4x.png"
                    Picasso.get().load(imageUrl).into(imageView)
                    val atmosphericPressure = (data.main.pressure / 1.33).toInt()
                    textViewAtmosphericPressure.text = "$atmosphericPressure mm Hg"
                }
            }
        }
    }
}