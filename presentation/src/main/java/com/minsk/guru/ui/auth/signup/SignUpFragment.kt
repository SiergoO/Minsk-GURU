package com.minsk.guru.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentSignUpBinding

class SignUpFragment (private val layout: Int = R.layout.fragment_sign_up) : Fragment(layout) {

    private lateinit var viewModel: SignUpViewModel

    private var _binding: FragmentSignUpBinding? = null
    val binding: FragmentSignUpBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater,
            layout,
            container,
            false
        )
        binding.lifecycleOwner = this
        return binding.root
    }
}