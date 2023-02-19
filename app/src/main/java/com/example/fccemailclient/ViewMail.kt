package com.example.fccemailclient

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class ViewMail : Fragment() {
    private val PREFS = "pk.edu.fccollege.MyPrefs"
    private val Sub_KEY = "IMG"
    private val user_KEY = "user"
    private val is_reply = false


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.view_mail_frag, container, false)
        val user = view.findViewById<TextView>(R.id.name)
        val img = view.findViewById<ImageView>(R.id.user_img)
        val subject = view.findViewById<TextView>(R.id.Subject)
        val msg = view.findViewById<TextView>(R.id.msg)
        parentFragmentManager.setFragmentResultListener(
            "tek", this
        ) { requestKey, result ->
            var mydata: ArrayList<String?>? = ArrayList()
            mydata = result.getStringArrayList("data")
            var i = 0
            val sharedPreferences =
                requireActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            val editor =
                requireContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                    .edit()
            user.text = mydata!![i]
            val up = mydata[i]
            i++
            val dp = mydata[i]
            Log.d("DP", dp!!)
            val dp1 = GetImgResId(dp)
            img.setImageResource(dp1)
            i++
            subject.text = mydata[i]
            val sub = mydata[i]
            i++
            msg.text = mydata[i]
            editor.putString(user_KEY, up)
            editor.putString(Sub_KEY, sub)
            editor.apply()
        }

        val reply = view.findViewById<Button>(R.id.butn_reply)
        reply.setOnClickListener {
            val sharedPreferences = requireActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            val editor = requireContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
            editor.putBoolean(is_reply.toString(), true)
            editor.apply()
            val fragment = Email()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_frag, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }
        return view

    }

    private fun GetImgResId(name: String?): Int {
        return when (name) {
            "img.png" -> R.drawable.pic_1
            "img_1.png" -> R.drawable.pic_2
            "img_2.png" -> R.drawable.pic_3
            "img_3.png" -> R.drawable.pic_4
            else -> R.drawable.folder_icon
        }


    }
}