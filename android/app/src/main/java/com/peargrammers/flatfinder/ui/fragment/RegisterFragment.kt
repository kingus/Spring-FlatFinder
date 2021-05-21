package com.peargrammers.flatfinder.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.dao.LoginRequest
import com.peargrammers.flatfinder.dao.RegisterRequest
import com.peargrammers.flatfinder.databinding.RegisterFragmentBinding
import com.peargrammers.flatfinder.ui.activity.AuthActivity
import com.peargrammers.flatfinder.ui.viewmodel.AuthViewModel
import com.peargrammers.flatfinder.utils.Status
import com.peargrammers.flatfinder.utils.Status.STATUS_OK

class RegisterFragment : Fragment(R.layout.register_fragment) {

    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: AuthViewModel
    lateinit var username: String
    lateinit var password: String
    lateinit var email: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as AuthActivity).viewModel

        binding.registerButton.setOnClickListener {

            username = binding.loginLayout.textInputET.text.toString()
            password = binding.passwordLayout.passwordTextInputET.text.toString()
            email = binding.emailLayout.emailInputET.text.toString()

            viewModel.postRegister(RegisterRequest(username, email, "", password))
        }

        binding.loginTextView.setOnClickListener {
            view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }


        viewModel.registerStatus.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                STATUS_OK.code -> Toast.makeText(context, getString(R.string.register_success), Toast.LENGTH_LONG).show()
                else -> Toast.makeText(context, getString(R.string.register_failed), Toast.LENGTH_LONG).show()
            }
        })

    }
}