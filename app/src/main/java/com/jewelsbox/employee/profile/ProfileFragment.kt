package com.jewelsbox.employee.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.load
import com.jewelsbox.employee.MainActivity
import com.jewelsbox.employee.R

class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sp = (activity as MainActivity).sp

        sp.getUser {
            if(it["profile_pic"] != "") view.findViewById<ImageView>(R.id.profileImage).load(it["profile_pic"])
            view.findViewById<TextView>(R.id.details).text = "${it["full_name"]}\n${it["user_code"]}\n${it["phone_no"]}\n${it["email_id"]}\n${it["city_name"]}"
        }



    }


}