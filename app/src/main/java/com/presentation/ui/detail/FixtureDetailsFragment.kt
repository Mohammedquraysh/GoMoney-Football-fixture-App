package com.presentation.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.data.remote.dto.fixturedto.MatchDto
import com.domain.model.Fixture
import com.example.gomoneyfootballfixtureassessment.R
import com.example.gomoneyfootballfixtureassessment.databinding.FragmentFixtureDetailsBinding
import com.util.Resource
import com.util.toFormattedDate
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FixtureDetailsFragment : Fragment() {

    private var _binding: FragmentFixtureDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FixtureDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFixtureDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: FixtureDetailsFragmentArgs by navArgs()
        val matchId = args.fixtureId
        observeViewModel()
        viewModel.loadFixtureDetail(matchId)
        setupToolbar()
    }


    private fun setupToolbar() {
        with(binding) {

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
    private fun observeViewModel() {
        viewModel.fixture.observe(viewLifecycleOwner) { resource ->
            Log.e("MQ", "observeViewModel: get ${resource.data} ", )
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBarDetail.visibility = View.VISIBLE
                    binding.cardMatchInfo.visibility = View.GONE
                    binding.cardMatchDetails.visibility = View.GONE
                    binding.textViewErrorDetail.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBarDetail.visibility = View.GONE
                    binding.cardMatchInfo.visibility = View.VISIBLE
                    binding.cardMatchDetails.visibility = View.VISIBLE
                    binding.textViewErrorDetail.visibility = View.GONE
                    resource.data?.let { bindFixtureData(it) }
                }
                is Resource.Error -> {
                    binding.progressBarDetail.visibility = View.GONE
                    binding.cardMatchInfo.visibility = View.GONE
                    binding.cardMatchDetails.visibility = View.GONE
                    binding.textViewErrorDetail.visibility = View.VISIBLE
                    binding.textViewErrorDetail.text = resource.message
                }
            }
        }
    }

    private fun bindFixtureData(fixture: MatchDto) {
        binding.apply {
            textViewHomeTeamDetail.text = fixture.homeTeam.name
            textViewAwayTeamDetail.text = fixture.awayTeam.name
            textViewMatchStatus.text = fixture.status
            textViewMatchDate.text = fixture.utcDate.toFormattedDate()
            textViewCompetitionDetail.text = "Competition: ${fixture.competition.name}"

            // Load team crests
            Glide.with(imageViewHomeCrestDetail.context)
                .load(fixture.homeTeam.crest)
                .placeholder(R.drawable.ic_soccer_ball)
                .error(R.drawable.ic_soccer_ball)
                .into(imageViewHomeCrestDetail)

            Glide.with(imageViewAwayCrestDetail.context)
                .load(fixture.awayTeam.crest)
                .placeholder(R.drawable.ic_soccer_ball)
                .error(R.drawable.ic_soccer_ball)
                .into(imageViewAwayCrestDetail)

            // Show score if match is finished
            if (fixture.status == "FINISHED" && fixture.score != null) {
                textViewScoreDetail.text = "${fixture.score.fullTime.home} - ${fixture.score.fullTime.away}"
                textViewScoreDetail.visibility = View.VISIBLE
            } else {
                textViewScoreDetail.visibility = View.GONE
            }

            // Show additional details
            if (fixture.matchday != null) {
                textViewMatchdayDetail.text = "Matchday: ${fixture.matchday}"
                textViewMatchdayDetail.visibility = View.VISIBLE
            } else {
                textViewMatchdayDetail.visibility = View.GONE
            }

            if (fixture.stage != null) {
                textViewStageDetail.text = "Stage: ${fixture.stage}"
                textViewStageDetail.visibility = View.VISIBLE
            } else {
                textViewStageDetail.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}