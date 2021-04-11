package com.minsk.guru.ui.auth

import android.app.ActivityOptions.makeSceneTransitionAnimation
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentAuthBinding
import com.minsk.guru.ui.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment(private val layout: Int = R.layout.fragment_auth) : Fragment(layout) {

    private val viewModel: AuthViewModel by viewModel()

    private var _binding: FragmentAuthBinding? = null
    val binding: FragmentAuthBinding
        get() = _binding!!

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
        viewModel.exceptionLiveData.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
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