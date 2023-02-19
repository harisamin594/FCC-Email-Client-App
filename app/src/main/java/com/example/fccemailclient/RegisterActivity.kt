package com.example.fccemailclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.fccemailclient.R.drawable.*
import com.example.fccemailclient.databinding.ActivityRegisterBinding
import java.io.*
import java.util.*


class RegisterActivity : AppCompatActivity() {

    val PREF = "pk.edu.fccollege.MyPrefs"
    val user = ""
    var user_count = 0
    var selected_pic = ""

    var register: Button? = null
    var username: EditText? = null
    var password: EditText? = null
    var confirmpassword: EditText? = null
    var reg_image: ImageView? = null

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        register = binding.registerScreen2
        username = binding.usernameScreen2
        password = binding.passScreen2
        confirmpassword = binding.confirmpassScreen2

        val spinner = binding.spinnerScreen2
        reg_image = binding.imageView

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                reg_image!!.setImageResource(pic_1)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, i: Int, id: Long) {
                if (i == 0) {
                    with(reg_image) { this!!.setImageResource(pic_1) }
                    selected_pic = "pic_1.png"
                }
                if (i == 1) {
                    with(reg_image) { this!!.setImageResource(pic_2) }
                    selected_pic = "pic_2.png"
                }
                if (i == 2) {
                    with(reg_image) { this!!.setImageResource(pic_3) }
                    selected_pic = "pic_3.png"
                }
                if (i == 3) {
                    with(reg_image) { this!!.setImageResource(pic_4) }
                    selected_pic = "pic_4.png"
                }
            }

        }

        binding.registerScreen2.setOnClickListener {
            val sharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE)
            val Username = username!!.getText().toString()
            val Password = password!!.getText().toString()
            val ConfirmPassword = confirmpassword!!.getText().toString()

            if ((Username == "") || (Password == "")) {
                Toast.makeText(this, "Empty credentials!", Toast.LENGTH_SHORT).show()
            }

            else if ((Password != ConfirmPassword)) {
                Toast.makeText(this, "Passwords don't match. Try Again!", Toast.LENGTH_SHORT).show()
            }

            else {
                val path = filesDir
                val file = File(path, "user_db.txt")
                if (!file.exists()) {
                    try {
                        file.createNewFile()
                    }
                    catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                else {
                    try {
                        user_count++
                        val fileWriter = FileWriter(file, true)
                        val fw = BufferedWriter(fileWriter)
                        fw.write(user_count.toString())
                        fw.write("\n")
                        fw.write(Username)
                        fw.write("\n")
                        fw.write(Password)
                        fw.write("\n")
                        fw.write(selected_pic)
                        fw.write("\n")
                        fw.close()
                    }
                    catch (e: IOException) {
                        e.printStackTrace()
                    }
                    val editor = sharedPreferences.edit()
                    editor.putString(user, Username)
                    editor.commit()
                    Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }
    }
}
