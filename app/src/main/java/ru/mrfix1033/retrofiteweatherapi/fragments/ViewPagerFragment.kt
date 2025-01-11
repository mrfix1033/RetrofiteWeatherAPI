package ru.mrfix1033.retrofiteweatherapi.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ru.mrfix1033.retrofiteweatherapi.R
import ru.mrfix1033.retrofiteweatherapi.data.models.ScreenElement
import ru.mrfix1033.retrofiteweatherapi.activities.SelectCityActivity
import ru.mrfix1033.retrofiteweatherapi.enumerations.Key

class ViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireArguments().run {
            val screenElement = getParcelable(Key.screenElement, ScreenElement::class.java)!!
            view.findViewById<ImageView>(R.id.imageView)
                .setImageResource(screenElement.imageResource)
            view.findViewById<TextView>(R.id.textView).text = screenElement.text
            if (getBoolean(Key.isLast)) {
                val buttonGoToWeather: Button = view.findViewById(R.id.buttonGoToWeather)
                buttonGoToWeather.visibility = View.VISIBLE
                buttonGoToWeather.setOnClickListener {
                    startActivity(Intent(view.context, SelectCityActivity::class.java))
                }
            }
        }
    }
}