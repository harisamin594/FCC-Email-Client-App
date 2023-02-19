package com.example.fccemailclient

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fccemailclient.databinding.ActivityMainBinding
import android.content.Intent
import java.io.FileNotFoundException
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val PREF = "pk.edu.fccollege.MyPrefs"
    private val user = ""


    private var username: EditText? = null
    private var password: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerbtnScreen1.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        username = binding.usernameScreen1
        password = binding.passScreen1

        binding.loginbtnScreen1.setOnClickListener {
            val Username = username!!.text.toString()
            val Password = password!!.text.toString()

            if ((Username == "") || (Password == "")) {
                Toast.makeText(this, "Incomplete Credentials!", Toast.LENGTH_SHORT).show()
            }

            else {

                var UsernameFlag = false
                var PasswordFlag = false

                try {
                    val scanner = Scanner(openFileInput("user_db.txt"))
                    while (scanner.hasNextLine()) {
                        val line = scanner.nextLine()
                        if (line == Username) {
                            UsernameFlag = true
                        }
                        if (line == Password) {
                            PasswordFlag = true
                        }
                        if (UsernameFlag == true && PasswordFlag == true) {
                            val sharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString(user, Username)
                            editor.commit()
                            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, SecondMainActivity::class.java))
                        }
                    }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }
}

