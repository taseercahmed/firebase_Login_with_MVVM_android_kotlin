package com.myloginapp.viewmodels

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider
import com.myloginapp.model.AuthAppRepository


class LoginRegisterViewModel(application: Application) : AndroidViewModel(application) {

    private var authAppRepository: AuthAppRepository? = null
    private var userLiveData: MutableLiveData<FirebaseUser>? = null
     var codeSentbyPhone:MutableLiveData<String>?=null

    init{

        authAppRepository = AuthAppRepository(application)
        userLiveData = authAppRepository!!.getUserLiveData()
        codeSentbyPhone= MutableLiveData()
    }
     fun setCode(code:String){
         Log.e("1122334code",code)
         codeSentbyPhone!!.value=code
         Log.e("1122334code77", codeSentbyPhone!!.value.toString())
     }

    fun login(email: String?, password: String?) {
        authAppRepository!!.login(email, password)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun register(email: String?, password: String?) {
        authAppRepository!!.register(email, password)
    }

    fun registerWithPhone(
        phone: String?,
        mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
        mycontext: Context
    ) {
        Log.e("112233phone",phone.toString())
        authAppRepository!!.sendVerificationCode(phone,mCallbacks,mycontext)
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser>? {
        return userLiveData
    }
}