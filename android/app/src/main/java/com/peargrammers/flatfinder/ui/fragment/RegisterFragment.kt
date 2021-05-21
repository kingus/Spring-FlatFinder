package com.peargrammers.flatfinder.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peargrammers.flatfinder.R
import com.peargrammers.flatfinder.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment(R.layout.register_fragment) {

    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

}