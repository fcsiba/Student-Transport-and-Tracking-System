package com.stats.umer.stats

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.stats.umer.stats.model.*
import com.stats.umer.stats.model.uiDTOs.SeatReservationScreenDTO
import com.stats.umer.stats.model.uiDTOs.TrackChildrenInfoDTO
import com.stats.umer.stats.model.uiDTOs.UserInfoScreenDTO


class StatsNetworkService {
    val mNetworkManager: NetworkManager
    val mGson: Gson

    init {
        mNetworkManager = NetworkManager()
        mGson = GsonBuilder().disableHtmlEscaping().create()
    }

    fun loginUser(userName: String, userPassword: String, userType: Int, callbackInterface: CallbackInterface<CallbackDataParser<UserInfoScreenDTO>>) {
        var requestUrl = ConstantValues.logInStudentUrl
        val hashMap = HashMap<String, Any>()
        hashMap["cnic"] = userName
        hashMap["password"] = userPassword
        hashMap["type"] = userType
        when (userType)
        {
            1 -> requestUrl = ConstantValues.logInStudentUrl
            3 -> requestUrl = ConstantValues.logInParentUrl
            2 -> requestUrl = ConstantValues.logInDriverUrl
        }
        mNetworkManager.post(requestUrl, hashMap, CallbackInterface { callbackDataParser ->
            if (callbackDataParser.status == Enum.ServerStatusCodes.Success)
            {
                var responseStatus = Enum.ServerStatusCodes.Success
                try {
                    val resultResponse = mGson.fromJson(callbackDataParser.data, LoginResponseDTO::class.java)
                    responseStatus = Enum.ServerStatusCodes.fromString(resultResponse.status)
                    if (responseStatus == Enum.ServerStatusCodes.Success)
                    {
                        val userInfo = UserInfoScreenDTO(resultResponse.data)
                        callbackInterface.onMethodCompletion(CallbackDataParser(Enum.ServerStatusCodes.Success, userInfo))
                    }
                    else
                    {
                        callbackInterface.onMethodCompletion(CallbackDataParser(Enum.ServerStatusCodes.Failure, resultResponse.message, true))
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    callbackInterface.onMethodCompletion(CallbackDataParser(Enum.ServerStatusCodes.Failure, "Error", false))
                }
            }
            else
            {
                callbackInterface.onMethodCompletion(CallbackDataParser(Enum.ServerStatusCodes.Failure, callbackDataParser.statusMessage, callbackDataParser.isShowToUser))
            }
        })
    }

    fun fetchBusInfo(callbackInterface: CallbackInterface<CallbackDataParser<ArrayList<SeatReservationScreenDTO>>>)
    {
        val hashMap = HashMap<String, Any>()
        hashMap["islogin"] = true
        hashMap["type"] = 1
        mNetworkManager.post(ConstantValues.fetchBusInfoUrl, hashMap, CallbackInterface { callbackDataParser ->
            if (callbackDataParser.status == Enum.ServerStatusCodes.Success) {
                var responseStatus = Enum.ServerStatusCodes.Success
                if (responseStatus == Enum.ServerStatusCodes.Success)
                {
                    fetchStopInfo(CallbackInterface { stopListCallResponse ->
                        if (stopListCallResponse.status == Enum.ServerStatusCodes.Success)
                        {
                            try {
                                val resultResponse = mGson.fromJson(callbackDataParser.data, BusInfoResponseDTO::class.java)
                                responseStatus = Enum.ServerStatusCodes.fromString(resultResponse.status)
                                if (responseStatus == Enum.ServerStatusCodes.Success)
                                {
                                    val jsonString = mGson.toJson(resultResponse.data)
                                    val busInfoMap = Gson().fromJson(jsonString, object : TypeToken<HashMap<String?, BusInfoNodeDTO>>() {}.type) as HashMap<String, BusInfoNodeDTO>
                                    val busList = ArrayList<BusInfoNodeDTO>()
                                    for (busInfo in busInfoMap) {
                                        busList.add(busInfo.value)
                                    }
                                    val stopListScreenList = ArrayList<SeatReservationScreenDTO>()
                                    for (stop in stopListCallResponse.data)
                                    {
                                        var busForStop = busList.find { it.current_stop.equals(stop.id) || it.next_stop.equals(stop.id) || stop.id.toInt() < it.current_stop.toInt()}
                                        if (busForStop != null)
                                        {
                                            stopListScreenList.add(SeatReservationScreenDTO(stop, busForStop))
                                        }
                                        else
                                        {
                                            busForStop = busList.first()
                                            stopListScreenList.add(SeatReservationScreenDTO(stop, busForStop))
                                        }
                                    }
                                    callbackInterface.onMethodCompletion(CallbackDataParser(responseStatus, stopListScreenList))
                                }
                                else
                                {
                                    callbackInterface.onMethodCompletion(CallbackDataParser(responseStatus, resultResponse.message, true))
                                }
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                                callbackInterface.onMethodCompletion(CallbackDataParser(Enum.ServerStatusCodes.Failure, "Error", false))
                            }
                        }
                        else
                        {
                            callbackInterface.onMethodCompletion(CallbackDataParser(Enum.ServerStatusCodes.Failure, "Error", false))
                        }
                    })
                }
                else
                {
                    callbackInterface.onMethodCompletion(CallbackDataParser(responseStatus, "Error", true))
                }
            }
        })
    }

    fun fetchStopInfo(callbackInterface: CallbackInterface<CallbackDataParser<ArrayList<StopListInfoNodeDTO>>>)
    {
        val hashMap = HashMap<String, Any>()
        hashMap["islogin"] = true
        hashMap["type"] = 1
        mNetworkManager.post(ConstantValues.fetchStopInfoUrl, hashMap, CallbackInterface { callbackDataParser ->
            if (callbackDataParser.status == Enum.ServerStatusCodes.Success) {
                var responseStatus = Enum.ServerStatusCodes.Success
                if (responseStatus == Enum.ServerStatusCodes.Success)
                {
                    try {
                        val resultResponse = mGson.fromJson(callbackDataParser.data, StopListResponseDTO::class.java)
                        responseStatus = Enum.ServerStatusCodes.fromString(resultResponse.status)
                        val jsonString = mGson.toJson(resultResponse.data)
                        val stopListMap = Gson().fromJson(jsonString, object : TypeToken<HashMap<String?, StopListInfoNodeDTO>>() {}.type) as HashMap<String, StopListInfoNodeDTO>
                        val stopList = ArrayList<StopListInfoNodeDTO>()
                        for (stopInfo in stopListMap) {
                            stopList.add(stopInfo.value)
                        }
                        callbackInterface.onMethodCompletion(CallbackDataParser(responseStatus, stopList))
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        callbackInterface.onMethodCompletion(CallbackDataParser(Enum.ServerStatusCodes.Failure, "Error", false))
                    }
                }
                else
                {
                    callbackInterface.onMethodCompletion(CallbackDataParser(responseStatus, "Error", true))
                }
            }
        })
    }

    fun fetchChildLocation (id:Int, callbackInterface: CallbackInterface<CallbackDataParser<TrackChildrenInfoDTO>>)
    {
        val hashMap = HashMap<String, Any>()
        hashMap["id"] = id
        hashMap["type"] = 3
        mNetworkManager.post(ConstantValues.trackStudentUrl, hashMap, CallbackInterface { networkResponse ->
            if (networkResponse.status == Enum.ServerStatusCodes.Success)
            {
                val response = mGson.fromJson(networkResponse.data, DefaultNetworkResponseDTO::class.java)
                val returnResponse = Enum.ServerStatusCodes.fromString(response.status)
                if (returnResponse == Enum.ServerStatusCodes.Success)
                {
                    val substr1 = response.message.substringAfter("having no '")
                    val substr2 = substr1.substringBefore("'")
                    fetchBusInfo(CallbackInterface { buslistInfo ->
                        if (buslistInfo.status == Enum.ServerStatusCodes.Success)
                        {
                            val busInfo = buslistInfo.data.find { it.busNumber == substr2 }
                            if (busInfo != null)
                            {
                                val position = LatLng(busInfo.stopLatitude, busInfo.stopLongitude)
                                val studentPositionInfo = TrackChildrenInfoDTO (position, substr2)
                                callbackInterface.onMethodCompletion(CallbackDataParser(Enum.ServerStatusCodes.Success, studentPositionInfo))
                            }
                            else
                            {
                                callbackInterface.onMethodCompletion(CallbackDataParser(returnResponse, "Unable to find bus location", true))
                            }
                        }
                        else
                        {
                            callbackInterface.onMethodCompletion(CallbackDataParser(returnResponse, "Unable to find bus location", true))
                        }
                    })
                }
                else
                {
                    callbackInterface.onMethodCompletion(CallbackDataParser(returnResponse, response.message, true))
                }
            }
        })
    }

    fun reserveSeat(busId:Int, stopId:Int, studentId:Int, callbackInterface: CallbackInterface<CallbackDataParser<String>>)
    {
        val hashMap = HashMap<String, Any> ()
        hashMap["islogin"] = true
        hashMap["type"] = 1
        hashMap["s_id"] = studentId
        hashMap["pickup_stop_id"] = stopId
        hashMap["b_id"] = busId
        mNetworkManager.post(ConstantValues.reserveSeatUrl, hashMap, CallbackInterface { networkCallResponse ->
            if (networkCallResponse.status == Enum.ServerStatusCodes.Success)
            {
                val resultResponse = mGson.fromJson(networkCallResponse.data, DefaultNetworkResponseDTO::class.java)
                val responseStatus = Enum.ServerStatusCodes.fromString(resultResponse.status)
                if (responseStatus == Enum.ServerStatusCodes.Success)
                {
                    getBus(busId, studentId, CallbackInterface { gotBusCallResponse ->
                        if (gotBusCallResponse.status == Enum.ServerStatusCodes.Success)
                        {
                            callbackInterface.onMethodCompletion(CallbackDataParser(Enum.ServerStatusCodes.Success, resultResponse.message))
                        }
                        else
                        {
                            callbackInterface.onMethodCompletion(CallbackDataParser(Enum.ServerStatusCodes.Failure, gotBusCallResponse.statusMessage, gotBusCallResponse.isShowToUser))
                        }
                    })
                }
                else
                {
                    callbackInterface.onMethodCompletion(CallbackDataParser(Enum.ServerStatusCodes.Failure, resultResponse.message, true))
                }
            }
            else
            {
                Log.e("Error Log", networkCallResponse.statusMessage)
                callbackInterface.onMethodCompletion(CallbackDataParser(Enum.ServerStatusCodes.Failure, networkCallResponse.statusMessage, false))
            }
        })
    }

    fun getBus(busId: Int, studentId: Int, callbackInterface: CallbackInterface<CallbackDataParser<Void>>)
    {
        val hashMap = HashMap<String, Any> ()
        hashMap["islogin"] = true
        hashMap["checkin"] = true
        hashMap["type"] = 1
        hashMap["s_id"] = studentId
        hashMap["b_id"] = busId
        mNetworkManager.post(ConstantValues.gotBusUrl, hashMap, CallbackInterface { networkCallResponse ->
            if (networkCallResponse.status == Enum.ServerStatusCodes.Success)
            {
                val resultResponse = mGson.fromJson(networkCallResponse.data, DefaultNetworkResponseDTO::class.java)
                val responseStatus = Enum.ServerStatusCodes.fromString(resultResponse.status)
                if (responseStatus == Enum.ServerStatusCodes.Success)
                {
                    callbackInterface.onMethodCompletion(CallbackDataParser())
                }
                else
                {
                    callbackInterface.onMethodCompletion(CallbackDataParser(Enum.ServerStatusCodes.Failure, resultResponse.message, true))
                }
            }
            else
            {
                Log.e("Error Log", networkCallResponse.statusMessage)
                callbackInterface.onMethodCompletion(CallbackDataParser(Enum.ServerStatusCodes.Failure, networkCallResponse.statusMessage, false))
            }
        })
    }

}