package com.stats.umer.stats.activity

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import butterknife.BindView
import butterknife.ButterKnife
import com.github.clans.fab.FloatingActionButton
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.stats.umer.stats.*
import com.stats.umer.stats.Enum
import com.stats.umer.stats.model.uiDTOs.SeatReservationScreenDTO
import com.stats.umer.stats.model.uiDTOs.UserInfoScreenDTO


class LocationActivity: AppCompatActivity(), OnMapReadyCallback {

    @BindView(R.id.rlSeatInfo)
    lateinit var rlSeatInfo: RelativeLayout
    @BindView(R.id.tvLocationUserName)
    lateinit var tvLocationUserName: TextView
    @BindView(R.id.btLocationScreenLogoutButton)
    lateinit var btLocationScreenLogoutButton: Button
    @BindView(R.id.tvStopName)
    lateinit var tvStopName: TextView
    @BindView(R.id.tvStopBusInfo)
    lateinit var tvStopBusInfo: TextView
    @BindView(R.id.tvTrackStudentTitle)
    lateinit var tvTrackStudentTitle: TextView
    @BindView(R.id.tvBusInfo)
    lateinit var tvBusInfo: TextView
    @BindView(R.id.btReserveSeat)
    lateinit var btReserveSeat: TextView
    @BindView(R.id.tvBusSeatCount)
    lateinit var tvBusSeatCount: TextView
    @BindView(R.id.llSeatsText)
    lateinit var llSeatsText: LinearLayout
    @BindView(R.id.fabMapActivityBackButton)
    lateinit var fabMapActivityBackButton: FloatingActionButton

    var googleMap: GoogleMap? = null
    var mapView: MapView? = null
    var mLocationLatitude:Double = 0.0
    var mLocationLongitude:Double = 0.0
    var mUserId = 0
    var mUserType = 1

    val mStatsNetworkService = StatsNetworkService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        ButterKnife.bind(this)
        mapView = findViewById(R.id.mapView) as MapView
        mapView?.onCreate(savedInstanceState)
        mapView?.onResume()

