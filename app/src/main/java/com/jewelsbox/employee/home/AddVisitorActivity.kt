package com.jewelsbox.employee.home

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.forEach
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.jewelsbox.employee.R
import com.jewelsbox.employee.databinding.ActivityAddVisitorBinding
import com.jewelsbox.employee.databinding.ItemInterestRadioBinding
import com.jewelsbox.employee.retrofit.SP
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.Calendar

class AddVisitorActivity : AppCompatActivity() {


    private lateinit var bind : ActivityAddVisitorBinding
    private var profileUriPath: String = ""

    

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityAddVisitorBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.editMobile.setText(intent.getStringExtra("mobile").toString())

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val sp = SP(this)
        
        bind.backButton.setOnClickListener { finish() }

        bind.capturePic.setOnClickListener {
            ImagePicker.with(this)
                .crop(1f, 1f)
                .compress(1024)
                .maxResultSize(512, 512)
                .createIntent { intent -> startForProfileImageResult.launch(intent) }
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val date = c.get(Calendar.DATE)

        bind.editDOB.setOnClickListener {
            val datePicker = DatePickerDialog(this, { _, year, month, date ->
                bind.editDOB.text = "$date/${month + 1}/$year"
            }, year, month, date)
            datePicker.datePicker.maxDate = System.currentTimeMillis()
            datePicker.show()
        }

        bind.editAnniversary.setOnClickListener {
            val datePicker = DatePickerDialog(this, { _, year, month, date ->
                bind.editAnniversary.text = "$date/${month + 1}/$year"
            }, year, month, date)
            datePicker.datePicker.maxDate = System.currentTimeMillis()
            datePicker.show()
        }

        val stateIds = mutableListOf<String>()
        val stateNames = mutableListOf<String>()

        val cityIds = mutableListOf<String>()
        val cityNames = mutableListOf<String>()

        viewModel.stateList(sp.getId(), {
            toast(it)
        }, {
            stateIds.add("0"); stateNames.add("Select State")
            it.forEach {
                stateIds.add(it.state_id); stateNames.add(it.state_name)
            }.also {
                bind.spinnerState.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item, stateNames
                )
            }
        })

        bind.spinnerState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                val text = p1 as TextView?
                text?.setTextColor(Color.parseColor(if (p2 == 0) "#4D5358" else "#000000"))

