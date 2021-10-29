package com.minsk.guru.screen.auth.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentSignInBinding
import com.minsk.guru.utils.showError
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment(layout: Int = R.layout.fragment_sign_in) : Fragment(layout) {

    private val viewModel: SignInViewModel by viewModel()

    private var _binding: FragmentSignInBinding? = null
    val binding: FragmentSignInBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(
            inflater,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel?.resultLiveData?.observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.action_signInFragment_to_HomeFragment)
            }
            viewModel?.errorLiveData?.observe(viewLifecycleOwner) {
                root.showError(it)
            }
            btnSignIn.setOnClickListener {
                viewModel?.signIn(
                    (binding.layoutEmail.findViewById(R.id.edit_email) as TextInputEditText).text.toString(),
                    (binding.layoutPassword.findViewById(R.id.edit_password) as TextInputEditText).text.toString()
                )
            }
            signUp.setOnClickListener { findNavController().navigate(R.id.action_signInFragment_to_signUpFragment) }
        }
    }
}