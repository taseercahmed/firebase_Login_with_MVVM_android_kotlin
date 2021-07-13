package com.myloginapp.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.myloginapp.R
import com.myloginapp.viewmodels.LoginRegisterViewModel


class LoginWithPhone : Fragment() {
  lateinit var codeSent:String;
    private var loginRegisterViewModel: LoginRegisterViewModel? = null
   lateinit var mCallbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginRegisterViewModel = ViewModelProvider(this).get(
            LoginRegisterViewModel::class.java
        )
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    var view=inflater.inflate(R.layout.fragment_login_with_phone, container, false)

        var edittext_phone:EditText=view.findViewById(R.id.fragment_loginregister_phone)
        var sendbtn:Button=view.findViewById(R.id.fragment_loginregister_send)

        sendbtn.setOnClickListener {
            var phone=edittext_phone.text.toString()

            if(phone.isNullOrEmpty()){
                edittext_phone.setError("Please enter phone number")
            }

            if(phone.length<10){
                edittext_phone.setError("Invalid Phone Number")
            }

            if(mCallbacks!=null){
                Log.e("112233phoerf",phone.toString())
                loginRegisterViewModel?.registerWithPhone(phone,mCallbacks,requireActivity())
            }else{
                Toast.makeText(requireContext(),"hey am new",Toast.LENGTH_LONG).show()
            }
        }
        mCallbacks =
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(e: FirebaseException) {

                    if (e is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(view.context, "" + e.message, Toast.LENGTH_LONG).show()
                    } else if (e is FirebaseTooManyRequestsException) {
                        Toast.makeText(requireContext(), "" + e.message, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(s, forceResendingToken)

                    codeSent = s

                     if(s!=null){

                     loginRegisterViewModel?.setCode(s)
                      }

                    Toast.makeText(requireContext(), "Verification code sent.", Toast.LENGTH_SHORT)
                        .show()
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_loginRegisterFragment_to_loggedInFragment)

                }
            }

        return view
    }


}
