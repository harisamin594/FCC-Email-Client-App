package com.example.fccemailclient

class Mails (img: String, line: String, line2: String, line3: String) {
   private var img: String? = null
   private var user: String? = null
   private var subject: String? = null
   private var body: String? = null


    fun getbody(): String? {
        return body
    }

    fun setuser(user: String?) {
        this.user = user
    }

    fun getuser(): String? {
        return user
    }

    @JvmName("getImg1")
    fun getImg(): String? {
        return img
    }

    fun getsubject(): String? {
        return subject
    }

    fun setsubject(subject: String?) {
        this.subject = subject
    }
}