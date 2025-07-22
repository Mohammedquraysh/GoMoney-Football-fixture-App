package com.presentation.ui.fixtures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.domain.model.Competition
import com.example.gomoneyfootballfixtureassessment.databinding.FragmentFixturesBinding
import com.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FixturesFragment : Fragment() {
    private var _binding: FragmentFixturesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FixturesViewModel by viewModels()
    private lateinit var fixturesAdapter: FixturesAdapter
    private lateinit var competitionsAdapter: CompetitionsSpinnerAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFixturesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSpinner()
        setupSwipeRefresh()
        observeViewModel()
    }


    private fun setupRecyclerView() {
        fixturesAdapter = FixturesAdapter { fixture ->
            findNavController().navigate(
                FixturesFragmentDirections.actionFixturesFragmentToFixtureDetailsFragment(fixture.id)
            )
        }

        binding.recyclerViewFixtures.apply {
            adapter = fixturesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    private fun setupSpinner() {
        competitionsAdapter = CompetitionsSpinnerAdapter(requireContext())
        binding.spinnerCompetitions.adapter = competitionsAdapter

        binding.spinnerCompetitions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val competitionId = if (position == 0) null else competitionsAdapter.getItem(position)?.id
                viewModel.filterByCompetition(competitionId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshFixtures()
        }
    }

    private fun observeViewModel() {
        viewModel.fixtures.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.textViewError.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textViewError.visibility = View.GONE
                    resource.data?.let { fixturesAdapter.submitList(it) }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textViewError.visibility = View.VISIBLE
                    binding.textViewError.text = resource.message
                }
            }
        }

        viewModel.competitions.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { competitions ->
                        val allCompetitions = listOf(
                            Competition(0, "All Competitions", "ALL", "ALL", null)
                        ) + competitions
                        competitionsAdapter.updateCompetitions(allCompetitions)
                    }
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }

        viewModel.isRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
            binding.swipeRefreshLayout.isRefreshing = isRefreshing
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
