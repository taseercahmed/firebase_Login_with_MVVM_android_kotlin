package com.myloginapp.view


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.myloginapp.myinterfaces.AuthPhoneCode
import com.myloginapp.R
import com.myloginapp.viewmodels.LoginRegisterViewModel
import com.rilixtech.widget.countrycodepicker.CountryCodePicker


class LoginWithPhone : Fragment() {

    private lateinit var sendbtn: Button
    private lateinit var edittext_phone: EditText
    lateinit var codeSent:String;
    private var loginRegisterViewModel: LoginRegisterViewModel? = null
   lateinit var mCallbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var authphoneinstance: AuthPhoneCode
     var verificationId:String?=null

    var countryCodePicker: CountryCodePicker? = null

    var countryCode: String? = null
    var alertDialog: AlertDialog? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            authphoneinstance= (activity as AuthPhoneCode?)!!
        } catch (e: ClassCastException) {
            throw ClassCastException("Error in retrieving data. Please try again")
        }
    }
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

         edittext_phone=view.findViewById(R.id.fragment_loginregister_phone)
        sendbtn=view.findViewById(R.id.fragment_loginregister_send)
        countryCodePicker = view.findViewById(R.id.ccp)


        sendbtn.setOnClickListener {
            var phone=edittext_phone.text.toString()
            countryCode = countryCodePicker?.getSelectedCountryCodeWithPlus()

            if(phone.isNullOrEmpty()){
                edittext_phone.setError("Please enter phone number")
            }

            if(phone.length<10){
                edittext_phone.setError("Invalid Phone Number")
            }

            if(mCallbacks!=null){
                Log.e("112233phoerf",phone.toString())
                if (phone.contains(countryCode.toString())) {

                    loginRegisterViewModel?.registerWithPhone(phone,mCallbacks,requireActivity())

                } else {
                    val phoneNumberWithCountryCode = countryCode + phone

                   loginRegisterViewModel?.registerWithPhone(phoneNumberWithCountryCode,mCallbacks,requireActivity())

                }

            }else{
                Toast.makeText(requireContext(),"hey am new",Toast.LENGTH_LONG).show()
            }
        }

        mCallbacks =
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                     var code = phoneAuthCredential.getSmsCode();
                    if(code!=null){
                        Log.e("112233codenew",code)
                        authphoneinstance.setphoneCode(code)
                     loginRegisterViewModel?.setCode(code)
                        verifyCode(code)

                    }
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
                   verificationId=s


                }
            }

        return view
    }
    private fun verifyCode(code: String) {

        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)


        loginRegisterViewModel?.signInWithCredential(credential,requireView(),R.id.action_loginRegisterFragment_to_loggedInFragment)
    }



}
