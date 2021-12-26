package com.minsk.guru.screen.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentProfileBinding
import com.minsk.guru.utils.showError
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment(private val layout: Int = R.layout.fragment_profile) : Fragment(layout) {

    private val viewModel: ProfileViewModel by viewModel()

    private var _binding: FragmentProfileBinding? = null
    val binding: FragmentProfileBinding
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.profileInfo.observe(viewLifecycleOwner) { profile ->
            Glide.with(this)
                .load(profile.profilePhotoLink)
                .centerCrop()
                .into(binding.imgProfile)
            binding.txtUserFullName.text = getString(R.string.full_name, profile.name, profile.surname)
            binding.txtUserEmail.text = profile.email
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            viewModel.logError(error.message?: getString(R.string.error_default))
            showError(error)
        }
        binding.btnLogOut.setOnClickListener {
            viewModel.apply {
                logOut()
                val mainNavController = parentFragment?.parentFragment?.findNavController()
                if (!checkLoggedIn()) {
                    mainNavController?.navigate(R.id.action_homeFragment_to_signInFragment)
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}