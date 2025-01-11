package ru.mrfix1033.retrofiteweatherapi.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import ru.mrfix1033.retrofiteweatherapi.utils.FragmentReplacer
import ru.mrfix1033.retrofiteweatherapi.R
import ru.mrfix1033.retrofiteweatherapi.activities.WeatherActivity
import ru.mrfix1033.retrofiteweatherapi.data.CityData
import ru.mrfix1033.retrofiteweatherapi.enumerations.Key

class InputCityFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_input_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextCity: EditText = view.findViewById(R.id.editTextCity)

        val fragmentReplacer = requireActivity() as FragmentReplacer

        view.findViewById<Button>(R.id.buttonBack).setOnClickListener {
            fragmentReplacer.replace(SelectCityModeFragment())
        }

        view.findViewById<Button>(R.id.buttonNext).setOnClickListener {
            val intent = Intent(view.context, WeatherActivity::class.java)
            intent.putExtra(Key.cityMode, Key.cityModeByName)
            intent.putExtra(Key.cityData, CityData(editTextCity.text.toString()))
            startActivity(intent)
        }
    }
}