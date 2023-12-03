package com.example.homework14

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework14.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val viewModel: ViewModel by viewModels()

    private lateinit var adapter: FragmentAdapter

    override fun setUp() {
        adapter = FragmentAdapter { data ->
            viewModel.deleteItem(data)
        }
        with(binding) {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerView.adapter = adapter
        }
    }

    override fun bindViewActionListeners() {
        with(binding) {
            btnOne.setOnClickListener {
                viewModel.addBMW()
            }
            btnTwo.setOnClickListener {
                viewModel.addMercedes()
            }
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.refreshData()
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataFlow.collect { dataList ->
                    adapter.submitList(dataList)
                }
            }
        }
    }
}

