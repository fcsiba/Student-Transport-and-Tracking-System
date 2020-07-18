package com.stats.umer.stats.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.stats.umer.stats.ConstantValues
import com.stats.umer.stats.R
import com.stats.umer.stats.StatsNetworkService
import com.stats.umer.stats.model.uiDTOs.UserInfoScreenDTO

class StudnetHomeScreenActivity: AppCompatActivity() {
    @BindView(R.id.tvPickupPointsButton)
    lateinit var tvPickPointsButton: TextView
    @BindView(R.id.tvDropOffPointsButton)
    lateinit var tvDropOffPointsButton: TextView
    @BindView(R.id.tvStudentUserName)
    lateinit var tvStudentUserName: TextView
    @BindView(R.id.tvCampusToCampusShuttleButton)
    lateinit var tvCampusToCampusShuttleButton: TextView
    @BindView(R.id.btStudentScreenLogoutButton)
    lateinit var btStudentScreenLogoutButton: Button

    val mStatsNetworkService = StatsNetworkService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home_screen)
        ButterKnife.bind(this)
        var userInfo: UserInfoScreenDTO? = null
        if (intent.hasExtra(ConstantValues.loggedInUserInfoExtra))
        {
            userInfo = intent.extras?.get(ConstantValues.loggedInUserInfoExtra) as UserInfoScreenDTO
        }
        if (userInfo != null)
        {
            tvStudentUserName.text = userInfo.userName
        }
        tvPickPointsButton.setOnClickListener {
            val intent = Intent(this, StopListActivity::class.java)
            intent.putExtra(ConstantValues.loggedInUserInfoExtra, userInfo)
            startActivity(intent)
        }
        tvDropOffPointsButton.setOnClickListener {
            val intent = Intent(this, StopListActivity::class.java)
            intent.putExtra(ConstantValues.loggedInUserInfoExtra, userInfo)
            startActivity(intent)
        }
        tvCampusToCampusShuttleButton.setOnClickListener {
            val intent = Intent(this, StopListActivity::class.java)
            intent.putExtra(ConstantValues.loggedInUserInfoExtra, userInfo)
            intent.putExtra(ConstantValues.campusToCampusShuttleExtra, true)
            startActivity(intent)
        }
        btStudentScreenLogoutButton.setOnClickListener {
            logoutUser()
        }
    }
    fun logoutUser()
    {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}