package com.myloginapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.myloginapp.R


class HomeFragment : Fragment() {
  var phonebtn: Button? =null
    var googlebtn: Button? =null
    var anonymousbtn: Button? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        }
        anonymousbtn?.setOnClickListener {

        }
        return v
    }


}
