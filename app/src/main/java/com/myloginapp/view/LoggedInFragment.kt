package com.myloginapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseUser
import com.myloginapp.R
import com.myloginapp.viewmodels.LoggedInViewModel
import com.myloginapp.viewmodels.LoginRegisterViewModel


class LoggedInFragment : Fragment() {
    private var loggedInUserTextView: TextView? = null
    private var logOutButton: Button? = null
    private var loggedInViewModel: LoggedInViewModel? = null

      private var loginRegisterViewModel: LoginRegisterViewModel? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loggedInViewModel = ViewModelProvider(this).get(LoggedInViewModel::class.java)

        loginRegisterViewModel=ViewModelProvider(this).get(LoginRegisterViewModel::class.java)

//        loginRegisterViewModel!!.getcodeSentbyPhone()!!.observe(this,{
//            loggedInUserTextView!!.text=it+"kjkjjh"
//        })
//        loggedInViewModel!!.userLiveData!!.observe(this,{
//            if (it != null) {
//                loggedInUserTextView!!.text = "Logged In User: " + it.email
//                logOutButton?.setEnabled(true)
//            } else {
//                logOutButton?.setEnabled(false)
//            }
//        })

        loggedInViewModel!!.loggedOutLiveData!!.observe(this, {
            if (it) {
                Toast.makeText(context, "User Logged Out", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_loggedInFragment_to_loginRegisterFragment)
            }
        })
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_logged_in, container, false)
        loggedInUserTextView = view.findViewById(R.id.fragment_loggedin_loggedInUser)
        logOutButton = view.findViewById(R.id.fragment_loggedin_logOut)
        loggedInUserTextView!!.text= loginRegisterViewModel!!.getcodeSentbyPhone().toString();
        logOutButton?.setOnClickListener{
            loggedInViewModel!!.logOut()
        }

        return view
    }
}