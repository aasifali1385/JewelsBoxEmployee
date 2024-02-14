package com.jewelsbox.employee.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.jewelsbox.employee.MainActivity
import com.jewelsbox.employee.R
import com.jewelsbox.employee.retrofit.SP

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        val sp = SP(this)

        viewModel.autoLogin(sp.getDeviceId(), {
            sp.saveUser(it)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }
}