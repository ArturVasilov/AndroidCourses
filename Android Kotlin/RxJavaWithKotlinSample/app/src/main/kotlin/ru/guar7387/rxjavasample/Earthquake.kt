package ru.guar7387.rxjavasample

import android.os.Parcel
import android.os.Parcelable

data public class Earthquake(public val dateTime: Long,
                             public val magnitude: Int,
                             public val location: String) : Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(dateTime)
        dest.writeInt(magnitude)
        dest.writeString(location)
    }

    companion object {

        public val CREATOR: Parcelable.Creator<Earthquake> = object : Parcelable.Creator<Earthquake> {
            override fun createFromParcel(`in`: Parcel): Earthquake {
                return Earthquake(`in`.readLong(), `in`.readInt(), `in`.readString())
            }

            override fun newArray(size: Int): Array<Earthquake?> {
                return arrayOfNulls(size)
            }
        }
    }

}

