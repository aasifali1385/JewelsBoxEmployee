package com.jewelsbox.employee.home.membership

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jewelsbox.employee.databinding.ActivityMembershipBinding
import com.jewelsbox.employee.retrofit.SP

class MembershipActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMembershipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMembershipBinding.inflate(layoutInflater)
        setContentView(bind.root)


        val membershipAdapter = MembershipAdapter { id, amount ->
            startActivity(Intent(this, UpdatePaymentActivity::class.java).apply {
                putExtra("id", id)
                    .putExtra("amount", amount)
            })
        }

        bind.recycler.layoutManager = LinearLayoutManager(this)
        bind.recycler.adapter = membershipAdapter


        val viewModel = ViewModelProvider(this)[MembershipViewModel::class.java]
        val sp = SP(this)


        viewModel.membership.observe(this) {
            membershipAdapter.loadMemberships(it)
            bind.progressBar.isVisible = false
            bind.recycler.isVisible = true
        }


        bind.search.setOnClickListener {
            if (bind.editId.text.isEmpty())
                bind.editId.error = "Please enter member Id / mobile no."
            else {
                bind.progressBar.isVisible = true
                bind.recycler.isVisible = false
                viewModel.searchMemberPlan(sp.getId(), bind.editId.text.toString()) {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    bind.progressBar.isVisible = false
                }
            }
        }






        bind.back.setOnClickListener { finish() }
    }


}

