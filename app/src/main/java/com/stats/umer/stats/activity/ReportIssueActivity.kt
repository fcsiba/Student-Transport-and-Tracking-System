package com.stats.umer.stats.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.github.clans.fab.FloatingActionButton
import com.stats.umer.stats.ConstantValues
import com.stats.umer.stats.R
import com.stats.umer.stats.model.uiDTOs.UserInfoScreenDTO

class ReportIssueActivity: AppCompatActivity(), TextWatcher {

    @BindView(R.id.tvReportIssueUserName)
    lateinit var tvReportIssueUserName: TextView
    @BindView(R.id.edReportIssueTextBox)
    lateinit var edReportIssueTextBox: EditText
    @BindView(R.id.tvReportIssueCharacterCount)
    lateinit var tvReportIssueCharacterCount: TextView
    @BindView(R.id.fabReportIssueBackButton)
    lateinit var fabReportIssueBackButton: FloatingActionButton
    @BindView(R.id.btReportIssueLogoutButton)
    lateinit var btReportIssueLogoutButton: Button
    @BindView(R.id.btSubmitIssueButton)
    lateinit var btSubmitIssueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_issue)
        ButterKnife.bind(this)
        var userInfo: UserInfoScreenDTO? = null
        if (intent.hasExtra(ConstantValues.loggedInUserInfoExtra))
        {
            userInfo = intent.extras?.get(ConstantValues.loggedInUserInfoExtra) as UserInfoScreenDTO
        }
        if (userInfo != null)
        {
            tvReportIssueUserName.text = userInfo.userName
        }
        fabReportIssueBackButton.setOnClickListener {
            finish()
        }
        edReportIssueTextBox.addTextChangedListener(this)
        btSubmitIssueButton.setOnClickListener {
            val issueText = edReportIssueTextBox.text
            if (issueText.isNotEmpty())
            {
                var alertMessage = "Thank you for reaching out to us.\nWe will look into the matter as soon as possible."
                val alertDialog = AlertDialog.Builder(this@ReportIssueActivity).create()
                alertDialog.setTitle("Report Received")
                alertDialog.setMessage(alertMessage)
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, which ->
                    dialog.dismiss()
                    finish()
                }
                alertDialog.show()
                tvReportIssueCharacterCount.text = "0/200"
            }
        }
        btReportIssueLogoutButton.setOnClickListener {
            logoutUser()
        }
    }

    fun logoutUser()
    {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        val text = s.toString()
        tvReportIssueCharacterCount.text = "${text.length}/200"
    }
}