package ru.mrfix1033.retrofiteweatherapi.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import ru.mrfix1033.retrofiteweatherapi.utils.FragmentReplacer
import ru.mrfix1033.retrofiteweatherapi.R
import ru.mrfix1033.retrofiteweatherapi.fragments.SelectCityModeFragment

class SelectCityActivity : AppCompatActivity(), FragmentReplacer {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select_city)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, SelectCityModeFragment())
            .commit()
    }

    override fun replace(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}