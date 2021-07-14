package com.myloginapp.view

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alimuzaffar.lib.pin.PinEntryEditText
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.myloginapp.R
import com.myloginapp.viewmodels.LoginRegisterViewModel


class PhoneCodeEnterScreen : Fragment() {
 var pinentrycode:PinEntryEditText?=null
    private var loginRegisterViewModel: LoginRegisterViewModel? = null
    private var code:String?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginRegisterViewModel= ViewModelProvider(this).get(LoginRegisterViewModel::class.java)
          Log.e("112234new", loginRegisterViewModel!!.codeSentbyPhone!!.value.toString())
        loginRegisterViewModel!!.codeSentbyPhone!!.observe(this,{
            code=it
            Log.e("1122334c",it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      var v=inflater.inflate(R.layout.fragment_phone_code_enter_screen, container, false)
       pinentrycode=v.findViewById(R.id.pinentrycode)
        pinentrycode?.setOnPinEnteredListener {
            Log.e("112233newcode", code.toString())
            verifySignInCode(it.toString())
        }
        return v
    }
    private fun verifySignInCode(s: String) {
        //String code = pass.getText().toString();
        try {
//            val credential = PhoneAuthProvider.getCredential(codeSent, s)
//            signInWithPhoneAuthCredential(credential)
        } catch (e: Exception) {
            val toast: Toast =
                Toast.makeText(requireContext(), "Verification Code is wrong", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()

        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

    }

}