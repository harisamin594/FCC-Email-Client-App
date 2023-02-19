package com.example.fccemailclient

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class Email : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.send_email_frag, container, false)
        val users = view.findViewById<EditText>(R.id.to_whom)
        val subjects = view.findViewById<EditText>(R.id.subject)
        val body = view.findViewById<EditText>(R.id.body_field)
        var user = MainActivity()
        var draft_bool = MainActivity()
        var PREF = MainActivity()
        var V_PREF = ViewMail()
        var is_reply = ViewMail()
        val prefs = requireContext().getSharedPreferences(V_PREF.toString(), Context.MODE_PRIVATE)
        val prefs2 = requireContext().getSharedPreferences(PREF.toString(), Context.MODE_PRIVATE)
        val You = prefs.getBoolean(java.lang.String.valueOf(is_reply), false)
        val me = prefs2.getBoolean(java.lang.String.valueOf(draft_bool), false)
        var user_KEY = ViewMail()
        var Sub_KEY = ViewMail()
        if (You == true) {
            val up = prefs2.getString(user_KEY.toString(), "")
            val sub = prefs.getString(Sub_KEY.toString(), "")
            users.setText(up)
            subjects.setText(sub)
        }
        if (me == true) {
            parentFragmentManager.setFragmentResultListener(
                "tek", this
            ) { requestKey, result ->
                var mydata: ArrayList<String?>? = ArrayList()
                mydata = result.getStringArrayList("data")
                var i = 0
                val sharedPreferences = requireActivity().getSharedPreferences(
                    V_PREF.toString(),
                    Context.MODE_PRIVATE
                )
                val editor =
                    requireContext().getSharedPreferences(V_PREF.toString(), Context.MODE_PRIVATE)
                        .edit()
                users.setText(mydata!![i])
                val up = mydata[i]
                i++
                val dp = mydata[i]
                subjects.setText(mydata[i])
                val sub = mydata[i]
                i++
                body.setText(mydata[i])
                editor.putBoolean(java.lang.String.valueOf(draft_bool), false)
                editor.commit()
            }
        }
        val send = view.findViewById<Button>(R.id.Send_butn)
        send.setOnClickListener {
            val usr1 = prefs2.getString(user.toString(), "")
            val usr = users.text.toString()
            val subj = subjects.text.toString()
            val bdy = body.text.toString()
            val path = requireActivity().filesDir
            val mb = usr + "ib.txt"
            val file = File(path, mb)
            try {
                FileWriter(file, true).use { fileWriter ->
                    val fw = BufferedWriter(fileWriter)
                    fw.write(usr1)
                    fw.write("\n")
                    fw.write(subj)
                    fw.write("\n")
                    fw.write(bdy)
                    fw.write("\n")
                    fw.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val mb1 = usr1 + "send.txt"
            val file2 = File(path, mb1)
            try {
                FileWriter(file2, true).use { fileWriter ->
                    val fw = BufferedWriter(fileWriter)
                    fw.write(usr)
                    fw.write("\n")
                    fw.write(subj)
                    fw.write("\n")
                    fw.write(bdy)
                    fw.write("\n")
                    fw.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Toast.makeText(context, resources.getString(R.string.Email_sent), Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_email_to_folderFrag)

        }
        val draft = view.findViewById<Button>(R.id.draft_butn)
        draft.setOnClickListener {
            val usr1 = prefs2.getString(user.toString(), "")
            val usr = users.text.toString()
            val subj = subjects.text.toString()
            val bdy = body.text.toString()
            val path = requireActivity().filesDir
            val mb = usr1 + "draft.txt"
            val file = File(path, mb)
            try {
                FileWriter(file, true).use { fileWriter ->
                    val fw = BufferedWriter(fileWriter)
                    fw.write(usr)
                    fw.write("\n")
                    fw.write(subj)
                    fw.write("\n")
                    fw.write(bdy)
                    fw.write("\n")
                    fw.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Toast.makeText(context, resources.getString(R.string.Saveddraft), Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_email_to_folderFrag)
        }
        return view
    }
}