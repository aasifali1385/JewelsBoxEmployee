package com.jewelsbox.employee.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.jewelsbox.employee.databinding.ActivityCmsBinding
import com.jewelsbox.employee.retrofit.SP


class CMSActivity : AppCompatActivity() {

    private lateinit var bind: ActivityCmsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityCmsBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val viewModel = ViewModelProvider(this)[MenuViewModel::class.java]

        val type = intent.getStringExtra("type").toString()
        val sp = SP(this)


        viewModel.getCMS(sp.getId(), type, { title, desc ->
            bind.title.text = title
            bind.desc.text = Html.fromHtml(desc, Html.FROM_HTML_MODE_LEGACY)
        }, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })


        bind.back.setOnClickListener { finish() }
    }
}