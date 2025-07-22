package com.presentation.ui.teams

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gomoneyfootballfixtureassessment.R
import com.example.gomoneyfootballfixtureassessment.databinding.FragmentTeamsBinding
import com.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamsFragment : Fragment() {
    private var _binding: FragmentTeamsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TeamsViewModel by viewModels()
    private lateinit var teamsAdapter: TeamsAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()

        arguments?.getString(ARG_COMPETITION_ID)?.let { competitionId ->
            viewModel.loadTeams(competitionId)
        }
    }


    private fun setupRecyclerView() {
        teamsAdapter = TeamsAdapter { team ->


            Log.e("MQ", "setupRecyclerView: ${team.name}", )
            findNavController().navigate(
                R.id.action_competitionMainFragment_to_teamSquadFragment,
                bundleOf(
                    "teamId" to team.id,
                    "teamName" to team.name
                )
            )
        }

        binding.recyclerViewTeams.apply {
            adapter = teamsAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private fun observeViewModel() {
        viewModel.teams.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.textError.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textError.visibility = View.GONE
                    resource.data?.let { teams ->
                        teamsAdapter.submitList(teams)
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

        fun newInstance(competitionId: String): TeamsFragment {
            return TeamsFragment().apply {
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