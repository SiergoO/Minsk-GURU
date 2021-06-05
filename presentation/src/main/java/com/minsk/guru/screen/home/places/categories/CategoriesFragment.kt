package com.minsk.guru.screen.home.places.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentCategoriesBinding
import com.minsk.guru.domain.model.Achievement
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment(private val layout: Int = R.layout.fragment_categories) :
    Fragment(layout) {

    private val viewModel: CategoriesViewModel by viewModel()
    private var _binding: FragmentCategoriesBinding? = null
    val binding: FragmentCategoriesBinding
        get() = _binding!!
    private lateinit var categoriesAdapter: CategoriesAdapter

    init {
        lifecycleScope.launchWhenResumed {
            withContext(coroutineContext) {
                val placesObserver = Observer<List<Achievement>> { places ->
                    categoriesAdapter.set(places)
                }
                viewModel.places.observe(viewLifecycleOwner, placesObserver)
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
        binding.achievements.apply {
            categoriesAdapter = CategoriesAdapter(context,
                object : CategoriesAdapter.Callback {
                    override fun onCategoryClicked(achievement: Achievement) {
                        findNavController().navigate(R.id.action_categoriesFragment_to_placesFragment)
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