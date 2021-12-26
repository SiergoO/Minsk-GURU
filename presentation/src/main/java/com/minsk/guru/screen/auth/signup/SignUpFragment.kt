package com.minsk.guru.screen.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentSignUpBinding
import com.minsk.guru.utils.showError
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment(private val layout: Int = R.layout.fragment_sign_up) : Fragment(layout) {

    private val viewModel: SignUpViewModel by viewModel()

    private var _binding: FragmentSignUpBinding? = null
    val binding: FragmentSignUpBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            layout,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel?.signUpResult?.observe(viewLifecycleOwner) {
                viewModel?.logEvent(
                    FirebaseAnalytics.Event.SIGN_UP,
                    bundleOf(Pair(FirebaseAnalytics.Param.METHOD, FirebaseAnalytics.Event.SIGN_UP))
                )
                findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
            }
            viewModel?.error?.observe(viewLifecycleOwner) { error ->
                viewModel?.logError(error.message ?: getString(R.string.error_default))
                showError(error)
            }
            btnSignUp.setOnClickListener {
                viewModel?.signUp(
                    editEmail.text.toString(),
                    editPassword.text.toString(),
                    editName.text.toString(),
                    editSurname.text.toString()
                )
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}