package com.myloginapp.model

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit


class AuthAppRepository(application: Application?) {
    private var application: Application? = null

    private var firebaseAuth: FirebaseAuth? = null
    private var userLiveData: MutableLiveData<FirebaseUser>? = null
    private var loggedOutLiveData: MutableLiveData<Boolean>? = null


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

    fun signInWithCredential2(
        credential: PhoneAuthCredential,
        requireView: View,
        actionLoginphoneToLoggedinphonecode: Int
    ) {
        firebaseAuth?.signInWithCredential(credential)?.addOnCompleteListener(OnCompleteListener {
         if(it.isSuccessful){
             Navigation.findNavController(requireView)
                        .navigate(actionLoginphoneToLoggedinphonecode)
         }else{
             Toast.makeText(application?.applicationContext,"Error in Credention",Toast.LENGTH_LONG).show()
         }
        })
    }

  fun firebaseAuthWithGoogle(
      idToken: String,
      actionLoginregisterfragmentToLoggedinfragment: Int,
      requireActivity: View
  ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful) {

                    Log.e("11223344", "signInWithCredential:success")

                    userLiveData!!.postValue(firebaseAuth!!.currentUser)
                    Navigation.findNavController(requireActivity).navigate(actionLoginregisterfragmentToLoggedinfragment)

                } else {

                    Log.e("112233error", "signInWithCredential:failure", it.exception)

                }
            })
    }

    fun firebaseAuthWithAnonymous(
        requireActivity: View,
        actionLoginregisterfragmentToLoggedinfragment: Int

    ) {

        firebaseAuth?.signInAnonymously()
            ?.addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful) {

                    Log.e("11223344anonymous", "signInWithCredential:success")

                    userLiveData!!.postValue(firebaseAuth!!.currentUser)
                    Navigation.findNavController(requireActivity).navigate(actionLoginregisterfragmentToLoggedinfragment)

                } else {

                    Log.e("112233error", "signInWithCredential:failure", it.exception)

                }
            })
    }
}
