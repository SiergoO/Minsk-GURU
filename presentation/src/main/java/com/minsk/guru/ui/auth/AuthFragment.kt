package com.minsk.guru.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentAuthBinding
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment(private val layout: Int = R.layout.fragment_auth) : Fragment(layout) {

    private val viewModel: AuthViewModel by viewModel()

    private var _binding: FragmentAuthBinding? = null
    val binding: FragmentAuthBinding
        get() = _binding!!

    init {
        lifecycleScope.launchWhenStarted {
            withContext(coroutineContext) {
                val signInObserver = Observer<Boolean> {
                    if (it == true) {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Smth wrong", Toast.LENGTH_SHORT).show()
                    }
                }
                viewModel.isSignInSuccessful.observe(viewLifecycleOwner, signInObserver)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            layout,
            container,
            false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signIn.setOnClickListener {
            viewModel.signIn(
                binding.email.text.toString(),
                binding.password.text.toString()
            )
        }
    }
}