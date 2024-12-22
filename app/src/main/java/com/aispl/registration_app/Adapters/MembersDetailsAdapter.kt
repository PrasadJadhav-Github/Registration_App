package com.aispl.registration_app.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aispl.registration_app.Model_Class.RegistrationMemberDatabase
import com.aispl.registration_app.R

class MembersDetailsAdapter(private val memberList: List<Map<String, String>>) :
    RecyclerView.Adapter<MembersDetailsAdapter.MemberViewHolder>() {

    inner class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_member_name)
        val tvMobile: TextView = itemView.findViewById(R.id.tv_mobile_no)
        val tvRole: TextView = itemView.findViewById(R.id.tv_member_role)
        val tvSubscriptionFee: TextView = itemView.findViewById(R.id.tv_subscription_amt)
        val tvDepositAmount: TextView = itemView.findViewById(R.id.editText_loan_amount)
        val tvLoanAmount: TextView = itemView.findViewById(R.id.editText_deposit_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_members_details, parent, false)
        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = memberList[position]


        holder.tvName.text = "Name: ${member[RegistrationMemberDatabase.COLUMN_NAME]}"
        holder.tvMobile.text = "Mobile: ${member[RegistrationMemberDatabase.COLUMN_MOBILE_NUMBER]}"


        val role = member[RegistrationMemberDatabase.COLUMN_ROLE]
        holder.tvRole.text = "Role: $role"

        holder.tvSubscriptionFee.text = "Subscription Fee: ${member[RegistrationMemberDatabase.COLUMN_SUBSCRIPTION_FEE]}"
        holder.tvDepositAmount.text = " ${member[RegistrationMemberDatabase.COLUMN_DEPOSIT_AMOUNT]}"
        holder.tvLoanAmount.text = " ${member[RegistrationMemberDatabase.COLUMN_LOAN_AMOUNT]}"
    }

    override fun getItemCount(): Int {
        return memberList.size
    }
}
