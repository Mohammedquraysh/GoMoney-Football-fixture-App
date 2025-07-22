package com.presentation.ui.league

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gomoneyfootballfixtureassessment.databinding.FragmentLeagueTableBinding
import com.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeagueTableFragment : Fragment() {
    private var _binding: FragmentLeagueTableBinding? = null
    private val binding get() = _binding!!


    private val viewModel: LeagueTableViewModel by viewModels()
    private lateinit var tableAdapter: LeagueTableAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLeagueTableBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()

        arguments?.getString(ARG_COMPETITION_ID)?.let { competitionId ->
            viewModel.loadStandings(competitionId)
        }
    }

    private fun setupRecyclerView() {
        tableAdapter = LeagueTableAdapter { team -> }

        binding.recyclerViewTable.apply {
            adapter = tableAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    private fun observeViewModel() {
        viewModel.standings.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.textError.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textError.visibility = View.GONE
                    resource.data?.let { standings ->
                        tableAdapter.submitList(standings)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textError.visibility = View.VISIBLE
                    binding.textError.text = resource.message
                }
            }
        }
    }

    companion object {
        private const val ARG_COMPETITION_ID = "competition_id"

        fun newInstance(competitionId: String): LeagueTableFragment {
            return LeagueTableFragment().apply {
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