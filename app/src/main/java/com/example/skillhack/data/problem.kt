package com.example.skillhack.data

data class problem(
    val deadline:String="",
    val prize:Int=0,
    val problemdesc:String="",
    val problemname:String="",
    val tags: MutableList<String> = mutableListOf<String>()
)
