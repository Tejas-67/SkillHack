package com.example.skillhack.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skillhack.data.Problem

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

    private var _currProblem=MutableLiveData<Problem>()
    public val currproblem get()=_currProblem.value

    public fun updateCurrProblem(p: Problem){
        _currProblem.value=p
    }
    public fun nullCurrProb(){
        _currProblem=MutableLiveData<Problem>()
    }
    public fun addToSolved(uid: String) {
        _listOfSolved.add(uid)
    }

    public fun increasePrize(amount: Int){
        _prize.value= _prize.value?.plus(amount)
    }

}