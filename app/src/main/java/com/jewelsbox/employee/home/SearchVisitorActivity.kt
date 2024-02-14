package com.jewelsbox.employee.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.jewelsbox.employee.databinding.ActivitySearchVisitorBinding
import com.jewelsbox.employee.retrofit.SP

class SearchVisitorActivity : AppCompatActivity() {

    private lateinit var bind: ActivitySearchVisitorBinding

    private var mobile = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySearchVisitorBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("desc")

        if (title != null) {
            bind.title.text = title
            bind.desc.text = desc
            bind.didNotFind.isVisible = true
            bind.addVisitor.isVisible = true
        }

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val sp = SP(this)

        bind.search.setOnClickListener {
            if (bind.editMobile.text.isEmpty())
                bind.editMobile.error = "Please enter visitor mobile number"
            else {

                bind.search.isEnabled = false
                bind.progressBar.isVisible = true
                viewModel.searchLead(sp.getId(), bind.editMobile.text.toString(), {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    bind.search.isEnabled = true
                    bind.progressBar.isVisible = false
                    mobile = bind.editMobile.text.toString()

                }, {
                    mobile = bind.editMobile.text.toString()
                    bind.editMobile.text.clear()
                    bind.detailsGroup.isVisible = true
                    bind.markVisiting.isVisible = false
                    bind.details.text =
                        "${it.lead_code}\n${it.full_name}\n${it.phone_no}\n${it.dob}\n${it.anniversary_date}\n${it.interested_name}\n${it.city_name}"

                    bind.markVisiting.setOnClickListener { view ->
                        bind.markVisiting.isEnabled = false
                        bind.markVisiting.text = "Marking..."
                        viewModel.markVisiting(sp.getId(), it.lead_id, {
                            Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
                            bind.markVisiting.isEnabled = true
                            bind.markVisiting.text = "Mark Visiting"
                        }, {
                            Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
                            bind.markVisiting.isEnabled = true
                            bind.markVisiting.text = "Mark Visiting"
                            bind.detailsGroup.isVisible = false
                        })
                    }

                    bind.search.isEnabled = true
                    bind.progressBar.isVisible = false
                })
            }
        }

        bind.addVisitor.setOnClickListener {
            val intent = Intent(this, AddVisitorActivity::class.java)
            intent.putExtra("mobile", mobile)
            startActivity(intent)
        }

        bind.back.setOnClickListener { finish() }
    }


}