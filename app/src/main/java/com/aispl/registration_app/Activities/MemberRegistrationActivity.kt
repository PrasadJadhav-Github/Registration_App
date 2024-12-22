package com.aispl.registration_app.Activities

import RegistrationMemberDatabaseHelper
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.aispl.registration_app.R
import com.aispl.registration_app.databinding.ActivityMemberRegistrationBinding
import java.util.*

class MemberRegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemberRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMemberRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClickListener()
        setUpRadioGroupListener()
        setUpDatePickers()
    }

    private fun onClickListener() {
        binding.btnSubmit.setOnClickListener {
            handleButtonClick()
        }
    }

    private fun setUpRadioGroupListener() {
        binding.roleGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioSingle) {
                binding.etMarriageDate.visibility = View.GONE
                binding.etMarriageDate.isEnabled = false
                binding.etMarriageDate.clearFocus()
                binding.etMarriageDate.text.clear()
            } else {
                binding.etMarriageDate.visibility = View.VISIBLE
                binding.etMarriageDate.isEnabled = true
            }
        }
    }

    private fun setUpDatePickers() {
        binding.etDob.setOnClickListener { openDatePickerDialog(binding.etDob) }
        binding.etJoiningDate.setOnClickListener { openDatePickerDialog(binding.etJoiningDate) }
        binding.etMarriageDate.setOnClickListener { openDatePickerDialog(binding.etMarriageDate) }

        binding.etDob.inputType = 0
        binding.etJoiningDate.inputType = 0
        binding.etMarriageDate.inputType = 0
    }

    private fun openDatePickerDialog(editText: EditText) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            editText.setText(formattedDate)
        }, year, month, dayOfMonth)

        datePickerDialog.show()
    }

    private fun handleButtonClick() {
        val mobileNumber = binding.etMobileNumber.text.toString()
        val name = binding.etName.text.toString()
        val role = when (binding.roleGroup.checkedRadioButtonId) {
            R.id.role_secretary -> "Secretary"
            R.id.role_member -> "Member"
            else -> ""
        }
        val subscriptionFee = binding.etSubscriptionFee.text.toString().toDoubleOrNull() ?: 0.0
        val depositAmount = binding.etDepositAmount.text.toString().toDoubleOrNull() ?: 0.0
        val loanAmount = binding.etLoanAmount.text.toString().toDoubleOrNull() ?: 0.0
        val gender = binding.etGender.text.toString()
        val dob = binding.etDob.text.toString()
        val joiningDate = binding.etJoiningDate.text.toString()
        val maritalStatus = binding.etMaritalStatus.text.toString()
        val marriageDate = if (binding.etMarriageDate.visibility == View.VISIBLE) {
            binding.etMarriageDate.text.toString().takeIf { it.isNotEmpty() }
        } else {
            null
        }
        val caste = binding.etCaste.text.toString()
        val religion = binding.etReligion.text.toString()
        val category = binding.etCategory.text.toString()
        val aadharNumber = binding.etAadharNumber.text.toString()

        if (name.isEmpty() || mobileNumber.isEmpty() || role.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
            return
        }

        val dbHelper = RegistrationMemberDatabaseHelper(this)
        val result = dbHelper.insertRegistrationMemberData(
            mobileNumber, name, role, subscriptionFee, depositAmount, loanAmount,
            gender, dob, joiningDate, maritalStatus, marriageDate, caste, religion, category, aadharNumber
        )

        if (result != -1L) {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error occurred during registration. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }
}