                bind.submit.isEnabled = false
                //////////////////////////////
                viewModel.cityList(sp.getId(), stateIds[p2], {
//                    toast(it)
                    bind.submit.isEnabled = true
                }, {
                    cityIds.clear(); cityNames.clear()

                    it.forEach {
                        cityIds.add(it.city_id); cityNames.add(it.city_name)
                    }.also {
                        bind.spinnerCity.adapter = ArrayAdapter(
                            this@AddVisitorActivity,
                            android.R.layout.simple_spinner_dropdown_item, cityNames
                        )
                        bind.submit.isEnabled = true
                    }


                })
                ///////////////////////////////
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }


        var interestId = ""
        val interestIds = mutableListOf<String>()
        val interestNames = mutableListOf<String>()

        viewModel.interestList(sp.getId(), {
            toast(it)
        }, {
            bind.interestGroup.removeAllViews()
            it.forEach {

                val interestRadio =
                    ItemInterestRadioBinding.inflate(layoutInflater, bind.root, false)
                interestRadio.root.text = it.interested_name
//                interestRadio.root.tag = it.interested_id

                interestRadio.root.setOnClickListener { _ ->

                    bind.submit.isEnabled = false

                    bind.interestGroup.forEach {
                        it.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    }

                    interestRadio.root.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("#D4BBFF"))

                    interestId = it.interested_id

                    viewModel.subInterestList(
                        sp.getId(),
                        interestId,
                        {
                            toast(it)
                            bind.submit.isEnabled = true
                        }, {
                            interestIds.clear(); interestNames.clear()
                            it.forEach {
                                interestIds.add(it.sub_interested_id); interestNames.add(it.sub_interested_name)
                            }.also {
                                bind.spinnerInterest.adapter = ArrayAdapter(
                                    this,
                                    android.R.layout.simple_spinner_dropdown_item, interestNames
                                )
                                bind.submit.isEnabled = true
                            }

                        })
                }


                bind.interestGroup.addView(interestRadio.root)


            }
        })




        bind.submit.setOnClickListener { buttonView ->
            when {

                profileUriPath == "" -> {
                    toast("Please capture/select picture")
                    bind.scroll.smoothScrollTo(0, 0)
                }

                bind.editName.text.isEmpty() -> {
                    bind.editName.error = "Please enter name"
                    bind.editName.requestFocus()
                }

                bind.editMobile.text.isEmpty() -> {
                    bind.editMobile.error = "Please enter mobile no."
                    bind.editMobile.requestFocus()
                }

                bind.editEmail.text.isEmpty() -> {
                    bind.editEmail.error = "Please enter email Id"
                    bind.editEmail.requestFocus()
                }

                bind.editDOB.text.isEmpty() -> {
                    bind.editDOB.error = "Please select DOB"
                    bind.scroll.smoothScrollTo(0, bind.dobHead.top)
                }

                bind.editAnniversary.text.isEmpty() -> {
                    bind.editAnniversary.error = "Please select anniversary date"
                    bind.scroll.smoothScrollTo(0, bind.anniversaryHead.top)
                }


                bind.spinnerState.selectedItemPosition == 0 -> {
                    toast("Please select state")
                    bind.scroll.smoothScrollTo(0, bind.stateHead.top)
                }

                bind.editPincode.text.isEmpty() -> {
                    bind.editPincode.error = "Please enter pincode"
                    bind.editPincode.requestFocus()
                }

                bind.editAddress.text.isEmpty() -> {
                    bind.editAddress.error = "Please enter address"
                    bind.editAddress.requestFocus()
                }

                interestId == "" -> {
                    toast("Please select interest for/in")
                    bind.scroll.smoothScrollTo(0, bind.interestHead.top)
                }

                else -> {
                    bind.submit.isEnabled = false
                    bind.submit.text = "Submitting..."


                    viewModel.addLead(
                        File(profileUriPath),
                        mapOf(
//                            "image" to RequestBody.create("multipart/form-data".toMediaTypeOrNull(), File(profileUriPath)),

                            "user_id" to sp.getId().toRequestBody("text/plain".toMediaTypeOrNull()),

                            "full_name" to bind.editName.text.toString()
                                .toRequestBody("text/plain".toMediaTypeOrNull()),
                            "phone_no" to bind.editMobile.text.toString()
                                .toRequestBody("text/plain".toMediaTypeOrNull()),
                            "email_id" to bind.editEmail.text.toString()
                                .toRequestBody("text/plain".toMediaTypeOrNull()),

                            "dob" to bind.editDOB.text.toString()
                                .toRequestBody("text/plain".toMediaTypeOrNull()),
                            "anniversary_date" to bind.editAnniversary.text.toString()
                                .toRequestBody("text/plain".toMediaTypeOrNull()),

                            "gender" to findViewById<RadioButton>(bind.genderGroup.checkedRadioButtonId).text.toString()
                                .toRequestBody("text/plain".toMediaTypeOrNull()),

                            "state_id" to stateIds[bind.spinnerState.selectedItemPosition].toRequestBody(
                                "text/plain".toMediaTypeOrNull()
                            ),
                            "city_id" to cityIds[bind.spinnerCity.selectedItemPosition].toRequestBody(
                                "text/plain".toMediaTypeOrNull()
                            ),

                            "pin_code" to bind.editPincode.text.toString()
                                .toRequestBody("text/plain".toMediaTypeOrNull()),
                            "address" to bind.editAddress.text.toString()
                                .toRequestBody("text/plain".toMediaTypeOrNull()),


                            "interest" to interestId.toRequestBody("text/plain".toMediaTypeOrNull()),
                            "sub_interested_id" to interestIds[bind.spinnerInterest.selectedItemPosition].toRequestBody(
                                "text/plain".toMediaTypeOrNull()
                            ),


                            "company_name" to bind.editCompany.text.toString()
                                .toRequestBody("text/plain".toMediaTypeOrNull()),

                            "page_type" to "visitor".toRequestBody("text/plain".toMediaTypeOrNull())


                        ), {
                            Snackbar.make(buttonView, it, Snackbar.LENGTH_SHORT).show()
                            bind.submit.isEnabled = true
                            bind.submit.text = "Submit"
                        }, {
                            Snackbar.make(buttonView, it, Snackbar.LENGTH_SHORT).show()

                            bind.submit.isEnabled = true
                            bind.submit.text = "Submit"

//                            val intent = Intent(context, MainActivity::class.java)
//                            startActivity(intent)
//                            (activity as MainActivity)

//                            (activity as MainActivity).bottomNavigation.selectedItemId = R.id.nav_home

                        })
                }
            }
        }


    }


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data!!
                profileUriPath = fileUri.path.toString()
                bind.profileImage.setImageURI(fileUri)

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                toast(ImagePicker.getError(data))
            } else {
                toast("Task Cancelled")
            }
        }

    private fun toast(it: String) = Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

}