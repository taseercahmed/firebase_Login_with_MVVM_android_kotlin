package com.myloginapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.myloginapp.myinterfaces.AuthPhoneCode

class MainActivity : AppCompatActivity(), AuthPhoneCode {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun setphoneCode(code: String) {
        Log.e("11223345",code)
    }

}
