package com.minsk.guru.ui.auth.signup

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.minsk.guru.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.minsk.guru.databinding.FragmentSignUpBinding
import com.minsk.guru.ui.home.HomeActivity
import java.lang.NullPointerException

class SignUpFragment (private val layout: Int = R.layout.fragment_sign_up) : Fragment(layout) {

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
        viewModel.exceptionLiveData.observe(viewLifecycleOwner) {
            if (it is NullPointerException) {
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Snackbar.make(this.requireView(), it?.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnSignUp.setOnClickListener {
                viewModel?.signUp(
                    (binding.layoutEmail.findViewById(R.id.edit_email) as TextInputEditText).text.toString(),
                    (binding.layoutPassword.findViewById(R.id.edit_password) as TextInputEditText).text.toString(),
                    (binding.layoutName.findViewById(R.id.edit_name) as TextInputEditText).text.toString(),
                    (binding.layoutSurname.findViewById(R.id.edit_surname) as TextInputEditText).text.toString()
                )

            }
        }
    }
}