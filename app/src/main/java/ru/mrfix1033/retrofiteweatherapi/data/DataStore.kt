package ru.mrfix1033.retrofiteweatherapi.data

import ru.mrfix1033.retrofiteweatherapi.R
import ru.mrfix1033.retrofiteweatherapi.data.models.ScreenElement

object DataStore {
    val screenElements = listOf(
        ScreenElement(
            "Добро пожаловать в приложение для просмотра погоды (смахните влево, чтобы продолжить)",
            R.drawable.screen_element_1_image
        ),
        ScreenElement(
            "Выберите \"Мой город\", чтобы посмотреть погоду в своём городе, или \"Ввести город\", чтобы увидеть погоду в другом городе",
            R.drawable.screen_element_2_image
        )
    )
}