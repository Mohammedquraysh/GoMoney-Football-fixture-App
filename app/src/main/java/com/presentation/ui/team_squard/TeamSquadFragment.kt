package com.presentation.ui.team_squard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.data.remote.dto.teamdto.TeamDetails
import com.example.gomoneyfootballfixtureassessment.R
import com.example.gomoneyfootballfixtureassessment.databinding.FragmentTeamSquadBinding
import com.presentation.ui.teams.TeamsFragment
import com.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamSquadFragment : Fragment() {
    private var _binding: FragmentTeamSquadBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TeamSquadViewModel by viewModels()
    private lateinit var squadAdapter: SquadAdapter
    private val args: TeamSquadFragmentArgs by navArgs()
    private var teamId: String? = null
    private var teamName: String? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamSquadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        observeViewModel()
        loadSquad()

        arguments?.let {
            teamId = it.getString("teamId")
            teamName = it.getString("teamName")
        }
    }

    private fun setupToolbar() {
        with(binding) {
            textTeamName.text = teamName
            btnClose.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupRecyclerView() {
        squadAdapter = SquadAdapter()
        binding.recyclerViewSquad.apply {
            adapter = squadAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.teamDetails.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.textError.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textError.visibility = View.GONE
                    resource.data?.let { team ->
                        bindTeamData(team)
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

    private fun bindTeamData(team: TeamDetails) {
        with(binding) {
            Glide.with(requireContext())
                .load(team.crest)
                .placeholder(R.drawable.ic_team_placeholder)
                .error(R.drawable.ic_team_placeholder)
                .into(imageTeamLogo)

            squadAdapter.submitList(team.squad)
            textTeamName.text = team.name
        }
    }

    private fun loadSquad() {
        viewModel.loadTeamDetails(args.teamId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}