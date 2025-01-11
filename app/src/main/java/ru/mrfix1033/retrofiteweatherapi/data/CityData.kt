package ru.mrfix1033.retrofiteweatherapi.data

import android.os.Parcel
import android.os.Parcelable

class CityData() : Parcelable {
    var cityName: String? = null
    var lat: Double? = null
    var lon: Double? = null

    constructor(parcel: Parcel) : this() {
        cityName = parcel.readString()
        lat = parcel.readValue(Double::class.java.classLoader) as? Double
        lon = parcel.readValue(Double::class.java.classLoader) as? Double
    }

    constructor(cityName: String) : this() {
        this.cityName = cityName
    }

    constructor(lat: Double, lon: Double) : this() {
        this.lat = lat
        this.lon = lon
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cityName)
        parcel.writeValue(lat)
        parcel.writeValue(lon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CityData> {
        override fun createFromParcel(parcel: Parcel): CityData {
            return CityData(parcel)
        }

        override fun newArray(size: Int): Array<CityData?> {
            return arrayOfNulls(size)
        }
    }
}