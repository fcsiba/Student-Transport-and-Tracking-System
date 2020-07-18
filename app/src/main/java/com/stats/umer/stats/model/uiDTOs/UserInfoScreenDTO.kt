package com.stats.umer.stats.model.uiDTOs

import android.os.Parcel
import android.os.Parcelable
import com.stats.umer.stats.model.LoginUserDataNode
import java.lang.Exception

class UserInfoScreenDTO : Parcelable {
    constructor()
    constructor(loginUserData: LoginUserDataNode)
    {
        try {
            userId = loginUserData.id.toInt()
        } catch (ex: Exception) {
            userId = 0
            ex.printStackTrace()
        }
        userName = loginUserData.name
        try {
            userType = loginUserData.type.toInt()
        } catch (ex: Exception) {
            userType = 1
            ex.printStackTrace()
        }
        try {
            latitude = loginUserData.latitude.toDouble()
        } catch (ex: Exception) {
            latitude = 25.1935599
            ex.printStackTrace()
        }
        try {
            longitude = loginUserData.longitude.toDouble()
        } catch (ex: Exception) {
            longitude = 66.8752774
            ex.printStackTrace()
        }
    }
    var userId: Int = 0
    var userName: String = ""
    var userType: Int = 1
    var latitude: Double = 25.1935599
    var longitude: Double = 66.8752774

    constructor(parcel: Parcel) : this() {
        userId = parcel.readInt()
        userName = parcel.readString() ?: ""
        userType = parcel.readInt()
        latitude = parcel.readDouble()
        longitude = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userId)
        parcel.writeString(userName)
        parcel.writeInt(userType)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfoScreenDTO> {
        override fun createFromParcel(parcel: Parcel): UserInfoScreenDTO {
            return UserInfoScreenDTO(parcel)
        }

        override fun newArray(size: Int): Array<UserInfoScreenDTO?> {
            return arrayOfNulls(size)
        }
    }
}