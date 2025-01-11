package ru.mrfix1033.retrofiteweatherapi.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import ru.mrfix1033.retrofiteweatherapi.utils.FragmentReplacer
import ru.mrfix1033.retrofiteweatherapi.R
import ru.mrfix1033.retrofiteweatherapi.activities.WeatherActivity
import ru.mrfix1033.retrofiteweatherapi.data.CityData
import ru.mrfix1033.retrofiteweatherapi.enumerations.Key
import ru.mrfix1033.retrofiteweatherapi.enumerations.RequestCode

class SelectCityModeFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_city_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val fragmentReplacer = requireActivity() as FragmentReplacer

        view.findViewById<Button>(R.id.buttonMyCity).setOnClickListener {

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) requireActivity().requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), RequestCode.requestPermissions
            ) else getLocationAndStartWeatherActivity()
        }

        view.findViewById<Button>(R.id.buttonInputCity).setOnClickListener {
            fragmentReplacer.replace(InputCityFragment())
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            RequestCode.requestPermissions -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    getLocationAndStartWeatherActivity()
                }
                return
            }
        }
    }

    fun getLocationAndStartWeatherActivity() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location ->
                val intent = Intent(requireContext(), WeatherActivity::class.java)
                intent.putExtra(Key.cityMode, Key.cityModeByCoords)
                intent.putExtra(
                    Key.cityData,
                    CityData(location.latitude, location.longitude)
                )
                startActivity(intent)
            }
    }
}