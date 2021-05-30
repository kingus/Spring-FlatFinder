package com.peargrammers.flatfinder.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.dao.LoginRequest
import com.peargrammers.flatfinder.databinding.LoginFragmentBinding
import com.peargrammers.flatfinder.datastore.UserPreferencesImpl
import com.peargrammers.flatfinder.ui.activity.AuthActivity
import com.peargrammers.flatfinder.ui.viewmodel.AuthViewModel
import com.peargrammers.flatfinder.utils.Resource
import com.peargrammers.flatfinder.utils.Status
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.login_fragment) {
    private val TAG = LoginFragment::class.qualifiedName
    lateinit var viewModel: AuthViewModel
    lateinit var username: String
    lateinit var password: String
    lateinit var userPreferencesImpl: UserPreferencesImpl

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.loginLayout.loginTextInputLayout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as AuthActivity).viewModel
        userPreferencesImpl = UserPreferencesImpl(requireContext())

        binding.loginButton.setOnClickListener {
            username = binding.loginLayout.textInputET.text.toString()
            password = binding.passwordLayout.passwordTextInputET.text.toString()
            Log.d(TAG, "loginButton.setOnClickListener")

            viewModel.postLogin(LoginRequest(username, password))
        }

        viewModel.token.observe(viewLifecycleOwner, Observer { response ->
            when (response) {

                is Resource.Success -> {
                    response.data?.let { token ->
                        Log.d(TAG, "login")
                        lifecycleScope.launch {
                            userPreferencesImpl.saveAuthToken(token)
                        }
                    }
                    view.findNavController().navigate(R.id.action_loginFragment_to_homeActivity2)
                }
            }
        })

        viewModel.loginStatus.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                Status.STATUS_OK.code -> Toast.makeText(
                    context,
                    getString(R.string.login_success),
                    Toast.LENGTH_LONG
                ).show()
                Status.INTERNAL_SERVER_ERROR.code -> Toast.makeText(
                    context,
                    getString(R.string.login_internal_error),
                    Toast.LENGTH_LONG
                ).show()
                Status.UNAUTHORIZED.code -> Toast.makeText(
                    context,
                    getString(R.string.wrong_login_password_label),
                    Toast.LENGTH_LONG
                ).show()
                else -> Toast.makeText(
                    context,
                    getString(R.string.register_failed),
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        binding.registerTextView.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }


}