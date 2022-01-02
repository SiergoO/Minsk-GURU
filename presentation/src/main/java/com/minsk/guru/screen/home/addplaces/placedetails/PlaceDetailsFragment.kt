package com.minsk.guru.screen.home.addplaces.placedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentPlaceDetailsBinding
import com.minsk.guru.model.Place
import com.minsk.guru.model.toDomainModel
import com.minsk.guru.utils.hideSoftKeyboard
import com.minsk.guru.utils.showError
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaceDetailsFragment(private val layout: Int = R.layout.fragment_place_details) : Fragment(layout) {

    private val viewModel: PlaceDetailsViewModel by viewModel()

    private var _binding: FragmentPlaceDetailsBinding? = null
    val binding: FragmentPlaceDetailsBinding
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
        viewModel.place = requireArguments().getParcelable<Place>(KEY_PLACE)!!.toDomainModel()
        viewModel.isVisited = requireArguments().getBoolean(KEY_IS_VISITED)
        binding.viewModel = viewModel
        binding.place = viewModel.place
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val firstNumber = viewModel.randomInt()
        val secondNumber = viewModel.randomInt()
        viewModel.updateVisitStatusResult.observe(viewLifecycleOwner) { isUpdateVisitStatusSuccessful ->
            if (isUpdateVisitStatusSuccessful) findNavController().navigateUp()
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            showError(error)
        }
        binding.apply {
            if (this@PlaceDetailsFragment.viewModel.isVisited) {
                groupProveCalculation.visibility = View.GONE
                tvIsVisited.visibility = View.VISIBLE
            } else {
                groupProveCalculation.visibility = View.VISIBLE
                tvIsVisited.visibility = View.GONE
            }
            tvProveCalculationBody.text = getString(R.string.prove_calculation_body, firstNumber, secondNumber)
            editCalculationResult.setOnEditorActionListener { v, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        activity?.hideSoftKeyboard()
                        if (this@PlaceDetailsFragment.viewModel.checkAnswerValid(
                                firstNumber,
                                secondNumber,
                                (v as EditText).text.toString().toInt()
                            )
                        ) {
                            this@PlaceDetailsFragment.viewModel.updateLocalPlace(this@PlaceDetailsFragment.viewModel.place!!, "", true)
                        } else {
                            binding.groupProveCalculation.visibility = View.GONE
                            binding.tvProveFailed.visibility = View.VISIBLE
                            lifecycleScope.launch {
                                delay(2000L)
                                findNavController().navigateUp()
                            }
                        }
                        true
                    }
                    else -> true
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val KEY_PLACE = "place"
        private const val KEY_IS_VISITED = "isVisited"
    }
}