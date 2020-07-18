package com.stats.umer.stats

import android.os.Handler
import android.os.Looper
import android.util.Log
import okhttp3.*
import okhttp3.FormBody
import java.io.IOException
import java.util.concurrent.TimeUnit


class NetworkManager {
    val TIMEOUT: Long = 300
    var mInstance: NetworkManager? = null
    var mClient: OkHttpClient? = null

    fun getInstance(): NetworkManager
    {
        if (mInstance == null)
        {
            mInstance = NetworkManager()
        }
        return mInstance as NetworkManager
    }

    constructor()
    {
        mClient = OkHttpClient.Builder().connectTimeout(TIMEOUT, TimeUnit.SECONDS).readTimeout(TIMEOUT, TimeUnit.SECONDS).writeTimeout(TIMEOUT, TimeUnit.SECONDS).build()
    }

    fun post(requestUrl: String, parameters: HashMap<String, Any>?, callback: CallbackInterface<CallbackDataParser<String>>) {

        var request: Request? = null

        val headerBuilder = Headers.Builder()

        if (parameters == null)
        {
            request = Request.Builder()
                    .url(requestUrl)
                    .build()
        }
        else {

            val requestBody = getRequestBody(parameters)

            request = Request.Builder()
                    .url(requestUrl)
                    .post(requestBody)
                    .build()
        }

        Log.i("Request Params", parameters.toString())
        Log.i("Request Url:", requestUrl)

        mClient?.newCall(request)?.enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                val callbackData = CallbackDataParser(Enum.ServerStatusCodes.Failure, e.message.toString())
                callback.onMethodCompletion(callbackData)
            }

            override fun onResponse(call: Call, response: Response) {
                val mHandler = Handler(Looper.getMainLooper()) // This Process makes sure that callback is returned on the UI thread from which it was called to avoid ambigous UI access error.
                mHandler.post {
                    val callbackData = CallbackDataParser(Enum.ServerStatusCodes.Success, response.body?.string() ?: "")
                    callback.onMethodCompletion(callbackData)
                }
            }
        })
    }

    private fun getRequestBody(parameters: HashMap<String, Any>): RequestBody
    {
        val builder = FormBody.Builder()
        for(parameter in parameters)
        {
            builder.add(parameter.key, parameter.value.toString())
        }

        return builder.build()
    }

}