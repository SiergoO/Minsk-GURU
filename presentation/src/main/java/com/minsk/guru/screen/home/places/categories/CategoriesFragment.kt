package com.minsk.guru.screen.home.places.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentCategoriesBinding
import com.minsk.guru.domain.model.UserCategory
import com.minsk.guru.model.toUiModel
import com.minsk.guru.utils.showError
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment(private val layout: Int = R.layout.fragment_categories) :
    Fragment(layout) {

    private val viewModel: CategoriesViewModel by viewModel()

    private var _binding: FragmentCategoriesBinding? = null
    val binding: FragmentCategoriesBinding
        get() = _binding!!

    private var categoriesAdapter: CategoriesAdapter? = null

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
        binding.apply {
            lifecycleOwner = this@CategoriesFragment
            binding.viewModel = viewModel
        }
        viewModel.fetchVisitedPlaces()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.visitedPlaces.observe(viewLifecycleOwner) {
            viewModel.createPagingOptions(this)
        }
        viewModel.options.observe(viewLifecycleOwner) { options ->
            categoriesAdapter = CategoriesAdapter(
                options,
                context,
                object : CategoriesAdapter.CategoryClickListener {
                    override fun onCategoryClicked(userCategory: UserCategory) {
                        viewModel.logEvent(
                            EVENT_CATEGORY_SELECTED,
                            bundleOf(Pair(ARG_CATEGORY_NAME, userCategory.name))
                        )
                        val bundle = bundleOf(KEY_USER_CATEGORY to userCategory.toUiModel())
                        findNavController().navigate(
                            R.id.action_categoriesFragment_to_placesFragment,
                            bundle
                        )
                    }
                })
            binding.achievements.apply {
                adapter = categoriesAdapter
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                lifecycleScope.launch {
                    viewModel.handleLoadingStates(categoriesAdapter?.loadStateFlow)
                }
                setHasFixedSize(false)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            viewModel.logError(error.message ?: getString(R.string.error_default))
            showError(error)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        categoriesAdapter = null
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
            binding.achievements.alpha = 0.5f
        } else {
            binding.progressBar.visibility = View.GONE
            binding.achievements.alpha = 1f
        }
    }

    companion object {
        private const val KEY_USER_CATEGORY = "userCategory"
        private const val EVENT_CATEGORY_SELECTED = "category_selected"
        private const val ARG_CATEGORY_NAME = "categoryName"
    }
}