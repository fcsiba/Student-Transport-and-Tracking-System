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
import com.stats.umer.stats.model.uiDTOs.UserInfoScreenDTO

class ParentHomeScreenActivity : AppCompatActivity() {
    @BindView(R.id.tvParentHomeScreenUserName)
    lateinit var tvParentHomeScreenUserName: TextView
    @BindView(R.id.btParentHomeScreenLogout)
    lateinit var btParentHomeScreenLogout: Button
    @BindView(R.id.tvTrackStudentButton)
    lateinit var tvTrackStudentButton: TextView
    @BindView(R.id.tvReportIssueButton)
    lateinit var tvReportIssueButton: TextView
    @BindView(R.id.tvReserveSeatButton)
    lateinit var tvReserveSeatButton: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_home_screen)
        ButterKnife.bind(this)
        var userInfo: UserInfoScreenDTO? = null
        if (intent.hasExtra(ConstantValues.loggedInUserInfoExtra))
        {
            userInfo = intent.extras?.get(ConstantValues.loggedInUserInfoExtra) as UserInfoScreenDTO
        }
        if (userInfo != null)
        {
            tvParentHomeScreenUserName.text = userInfo.userName
        }
        tvTrackStudentButton.setOnClickListener {
            val intent = Intent(this, LocationActivity::class.java)
            intent.putExtra(ConstantValues.loggedInUserInfoExtra, userInfo)
            startActivity(intent)
        }
        tvReportIssueButton.setOnClickListener {
            val intent = Intent(this, ReportIssueActivity::class.java)
            intent.putExtra(ConstantValues.loggedInUserInfoExtra, userInfo)
            startActivity(intent)
        }
        tvReserveSeatButton.setOnClickListener {
            val intent = Intent(this, StopListActivity::class.java)
            intent.putExtra(ConstantValues.loggedInUserInfoExtra, userInfo)
            startActivity(intent)
        }
        btParentHomeScreenLogout.setOnClickListener {
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