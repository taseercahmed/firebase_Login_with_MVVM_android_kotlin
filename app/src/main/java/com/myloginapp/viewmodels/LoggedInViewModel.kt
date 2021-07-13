package com.myloginapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.myloginapp.model.AuthAppRepository


class LoggedInViewModel(application: Application) : AndroidViewModel(application) {

    private val authAppRepository: AuthAppRepository

    val userLiveData: MutableLiveData<FirebaseUser>?

    val loggedOutLiveData: MutableLiveData<Boolean>?

    init {
        authAppRepository = AuthAppRepository(application)
        userLiveData = authAppRepository.getUserLiveData()
        loggedOutLiveData = authAppRepository.getLoggedOutLiveData()
    }

    fun logOut() {
        authAppRepository.logOut()
    }


}