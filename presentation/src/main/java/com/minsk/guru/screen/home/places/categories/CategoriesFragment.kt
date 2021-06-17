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
import com.minsk.guru.domain.model.Category
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment(private val layout: Int = R.layout.fragment_categories) :
    Fragment(layout) {

    private val viewModel: CategoriesViewModel by viewModel()

    private var _binding: FragmentCategoriesBinding? = null
    val binding: FragmentCategoriesBinding
        get() = _binding!!
    private lateinit var categoriesAdapter: CategoriesAdapter

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
        binding.viewModel = viewModel
        viewModel.getCategories()
        viewModel.categoriesResultLiveData.observe(viewLifecycleOwner) {
            categoriesAdapter.set(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.achievements.apply {
            categoriesAdapter = CategoriesAdapter(context,
                object : CategoriesAdapter.Callback {

                    override fun onCategoryClicked(category: Category) {
                        val bundle =
                            bundleOf("categoryName" to category.name)
                        findNavController().navigate(
                            R.id.action_categoriesFragment_to_placesFragment,
                            bundle
                        )
                    }
                })
            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = categoriesAdapter
            setHasFixedSize(true)
            super.onViewCreated(view, savedInstanceState)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}