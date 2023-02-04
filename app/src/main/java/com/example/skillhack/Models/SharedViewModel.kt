package com.example.skillhack.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    private val _name: String=""
    public val name: String
        get()=_name

    private val _phoneNumber: Number? =null
    public val phoneNumber get()=_phoneNumber


    private var _prize=MutableLiveData<Int>(0)
            public val prize: LiveData<Int> get()=_prize

    private val _listOfSolved=mutableListOf<String>()
    public val listOfSolved: MutableList<String> get()=_listOfSolved

    public fun addToSolved(uid: String) {
        _listOfSolved.add(uid)
    }

    public fun increasePrize(amount: Int){
        _prize.value= _prize.value?.plus(amount)
    }

}