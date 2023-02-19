package com.example.fccemailclient

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fccemailclient.databinding.InboxFragBinding
import java.io.*
import java.util.*

class InboxFrag : Fragment()  {
    private var _binding: InboxFragBinding? = null
    private val binding get() = _binding!!
    var mail_list: ArrayList<Mails>? = null
    var user = MainActivity()
    var PREF = MainActivity()
    //var mail_list: ArrayList<Mail>? = null
    //ArrayList<FolderClass> folder_list;


    //ArrayList<FolderClass> folder_list;
    fun InboxFragment() {
        // Required empty public constructor

    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState)
        _binding = InboxFragBinding.inflate(layoutInflater)
        return binding.root
        mail_list = ArrayList<Mails>()

        try {
            ReadFromFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.returnToInbox.setOnClickListener {
            findNavController().navigate(R.id.action_inboxFrag_to_folderFrag)
        }
        val Comp = requireView().findViewById<Button>(R.id.compose)
        Comp.setOnClickListener {
            val fragment = Email()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_frag, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()


        }
        val ls = view.findViewById<ListView>(R.id.received_emails)
        val adapter = activity?.let { it1 ->
            mail_list?.let { it2 ->
                MailAdapter(
                    it1, R.layout.mail_layout,
                    it2
                )
            }

        }

        ls.adapter = adapter

        ls.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
                val prefs2 =
                    requireContext().getSharedPreferences(PREF.toString(), Context.MODE_PRIVATE)
                val user1 = prefs2.getString(user.toString(), "")
                val popupMenu = PopupMenu(context, view)
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    var m: Mails = mail_list!![i]
                    var user: String? = m.getuser()
                    var sub: String = m.getsubject().toString()
                    var body: String = m.getbody().toString()
                    when (menuItem.itemId) {
                        R.id.mark_as_read -> true
                        R.id.delete -> {
                            m = mail_list!![i]
                            adapter!!.remove(m)
                            adapter.notifyDataSetChanged()
                            user = m.getuser()
                            sub = m.getsubject().toString()
                            body = m.getbody().toString()
                            val mb = user1 + "trash.txt"
                            val path = requireActivity().filesDir
                            val file = File(path, mb)
                            try {
                                FileWriter(file, true).use { fileWriter ->
                                    val fw = BufferedWriter(fileWriter)
                                    fw.write(user)
                                    fw.write("\n")
                                    fw.write(sub)
                                    fw.write("\n")
                                    fw.write(body)
                                    fw.write("\n")
                                    fw.close()
                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                            true
                        }
                        R.id.send_in_spam -> {
                            m = mail_list!![i]
                            user = m.getuser()
                            sub = m.getsubject().toString()
                            body = m.getbody().toString()
                            val mc = user1 + "spam.txt"
                            val path2 = requireActivity().filesDir
                            val file2 = File(path2, mc)
                            try {
                                FileWriter(file2, true).use { fileWriter ->
                                    val fw = BufferedWriter(fileWriter)
                                    fw.write(user1)
                                    fw.write("\n")
                                    fw.write(sub)
                                    fw.write("\n")
                                    fw.write(body)
                                    fw.write("\n")
                                    fw.close()
                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                            true
                        }
                        else -> false
                    }
                }
                false
            }

        ls.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val m: Mails = mail_list!![i]
                val title: String = m.getuser().toString()
                val img: String = m.getImg().toString()
                val subject: String = m.getsubject().toString()
                val body: String = m.getbody().toString()
                val mydata = ArrayList<String>()
                mydata.add(title)
                mydata.add(img)
                mydata.add(subject)
                mydata.add(body)
                val result = Bundle()
                result.putStringArrayList("data", mydata)
                parentFragmentManager.setFragmentResult("tek", result)
                val fragment = ViewMail()
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.nav_host_frag, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        return
    }

    @SuppressLint("UseRequireInsteadOfGet")
    @Throws(IOException::class)
    fun ReadFromFile() {
        val sharedPreferences =
            requireActivity().getSharedPreferences(PREF.toString(), Context.MODE_PRIVATE)
        val path = requireActivity().filesDir
        val usr = sharedPreferences.getString(user.toString(), "")
        val filename = usr + "ib.txt"
        val file = File(path, filename)
        val scanner = Scanner(requireActivity().openFileInput(filename))
        while (scanner.hasNextLine()) {
            val line = scanner.nextLine()
            val line2 = scanner.nextLine()
            val line3 = scanner.nextLine()
            val img = getdp(line)
            mail_list!!.add(Mails(img, line, line2, line3))
        }

    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun getdp(User: String): String {
        val line2: String
        val line3: String
        try {
            val scanner = Scanner(requireActivity().openFileInput("users.txt"))
            while (scanner.hasNextLine()) {
                val line = scanner.nextLine()
                if (line == User) {
                    line2 = scanner.nextLine()
                    line3 = scanner.nextLine()
                    Log.d("USER", line3)
                    return line3
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return "img"


    }

}