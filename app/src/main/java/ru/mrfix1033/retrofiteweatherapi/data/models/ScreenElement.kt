package ru.mrfix1033.retrofiteweatherapi.data.models

import android.os.Parcel
import android.os.Parcelable

class ScreenElement(val text: String, val imageResource: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeInt(imageResource)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScreenElement> {
        override fun createFromParcel(parcel: Parcel): ScreenElement {
            return ScreenElement(parcel)
        }

        override fun newArray(size: Int): Array<ScreenElement?> {
            return arrayOfNulls(size)
        }
    }
}