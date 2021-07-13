package com.myloginapp.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseUser
import com.myloginapp.R
import com.myloginapp.viewmodels.LoginRegisterViewModel


class LoginRegisterFragment : Fragment() {

    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var loginButton: Button? = null
    private var registerButton: Button? = null
    private var loginRegisterViewModel: LoginRegisterViewModel? = null
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginRegisterViewModel = ViewModelProvider(this).get(
            LoginRegisterViewModel::class.java
        )
        loginRegisterViewModel!!.getUserLiveData()!!
            .observe(this, {
                if (it!= null) {
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_loginRegisterFragment_to_loggedInFragment)
                }
            })
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_login_register, container, false)
        emailEditText = view.findViewById(R.id.fragment_loginregister_email)
        passwordEditText = view.findViewById(R.id.fragment_loginregister_password)
        loginButton = view.findViewById(R.id.fragment_loginregister_login)
        registerButton = view.findViewById(R.id.fragment_loginregister_register)

        loginButton?.setOnClickListener(View.OnClickListener {
            val email = emailEditText?.getText().toString()
            val password = passwordEditText?.getText().toString()
            if (email.length > 0 && password.length > 0) {
                loginRegisterViewModel!!.login(email, password)
            } else {
                Toast.makeText(
                    context,
                    "Email Address and Password Must Be Entered",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        registerButton?.setOnClickListener{
            val email = emailEditText?.getText().toString()
            val password = passwordEditText?.getText().toString()
            if (email.length > 0 && password.length > 0) {
                loginRegisterViewModel!!.register(email, password)
            } else {
                Toast.makeText(
                    context,
                    "Email Address and Password Must Be Entered",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return view
    }
}