package com.example.challengechapterfive.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechapterfive.room.UserAccount

class UserViewModel: ViewModel(){
    var userLogin : MutableLiveData<UserAccount> = MutableLiveData()
    fun getUserAccount(user: UserAccount) {
        userLogin.postValue(user)
    }
}