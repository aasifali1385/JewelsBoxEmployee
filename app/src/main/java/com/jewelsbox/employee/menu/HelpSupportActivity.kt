package com.jewelsbox.employee.menu

import android.app.Notification.Action
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.jewelsbox.employee.R
import com.jewelsbox.employee.retrofit.SP

class HelpSupportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_support)


        findViewById<ImageView>(R.id.back).setOnClickListener { finish() }
        val email = findViewById<TextView>(R.id.email)
        val whatsapp = findViewById<TextView>(R.id.whatsapp)
        val phone = findViewById<TextView>(R.id.phone)


        val viewModel = ViewModelProvider(this)[MenuViewModel::class.java]
        val sp = SP(this)

        viewModel.helpSupport(sp.getId(), { emailId, whatsappNo, phoneNo ->

            email.text = emailId
            whatsapp.text = whatsappNo
            phone.text = phoneNo

            email.setOnClickListener { email(emailId) }
            whatsapp.setOnClickListener { whatsapp(whatsappNo) }
            phone.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phoneNo")
                startActivity(intent)
            }


        }, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })


    }


    private fun email(email: String) {
        try {
            startActivity(Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, "JewelsBoxEmployee Customer Care")
                putExtra(Intent.EXTRA_TEXT, "Hi, I am from JewelsBoxEmployee App")
            })
        } catch (e: Exception) {
            Toast.makeText(this, "Email app not found !", Toast.LENGTH_SHORT).show()
        }
    }

    private fun whatsapp(number: String) {
        val url = "https://api.whatsapp.com/send?phone=$number&text=Hi, I am from JewelsBoxEmployee app"
        try {
            val sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.action = Intent.ACTION_VIEW
            sendIntent.setPackage("com.whatsapp")
            sendIntent.data = Uri.parse(url)
            startActivity(sendIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "Whatsapp app not found !", Toast.LENGTH_SHORT).show()
        }

    }

}

