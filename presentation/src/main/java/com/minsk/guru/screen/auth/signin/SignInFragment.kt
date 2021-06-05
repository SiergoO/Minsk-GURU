package com.minsk.guru.screen.auth.signin

import android.app.ActivityOptions.makeSceneTransitionAnimation
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentSignInBinding
import com.minsk.guru.screen.home.HomeActivity
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
        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent, makeSceneTransitionAnimation(activity).toBundle())
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(this.requireView(), it.cause?.message.toString(), Snackbar.LENGTH_LONG)
                .show()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
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