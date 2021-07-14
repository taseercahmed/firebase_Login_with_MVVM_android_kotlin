package com.myloginapp.model

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit


class AuthAppRepository(application: Application?) {
    private var application: Application? = null

    private var firebaseAuth: FirebaseAuth? = null
    private var userLiveData: MutableLiveData<FirebaseUser>? = null
    private var loggedOutLiveData: MutableLiveData<Boolean>? = null
    private var codeSent:String=""

     init {

        this.application = application
        firebaseAuth = FirebaseAuth.getInstance()
        userLiveData = MutableLiveData()
        loggedOutLiveData = MutableLiveData()
        if (firebaseAuth!!.getCurrentUser() != null) {
            userLiveData!!.postValue(firebaseAuth!!.getCurrentUser())
            loggedOutLiveData!!.postValue(false)
        }

    }

    fun login(email: String?, password: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            firebaseAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(application!!.mainExecutor,
                    { task ->
                        if (task.isSuccessful) {
                            userLiveData!!.postValue(firebaseAuth!!.currentUser)
                        } else {
                            Toast.makeText(
                                application!!.applicationContext,
                                "Login Failure: " + task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun register(email: String?, password: String?) {
        firebaseAuth!!.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(application!!.mainExecutor,
                { task ->
                    if (task.isSuccessful) {
                        userLiveData!!.postValue(firebaseAuth!!.currentUser)
                    } else {
                        Toast.makeText(
                            application!!.applicationContext,
                            "Registration Failure: " + task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

    fun logOut() {
        firebaseAuth!!.signOut()
        loggedOutLiveData!!.postValue(true)
    }
    fun getUserLiveData(): MutableLiveData<FirebaseUser>? {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean>? {
        return loggedOutLiveData
    }

    fun sendVerificationCode(
        phoneNumber: String?,
        mCallbacks: OnVerificationStateChangedCallbacks,
        mycontext: Context
    ) {
        Log.e("112233phone",phoneNumber.toString())
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber.toString(),  // Phone number to verify
            60,  // Timeout duration
            TimeUnit.SECONDS,  // Unit of timeout
            mycontext as Activity,  // Activity (for callback binding)
            mCallbacks
        )
    }


}
