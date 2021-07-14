package com.myloginapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.myloginapp.R
import com.myloginapp.viewmodels.LoginRegisterViewModel


class HomeFragment : Fragment() {

    private val RC_SIGN_IN: Int=233
    private var googleSignInClient: GoogleSignInClient?=null
    var phonebtn: Button? =null
    var googlebtn: Button? =null
    var anonymousbtn: Button? =null
    private var loginRegisterViewModel: LoginRegisterViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        loginRegisterViewModel = ViewModelProvider(this).get(
            LoginRegisterViewModel::class.java
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v= inflater.inflate(R.layout.fragment_home, container, false)

        phonebtn=v.findViewById(R.id.button)
        googlebtn=v.findViewById(R.id.button2)
        anonymousbtn=v.findViewById(R.id.button3)

        phonebtn?.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_loginRegisterFragment_to_loggedInFragment76)
        }
        googlebtn?.setOnClickListener {
               signIn()
        }
        anonymousbtn?.setOnClickListener {
            loginRegisterViewModel?.anonymousFirebaseAuth(requireView(),R.id.action_loginRegisterFragment_to_loggedInFragment)
        }
        return v
    }
    private fun signIn() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)!!
                Log.e("112233d", "firebaseAuthWithGoogle:" + account.id)
                loginRegisterViewModel?.googleSignIn(account.idToken!!,requireView(),R.id.action_loginRegisterFragment_to_loggedInFragment)
            } catch (e: ApiException) {

                Log.w("112233d", "Google sign in failed", e)
            }
        }
    }


}
