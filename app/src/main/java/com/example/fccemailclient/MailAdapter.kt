package com.example.fccemailclient

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlin.properties.Delegates

class MailAdapter (context: Context, resource: Int, blist: ArrayList<*>) :
    ArrayAdapter<Any?>(context, resource, blist) {
    private lateinit var mail_list: ArrayList<Mails>
    private lateinit var mycontext: Context
    private var resid by Delegates.notNull<Int>()
    private lateinit var adapter: MailAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflator = LayoutInflater.from(context).inflate(R.layout.inbox_frag, parent, false)
        val mail = mail_list!![position]
        val name = inflator.findViewById<TextView>(R.id.row_user)
        val subject = inflator.findViewById<TextView>(R.id.description)
        val img = inflator.findViewById<ImageView>(R.id.picture)
        name.text = mail.getuser()
        subject.text = mail.getsubject()
        img.setImageResource(GetImgResId(mail.getImg()))
        return inflator
    }


    fun GetImgResId(name: String?): Int {
        return when (name) {
            "img.png" -> R.drawable.pic_1
            "img_1.png" -> R.drawable.pic_2
            "img_2.png" -> R.drawable.pic_3
            "img_3.png" -> R.drawable.pic_4
            else -> R.drawable.folder_icon
        }
    }
}



