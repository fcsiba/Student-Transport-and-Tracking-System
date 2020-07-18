package com.stats.umer.stats.activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.github.clans.fab.FloatingActionButton
import com.stats.umer.stats.CallbackInterface
import com.stats.umer.stats.ConstantValues
import com.stats.umer.stats.Enum
import com.stats.umer.stats.R
import com.stats.umer.stats.StatsNetworkService
import com.stats.umer.stats.adapter.BusStopPointsRecyclerViewAdapter
import com.stats.umer.stats.model.uiDTOs.SeatReservationScreenDTO
import com.stats.umer.stats.model.uiDTOs.UserInfoScreenDTO

class StopListActivity: AppCompatActivity(), BusStopPointsRecyclerViewAdapter.StopItemClickListener {
    @BindView(R.id.rvBusStopList)
    lateinit var rvBusStopList: RecyclerView
    @BindView(R.id.tvStopListUserName)
    lateinit var tvStopListUserName: TextView
    @BindView(R.id.tvBusStopTitle)
    lateinit var tvBusStopTitle: TextView
    @BindView(R.id.btStopListLogoutButton)
    lateinit var btStopListLogoutButton: Button
    @BindView(R.id.fabBusStopPointsBackButton)
    lateinit var fabBusStopPointsBackButton: FloatingActionButton

    val mBusStopListRecyclerViewAdapter = BusStopPointsRecyclerViewAdapter(this)

    val mStatsNetworkService = StatsNetworkService()

    var mLoggedInUserInfo: UserInfoScreenDTO? = UserInfoScreenDTO()

    var mCampusToCampusShuttleScreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_list)
        ButterKnife.bind(this)
        mLoggedInUserInfo = null
        if (intent.hasExtra(ConstantValues.loggedInUserInfoExtra))
        {
            mLoggedInUserInfo = intent.extras?.get(ConstantValues.loggedInUserInfoExtra) as UserInfoScreenDTO
        }
        if (intent.hasExtra(ConstantValues.campusToCampusShuttleExtra))
        {
            mCampusToCampusShuttleScreen = intent.getBooleanExtra(ConstantValues.campusToCampusShuttleExtra, false)
        }
        if (mLoggedInUserInfo != null)
        {
            tvStopListUserName.text = mLoggedInUserInfo?.userName
        }
        fabBusStopPointsBackButton.setOnClickListener {
            finish()
        }
        btStopListLogoutButton.setOnClickListener {
            logoutUser()
        }
        if (mCampusToCampusShuttleScreen)
        {
            tvBusStopTitle.text = "Select your destination"
        }
        rvBusStopList.layoutManager = LinearLayoutManager(this@StopListActivity)
        rvBusStopList.adapter = mBusStopListRecyclerViewAdapter
        loadStopList()
    }
    fun loadStopList()
    {
        val dialog = ProgressDialog.show(this@StopListActivity, "",
                "Logging in. Please wait...", true)
        dialog.show()
        mStatsNetworkService.fetchBusInfo(CallbackInterface { callResponse ->
            dialog.dismiss()
            if (callResponse.status == Enum.ServerStatusCodes.Success)
            {
                var stopList = callResponse.data
                if (mCampusToCampusShuttleScreen)
                {
                    stopList = callResponse.data.filter { it.stopName.toLowerCase().contains("campus")} as ArrayList<SeatReservationScreenDTO>?
                }
                mBusStopListRecyclerViewAdapter.setDataSource(stopList)
            }
            else
            {
                val alertDialog = AlertDialog.Builder(this@StopListActivity).create()
                alertDialog.setTitle("Error")
                alertDialog.setMessage("There was a problem communicating with server.")
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, which -> dialog.dismiss() }
                alertDialog.show()
            }
        })
    }

    fun logoutUser()
    {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onStopItemClicked(stopInfo: SeatReservationScreenDTO) {
        val intent = Intent(this@StopListActivity, LocationActivity::class.java)
        intent.putExtra(ConstantValues.loggedInUserInfoExtra, mLoggedInUserInfo)
        intent.putExtra(ConstantValues.stopInfoExtra, stopInfo)
        startActivity(intent)
    }
}