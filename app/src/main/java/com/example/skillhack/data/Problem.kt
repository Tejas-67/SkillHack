package com.example.skillhack.data

data class Problem(
    var pid: String="",
    var deadline:String="",
    var prize:Int=0,
    var problemdesc:String="",
    var problemname:String="",
    var solvers :ArrayList<String> = arrayListOf(),
    var submissions: Map<String,String> = mapOf(),
    var tags: ArrayList<String> = arrayListOf()
)
