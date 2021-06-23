package com.minsk.guru.screen.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentSignUpBinding
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
        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_signUpFragment_to_HomeFragment)
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
            btnSignUp.setOnClickListener {
                viewModel?.signUp(
                    editEmail.text.toString(),
                    editPassword.text.toString(),
                    editName.text.toString(),
                    editSurname.text.toString()
                )
            }
        }
    }
}