        fabMapActivityBackButton.setOnClickListener {
            finish()
        }
        btLocationScreenLogoutButton.setOnClickListener {
            logoutUser()
        }
        var stopInfo: SeatReservationScreenDTO? = null
        var userInfo: UserInfoScreenDTO? = null
        var locationActivityType = ConstantValues.trackYourStudent
        if (intent.hasExtra(ConstantValues.stopInfoExtra))
        {
            stopInfo = intent.extras?.get(ConstantValues.stopInfoExtra) as SeatReservationScreenDTO
        }
        if (intent.hasExtra(ConstantValues.loggedInUserInfoExtra))
        {
            userInfo = intent.extras?.get(ConstantValues.loggedInUserInfoExtra) as UserInfoScreenDTO
        }
        if (intent.hasExtra(ConstantValues.locationActivityPreviousActivityExtra))
        {
            locationActivityType = intent.extras?.getInt(ConstantValues.locationActivityPreviousActivityExtra) ?: ConstantValues.trackYourStudent
        }
        if (userInfo != null)
        {
            tvLocationUserName.text = userInfo.userName
            mUserId = userInfo.userId
            mUserType = userInfo.userType
        }
        if (stopInfo != null)
        {
            tvTrackStudentTitle.visibility = View.GONE
            tvBusInfo.visibility = View.GONE
            rlSeatInfo.visibility = View.VISIBLE
            tvBusSeatCount.visibility = View.GONE
            llSeatsText.visibility = View.GONE
            tvStopName.text = stopInfo.stopName
            tvStopBusInfo.text = stopInfo.busNumber
            mLocationLatitude = stopInfo.stopLatitude
            mLocationLongitude = stopInfo.stopLongitude
            makeMapReady()
        }
        else if (locationActivityType == ConstantValues.driverPosition)
        {
            rlSeatInfo.visibility = View.GONE
            tvBusInfo.visibility = View.GONE
            tvTrackStudentTitle.visibility = View.GONE
            fabMapActivityBackButton.visibility = View.GONE
            tvBusSeatCount.visibility = View.VISIBLE
            llSeatsText.visibility = View.VISIBLE
            val sharedPref = getSharedPreferences(ConstantValues.statsSharedPrefs, Context.MODE_PRIVATE)
            val availableSeats = sharedPref.getInt(ConstantValues.reservedSeatsKey, 0)
            tvBusSeatCount.text = "$availableSeats / 20"
            mLocationLatitude = userInfo?.latitude ?: 25.1935599
            mLocationLongitude = userInfo?.longitude ?: 66.8752774
            makeMapReady()
        }
        else if (locationActivityType == ConstantValues.trackYourStudent)
        {
            tvBusSeatCount.visibility = View.GONE
            llSeatsText.visibility = View.GONE
            rlSeatInfo.visibility = View.GONE
            tvBusInfo.visibility = View.VISIBLE
            tvTrackStudentTitle.visibility = View.VISIBLE
            val dialog = ProgressDialog.show(this@LocationActivity, "",
                    "Fetching information. Please wait...", true)
            dialog.show()
            mStatsNetworkService.fetchChildLocation(mUserId, CallbackInterface { fetchStudentInfo ->
                dialog.dismiss()
                if (fetchStudentInfo.status == Enum.ServerStatusCodes.Success)
                {
                    mLocationLatitude = fetchStudentInfo.data.position.latitude
                    mLocationLongitude = fetchStudentInfo.data.position.longitude
                    tvBusInfo.text = "Bus Number: ${fetchStudentInfo.data.busNumber}"
                    makeMapReady()
                }
                else
                {
                    var alertMessage = "Oops there was an error while communicating with server."
                    if (fetchStudentInfo.isShowToUser)
                    {
                        alertMessage = fetchStudentInfo.statusMessage
                    }
                    val alertDialog = AlertDialog.Builder(this@LocationActivity).create()
                    alertDialog.setTitle("Error")
                    alertDialog.setMessage(alertMessage)
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, which ->
                        dialog.dismiss()
                        finish()
                    }
                    alertDialog.show()
                }
            })
        }
        btReserveSeat.setOnClickListener {
            val dialog = ProgressDialog.show(this@LocationActivity, "",
                    "Reserving Seat. Please wait...", true)
            dialog.show()
            mStatsNetworkService.reserveSeat(stopInfo?.busId ?: 0, stopInfo?.stopId ?: 0, userInfo?.userId ?: 0, CallbackInterface { fetchStudentInfo ->
                dialog.dismiss()
                if (fetchStudentInfo.status == Enum.ServerStatusCodes.Success)
                {
                    val alertDialog = AlertDialog.Builder(this@LocationActivity).create()
                    alertDialog.setTitle("Seat Reserved")
                    alertDialog.setMessage(fetchStudentInfo.data)
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, which ->
                        val sharedPref = getSharedPreferences(ConstantValues.statsSharedPrefs, Context.MODE_PRIVATE)
                        var availableSeats = sharedPref.getInt(ConstantValues.reservedSeatsKey, 0)
                        val sharedPreferenceEditor = getSharedPreferences(ConstantValues.statsSharedPrefs, Context.MODE_PRIVATE).edit()
                        availableSeats += 1
                        if (availableSeats == 20)
                        {
                            sharedPreferenceEditor.putInt(ConstantValues.reservedSeatsKey, 20).commit()
                        }
                        else
                        {
                            sharedPreferenceEditor.putInt(ConstantValues.reservedSeatsKey, availableSeats).commit()
                        }
                        dialog.dismiss()
                        finish()
                    }
                    alertDialog.show()
                }
                else
                {
                    var alertMessage = "Oops there was an error while communicating with server."
                    if (fetchStudentInfo.isShowToUser)
                    {
                        alertMessage = fetchStudentInfo.statusMessage
                    }
                    val alertDialog = AlertDialog.Builder(this@LocationActivity).create()
                    alertDialog.setTitle("Error")
                    alertDialog.setMessage(alertMessage)
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, which ->
                        dialog.dismiss()
                        finish()
                    }
                    alertDialog.show()
                }
            })
        }
    }

    fun makeMapReady()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            var alertMessage = "Location permission has not been allowed to the application. Please allow location permission in settings."
            val alertDialog = AlertDialog.Builder(this@LocationActivity).create()
            alertDialog.setTitle("Error")
            alertDialog.setMessage(alertMessage)
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, which ->
                dialog.dismiss()
                finish()
            }
            alertDialog.show()
        }
        else
        {
            mapView?.getMapAsync(this)
        }
    }

    fun logoutUser()
    {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onResume() {
        mapView?.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onMapReady(gMap: GoogleMap?) {
        googleMap = gMap
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true
        googleMap?.addMarker(MarkerOptions().position(LatLng(mLocationLatitude, mLocationLongitude)).title("Current Position"))
        val coordinate = LatLng(mLocationLatitude, mLocationLongitude)
        val location = CameraUpdateFactory.newLatLngZoom(coordinate, 15f)
        googleMap?.animateCamera(location)

    }

}