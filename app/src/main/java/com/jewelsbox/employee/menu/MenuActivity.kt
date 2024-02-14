package com.jewelsbox.employee.menu

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.jewelsbox.employee.auth.AuthViewModel
import com.jewelsbox.employee.auth.LoginActivity
import com.jewelsbox.employee.databinding.ActivityMenuBinding
import com.jewelsbox.employee.retrofit.SP


class MenuActivity : AppCompatActivity() {


    private lateinit var bind: ActivityMenuBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        val sp = SP(this)

        sp.getUser {
            if (it["profile_pic"] != "") bind.profileImage.load(it["profile_pic"])
            bind.empDetails.text = "${it["full_name"]}\n${it["user_code"]}"

        }


        bind.helpSupport.setOnClickListener {
            startActivity(Intent(this, HelpSupportActivity::class.java))
        }

        bind.termsConditions.setOnClickListener {
            startActivity(Intent(this, CMSActivity::class.java)
                .apply { putExtra("type", "terms") })
        }
        bind.privacyPolicy.setOnClickListener {
            startActivity(Intent(this, CMSActivity::class.java)
                .apply { putExtra("type", "policy") })
        }

        bind.ratePlayStore.setOnClickListener {
            Toast.makeText(this, "Not Available on Play Store", Toast.LENGTH_SHORT).show()
        }


        bind.back.setOnClickListener { finish() }
        bind.logout.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Are you sure, want to logout?")
            dialog.setNegativeButton("Cancel") { dg, _ -> dg.cancel() }
            dialog.setPositiveButton("Yes, Logout") { dg, _ ->
                dg.cancel()

                bind.logout.isEnabled = false
                bind.logout.text = "Logging out..."

                viewModel.logout(sp.getId(), {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finishAffinity()
                }, {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                })
            }
            dialog.show()
        }


        try {
            val versionName: String = packageManager.getPackageInfo(packageName, 0).versionName
            bind.appVersion.text = "App v$versionName"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }


    }


}