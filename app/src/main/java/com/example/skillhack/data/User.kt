package com.example.skillhack.data

data class User (
    var problemCount :Int =0,
    var image: String = "",
    var name: String ="",
    var problems: ArrayList<Map<String,String>> = arrayListOf(mapOf()),
    var rewardsEarned: Int = 0,
    var skills :ArrayList<String> = arrayListOf(),
    var mobileNumber : String = "",
    var dob: String = ""
)