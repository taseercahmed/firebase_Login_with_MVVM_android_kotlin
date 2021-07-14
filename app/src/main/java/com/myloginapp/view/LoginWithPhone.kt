package com.myloginapp.view


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
import androidx.navigation.Navigation
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.myloginapp.AuthPhoneCode
import com.myloginapp.R
import com.myloginapp.viewmodels.LoginRegisterViewModel
import com.rilixtech.widget.countrycodepicker.CountryCodePicker


class LoginWithPhone : Fragment() {
    private lateinit var sendbtn: Button
    private lateinit var edittext_phone: EditText
    lateinit var codeSent:String;
    private var loginRegisterViewModel: LoginRegisterViewModel? = null
   lateinit var mCallbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks

    var countryCodePicker: CountryCodePicker? = null

    var countryCode: String? = null
    var alertDialog: AlertDialog? = null

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
//        alertDialog = AlertDialog.Builder()
//            .setContext(requireContext())
//            .setMessage("Please wait")
//            .setCancelable(true)
//            .build()

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
                 //   sendVerificationCode(phone)
                    loginRegisterViewModel?.registerWithPhone(phone,mCallbacks,requireActivity())

                } else {
                    val phoneNumberWithCountryCode = countryCode + phone
                  //  sendVerificationCode(phoneNumberWithCountryCode)
                   loginRegisterViewModel?.registerWithPhone(phoneNumberWithCountryCode,mCallbacks,requireActivity())

                }

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
                        Log.e("1122334",s)
                     if(s!=null){


                     loginRegisterViewModel?.setCode(s)
                      }

                    Toast.makeText(requireContext(), "Verification code sent.", Toast.LENGTH_SHORT)
                        .show()
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_loginphone_to_loggedInphonecode)

                }
            }

        return view
    }



//    fun sendVerificationCode(phoneNumber: String) {
//        if (phoneNumber.isEmpty()) {
//           edittext_phone.setError("Phone no required")
//            alertDialog!!.cancel()
//            return
//        }
//        if (phoneNumber.length < 10) {
//            edittext_phone.setError("Please enter a valid phone")
//            alertDialog!!.cancel()
//            return
//        }
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//            phoneNumber,  // Phone number to verify
//            60,  // Timeout duration
//            TimeUnit.SECONDS,  // Unit of timeout
//            requireActivity(),  // Activity (for callback binding)
//            mCallbacks
//        ) // OnVerificationStateChangedCallbacks
//    }
//
//   var  mCallbacks: OnVerificationStateChangedCallbacks =
//        object : OnVerificationStateChangedCallbacks() {
//            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
//              //  alertDialog!!.cancel()
//                  Toast.makeText(requireContext(), "sucessfull " , Toast.LENGTH_SHORT).show();
//                //signInWithPhoneAuthCredential(phoneAuthCredential);
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//                //Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
//             //   alertDialog!!.cancel()
//                   Toast.makeText(requireContext(), "error is " + e, Toast.LENGTH_SHORT).show();
//                //Log.e("112233", "" + e);
//                if (e is FirebaseAuthInvalidCredentialsException) {
//                    Toast.makeText(requireContext(), "" + e.message, Toast.LENGTH_LONG).show()
//                } else if (e is FirebaseTooManyRequestsException) {
//                    Toast.makeText(requireContext(), "" + e.message, Toast.LENGTH_LONG).show()
//                }
//            }
//
//            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
//                super.onCodeSent(s, forceResendingToken)
//              //  alertDialog!!.cancel()
//                codeSent = s
//                Log.e("112233",s)
//               // val phoneNumber: String = phonnumber.getText().toString()
//                Toast.makeText(requireContext(), "Verification code sent.", Toast.LENGTH_SHORT)
//                    .show()
////                val intent = Intent(this@Signupactivity, PasswordActivity::class.java)
////                intent.putExtra("phoneNumber", phoneNumber)
////                intent.putExtra("code", s)
////                startActivity(intent)
////                finish()
//
//                //Log.e("112233", "code: " + s);
//            }
//        }
}
