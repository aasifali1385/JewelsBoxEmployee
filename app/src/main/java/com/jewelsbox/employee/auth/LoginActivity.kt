package com.jewelsbox.employee.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.jewelsbox.employee.MainActivity
import com.jewelsbox.employee.databinding.ActivityLoginBinding
import com.jewelsbox.employee.retrofit.SP

class LoginActivity : AppCompatActivity() {

    private lateinit var bind: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        val sp = SP(this)

        bind.login.setOnClickListener {

            when {
                bind.editEmpId.text.isEmpty() -> bind.editEmpId.error = "Please enter employee ID"
                bind.editPass.text.isEmpty() -> bind.editPass.error = "Please enter password"
                else -> {
                    bind.login.isEnabled = false
                    bind.login.text = "Logging in..."

                    viewModel.login(
                        bind.editEmpId.text.toString(),
                        bind.editPass.text.toString(),
                        sp.getDeviceId(),
                        {
                            sp.saveUser(it)
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }, {
                            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                            bind.login.isEnabled = true
                            bind.login.text = "Login"
                        })
                }
            }

        }


    }

}