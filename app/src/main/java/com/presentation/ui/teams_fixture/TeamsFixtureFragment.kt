package com.presentation.ui.teams_fixture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gomoneyfootballfixtureassessment.databinding.FragmentTeamsFixtureBinding
import com.presentation.ui.fixtures.FixturesAdapter
import com.presentation.ui.fixtures.FixturesViewModel
import com.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamsFixtureFragment : Fragment() {
    private var _binding: FragmentTeamsFixtureBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FixturesViewModel by viewModels()
    private lateinit var fixturesAdapter: FixturesAdapter
    private var competitionId: String? = null
    private var competitionName: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTeamsFixtureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            competitionId = it.getString("competition_id")
            competitionName = it.getString("competition_name")
        }

        setupRecyclerView()
        observeViewModel()
        viewModel.filteredCompetitions(competitionId?.toInt())
    }



    private fun setupRecyclerView() {
        fixturesAdapter = FixturesAdapter { fixture -> }

        binding.recyclerViewFixtures.apply {
            adapter = fixturesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }


    private fun observeViewModel() {
        viewModel.filteredFixtures.observe(viewLifecycleOwner) { resource ->
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
    }

    companion object {
        private const val ARG_COMPETITION_ID = "competition_id"

        fun newInstance(competitionId: String): TeamsFixtureFragment {
            return TeamsFixtureFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_COMPETITION_ID, competitionId)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}