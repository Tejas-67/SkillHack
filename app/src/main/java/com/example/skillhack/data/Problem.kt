package com.example.skillhack.data

data class Problem(
    val pid: String="",
    val deadline:String="",
    val prize:Int=0,
    val problemdesc:String="",
    val problemname:String="",
    val tags: ArrayList<String> = ArrayList(),
    val submissions: ArrayList<String> = ArrayList()
)
