package com.minsk.guru.screen.home.places.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentCategoriesBinding
import com.minsk.guru.domain.model.UserCategory
import com.minsk.guru.utils.showError
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.achievements.apply {
            viewModel.categories.observe(viewLifecycleOwner) {
                categoriesAdapter = CategoriesAdapter(
                    context,
                    it,
                    object : CategoriesAdapter.Callback {
                        override fun onCategoryClicked(category: UserCategory) {
                            val bundle = bundleOf("categoryName" to category.name)
                            findNavController().navigate(
                                R.id.action_categoriesFragment_to_placesFragment,
                                bundle
                            )
                        }
                    })
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = categoriesAdapter
                setHasFixedSize(true)
            }
            viewModel.error.observe(viewLifecycleOwner) {
                showError(it)
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        categoriesAdapter = null
    }
}