package com.jewelsbox.employee.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jewelsbox.employee.MainActivity
import com.jewelsbox.employee.R
import com.jewelsbox.employee.databinding.FragmentHomeBinding
import com.jewelsbox.employee.home.membership.MembershipActivity


class HomeFragment : Fragment() {

    private lateinit var bind: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentHomeBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.option1.title.text = getString(R.string.title1)
        bind.option2.title.text = getString(R.string.title2)
        bind.option3.title.text = getString(R.string.title3)
        bind.option4.title.text = getString(R.string.title4)

        bind.option1.desc.text = getString(R.string.desc1)
        bind.option2.desc.text = getString(R.string.desc2)
        bind.option3.desc.text = getString(R.string.desc3)
        bind.option4.desc.text = getString(R.string.desc4)

        bind.option1.viewMore.text = getString(R.string.viewMore1)
        bind.option2.viewMore.text = getString(R.string.viewMore2)
        bind.option3.viewMore.text = getString(R.string.viewMore3)
        bind.option4.viewMore.text = getString(R.string.viewMore4)

        bind.option1.icon.setImageResource(R.drawable.ic_option1)
        bind.option2.icon.setImageResource(R.drawable.ic_option2)
        bind.option3.icon.setImageResource(R.drawable.ic_option3)
        bind.option4.icon.setImageResource(R.drawable.ic_option4)

        bind.option1.viewMore.setOnClickListener {
            val intent = Intent(context, SearchVisitorActivity::class.java)
            intent.putExtra("title", "Search and add visitor")
            intent.putExtra("desc", "Search visitor to mark as visiting or add visitor")
            startActivity(intent)
        }

        bind.option2.viewMore.setOnClickListener {
            startActivity(Intent(context, SearchVisitorActivity::class.java))
        }

        bind.option3.viewMore.setOnClickListener { startActivity(Intent(context, MembershipActivity::class.java))

        }
        bind.option4.viewMore.setOnClickListener {
            (activity as MainActivity).bottomNavigation.selectedItemId = R.id.nav_addlead
        }


    }

}