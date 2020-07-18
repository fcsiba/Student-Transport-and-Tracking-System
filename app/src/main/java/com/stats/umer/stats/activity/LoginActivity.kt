package com.stats.umer.stats.activity

import android.Manifest.permission.*
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import butterknife.BindView
import butterknife.ButterKnife
import com.github.clans.fab.FloatingActionButton
import com.stats.umer.stats.*
import com.stats.umer.stats.Enum


class LoginActivity: AppCompatActivity(){

    @BindView(R.id.edLoginUserName)
    lateinit var edLoginUserName: EditText
    @BindView(R.id.ddLoginUserType)
    lateinit var ddLoginUserType: Spinner
    @BindView(R.id.edLoginUserPassword)
    lateinit var edLoginUserPassowrd: EditText
    @BindView(R.id.fabLogInButton)
    lateinit var fabLogInButton: FloatingActionButton

    val statsNetworkService = StatsNetworkService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
        checkForPermission()
        fabLogInButton.setOnClickListener {
            val userName = edLoginUserName.text
            val userPassword = edLoginUserPassowrd.text
            val selectedUserType = ddLoginUserType.selectedItem as String
            val userType: Int = when {
                selectedUserType.equals("Student") -> {
                    1
                }
                selectedUserType.equals("Parent") -> {
                    3
                }
                else -> {
                    2
                }
            }

            if (userName.isEmpty() || userPassword.isEmpty())
            {
                var alertMessage = "Please enter your credentials."
                val alertDialog = AlertDialog.Builder(this@LoginActivity).create()
                alertDialog.setTitle("Error")
                alertDialog.setMessage(alertMessage)
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, which -> dialog.dismiss() }
                alertDialog.show()
            }
            else
            {
                val dialog = ProgressDialog.show(this@LoginActivity, "",
                        "Logging in. Please wait...", true)
                dialog.show()
                statsNetworkService.loginUser(userName.toString(), userPassword.toString(), userType, CallbackInterface { callResponse ->
                    dialog.dismiss()
                    if (callResponse.status == Enum.ServerStatusCodes.Success)
                    {
                        val intent: Intent
                        if (userType == 1)
                        {
                            intent = Intent(this@LoginActivity, StudnetHomeScreenActivity::class.java)
                        }
                        else if (userType == 3)
                        {
                            intent = Intent(this@LoginActivity, ParentHomeScreenActivity::class.java)
                        }
                        else
                        {
                            intent = Intent(this@LoginActivity, LocationActivity::class.java)
                            intent.putExtra(ConstantValues.locationActivityPreviousActivityExtra, ConstantValues.driverPosition)
                        }
                        intent.putExtra(ConstantValues.loggedInUserInfoExtra, callResponse.data)
                        startActivity(intent)
                    }
                    else
                    {
                        var alertMessage = "There was an error while logging in."
                        if (callResponse.isShowToUser)
                        {
                            alertMessage = callResponse.statusMessage
                        }
                        val alertDialog = AlertDialog.Builder(this@LoginActivity).create()
                        alertDialog.setTitle("Error")
                        alertDialog.setMessage(alertMessage)
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
                        ) { dialog, which -> dialog.dismiss() }
                        alertDialog.show()
                    }
                })
            }
        }
    }
    fun checkForPermission()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@LoginActivity, arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), 100);
        }
    }
}