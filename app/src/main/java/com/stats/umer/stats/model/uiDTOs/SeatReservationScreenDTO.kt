package com.stats.umer.stats.model.uiDTOs

import android.os.Parcel
import android.os.Parcelable
import com.stats.umer.stats.model.BusInfoNodeDTO
import com.stats.umer.stats.model.StopListInfoNodeDTO
import java.lang.Exception

class SeatReservationScreenDTO : Parcelable {
    constructor()

    constructor(stopInfo: StopListInfoNodeDTO, busInfo: BusInfoNodeDTO)
    {
        try {
            busId = busInfo.id.toInt()
        }
        catch (ex: Exception) {
            busId = 0
            ex.printStackTrace()
        }
        busNumber = busInfo.bus_no
        try {
            stopId = stopInfo.id.toInt()
        }
        catch (ex: Exception) {
            stopId = 0
            ex.printStackTrace()
        }
        stopName = stopInfo.name
        pickupSchedule = ""
        stopLatitude = stopInfo.password.toDouble()
        stopLongitude = stopInfo.father_name.toDouble()
    }

    var stopId: Int = 0
    var busId: Int = 0
    var stopName: String = ""
    var busNumber: String = ""
    var pickupSchedule: String = ""
    var stopLatitude: Double = 0.0
    var stopLongitude: Double = 0.0

    constructor(parcel: Parcel) : this() {
        stopId = parcel.readInt()
        busId = parcel.readInt()
        stopName = parcel.readString() ?: ""
        busNumber = parcel.readString() ?: ""
        pickupSchedule = parcel.readString() ?: ""
        stopLatitude = parcel.readDouble()
        stopLongitude = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(stopId)
        parcel.writeInt(busId)
        parcel.writeString(stopName)
        parcel.writeString(busNumber)
        parcel.writeString(pickupSchedule)
        parcel.writeDouble(stopLatitude)
        parcel.writeDouble(stopLongitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SeatReservationScreenDTO> {
        override fun createFromParcel(parcel: Parcel): SeatReservationScreenDTO {
            return SeatReservationScreenDTO(parcel)
        }

        override fun newArray(size: Int): Array<SeatReservationScreenDTO?> {
            return arrayOfNulls(size)
        }
    }
}