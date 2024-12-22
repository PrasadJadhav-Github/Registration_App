package com.aispl.registration_app.Activities

import RegistrationMemberDatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aispl.registration_app.Adapters.MembersDetailsAdapter
import com.aispl.registration_app.databinding.ActivityRegistrationDetailsBinding

class RegistrationDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Registration Details"

        setupRecyclerView()
        buttonClick()
    }

    private fun buttonClick() {
        binding.btnAddNew.setOnClickListener {
            val intent = Intent(this, MemberRegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.rvMembers.layoutManager = LinearLayoutManager(this)

        // Fetch data from the database
        val dbHelper = RegistrationMemberDatabaseHelper(this)
        val memberList = dbHelper.getSpecificRegistrationMembers()

        // Set the total count of members
        binding.tvMembersCount.text = "Members Count: ${memberList.size}"

        if (memberList.isNotEmpty()) {
            val adapter = MembersDetailsAdapter(memberList)
            binding.rvMembers.adapter = adapter
        } else {
            binding.tvNoData.text = "No members found."
            binding.tvNoData.visibility = View.VISIBLE
        }
    }
}
