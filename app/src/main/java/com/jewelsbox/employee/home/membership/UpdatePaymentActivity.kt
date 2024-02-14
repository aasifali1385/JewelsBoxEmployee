package com.jewelsbox.employee.home.membership

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.jewelsbox.employee.databinding.ActivityUpdatePaymentBinding
import com.jewelsbox.employee.retrofit.SP
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.Calendar

class UpdatePaymentActivity : AppCompatActivity() {

    private lateinit var bind: ActivityUpdatePaymentBinding

    private var imageUrl = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityUpdatePaymentBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.back.setOnClickListener { finish() }

        val viewModel = ViewModelProvider(this)[MembershipViewModel::class.java]
        val sp = SP(this)


        val planId = intent.getStringExtra("id").toString()
        val amount = intent.getStringExtra("amount").toString()

        bind.editAmount.text = "\u20B9$amount"

        viewModel.paymentMode(sp.getId(), {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }, {
            bind.submit.isEnabled = true
            bind.spinnerPayment.adapter =
                ArrayAdapter(
                    this, android.R.layout.simple_spinner_dropdown_item,
                    listOf("Choose Payment Mode") + it
                )
        })

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val date = c.get(Calendar.DATE)

        bind.editDate.setOnClickListener {
            val datePicker = DatePickerDialog(this, { _, year, month, date ->
                bind.editDate.text = "$date/${month + 1}/$year"
            }, year, month, date)
            datePicker.datePicker.maxDate = System.currentTimeMillis()
            datePicker.show()
        }




        bind.uploadImage.setOnClickListener {
            ImagePicker.with(this)
                .crop(3f, 4f)
                .compress(1024)
                .maxResultSize(512, 768)
                .start()
//                .createIntent { intent -> startForProfileImageResult.launch(intent) }
        }


        bind.submit.setOnClickListener {
            when {
                bind.spinnerPayment.selectedItemPosition == 0 -> toast("Choose any payment mode")

                bind.editDate.text.isEmpty() -> bind.editDate.error =
                    "Please choose transaction data"

                bind.editTxnId.text.isEmpty() -> bind.editTxnId.error =
                    "Please enter transaction Id"

                imageUrl == "" -> toast("Please capture/select payment page")

                else -> {
                    bind.submit.isEnabled = false
                    bind.submit.text = "Submitting..."

                    viewModel.getPayment(
                        sp.getId(),
                        planId,
                        amount,
                        bind.spinnerPayment.selectedItem.toString(),
                        bind.editDate.text.toString(),
                        bind.editTxnId.text.toString(),
                        File(imageUrl), {
                            toast(it)
                        }, {
                            toast(it)
                            finish()
                        }
                    )

                }
            }
        }


    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri = data?.data!!
            imageUrl = uri.path.toString()
            bind.imageView.setImageURI(uri)

            Log.e("TAG", "onActivityResult: $uri")
            Log.e("TAG", "onActivityResult: $imageUrl")
            Log.e("TAG", "onActivityResult: ${File(imageUrl).name}")
            Log.e("TAG", "onActivityResult: ${File(imageUrl).extension}")


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }


//    private val startForProfileImageResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//            val resultCode = result.resultCode
//            val data = result.data
//
//            if (resultCode == Activity.RESULT_OK) {
//                val fileUri = data?.data!!
//                imageUrl = fileUri.path.toString()
//                bind.imageView.setImageURI(fileUri)
//
//            } else if (resultCode == ImagePicker.RESULT_ERROR) {
//                toast(ImagePicker.getError(data))
//            } else {
//                toast("Task Cancelled")
//            }
//        }


    private fun toast(it: String) = Toast.makeText(this, it, Toast.LENGTH_SHORT).show()


}