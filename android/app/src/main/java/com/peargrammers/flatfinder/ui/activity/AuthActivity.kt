package com.peargrammers.flatfinder.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.ui.fragment.LoginFragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)

        val loginFragment = LoginFragment()

        replaceFragment(R.id.fragmentFL, loginFragment)

    }

    fun replaceFragment(id: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(id, fragment)
            commit()
        }
    }
}
