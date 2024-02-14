package com.jewelsbox.employee.home.membership

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jewelsbox.employee.databinding.ItemMembershipBinding

class MembershipAdapter(private val pay: (String,String) -> Unit) :
    RecyclerView.Adapter<MembershipAdapter.MembershipHolder>() {

    private val list = mutableListOf<Membership>()

    fun loadMemberships(it: List<Membership>) {
        list.clear()
        list.addAll(it)
        notifyDataSetChanged()
    }


    inner class MembershipHolder(private val bind: ItemMembershipBinding) : ViewHolder(bind.root) {
        @SuppressLint("SetTextI18n")
        fun setView(membership: Membership) {
            bind.details.text =
                "${membership.user_plan_code}\n${membership.full_name}\n${membership.investment_amount}\n${membership.next_payment_date}\n${membership.plan_name}"

            bind.pay.setOnClickListener { pay(membership.user_plan_id, membership.investment_amount) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembershipHolder {
        return MembershipHolder(
            ItemMembershipBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MembershipHolder, position: Int) {
        holder.setView(list[position])
    }

    override fun getItemCount() = list.size


}