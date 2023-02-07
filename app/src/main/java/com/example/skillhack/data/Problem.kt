package com.example.skillhack.data

data class Problem(
    var pid: String="",
    var deadline:String="",
    var prize:Int=0,
    var problemdesc:String="",
    var problemname:String="",
    var tags: ArrayList<String> = ArrayList(),
    var submissions: ArrayList<String> = ArrayList()
)
