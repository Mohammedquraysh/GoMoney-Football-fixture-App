package com.presentation.ui.fixtures

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.domain.model.Fixture
import com.example.gomoneyfootballfixtureassessment.R
import com.example.gomoneyfootballfixtureassessment.databinding.ItemFixtureBinding
import com.util.toFormattedDate

class FixturesAdapter(
    private val onItemClick: (Fixture) -> Unit
) : ListAdapter<Fixture, FixturesAdapter.FixtureViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixtureViewHolder {
        val binding = ItemFixtureBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FixtureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FixtureViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FixtureViewHolder(
        private val binding: ItemFixtureBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(fixture: Fixture) {
            binding.apply {
                textViewHomeTeam.text = fixture.homeTeam
                textViewAwayTeam.text = fixture.awayTeam
                textViewDateTime.text = fixture.utcDate.toFormattedDate()
                textViewStatus.text = fixture.status
                textViewCompetition.text = fixture.competition

                // Load team crests
                Glide.with(imageViewHomeCrest.context)
                    .load(fixture.homeTeamCrest)
                    .placeholder(R.drawable.ic_soccer_ball)
                    .error(R.drawable.ic_soccer_ball)
                    .into(imageViewHomeCrest)

                Glide.with(imageViewAwayCrest.context)
                    .load(fixture.awayTeamCrest)
                    .placeholder(R.drawable.ic_soccer_ball)
                    .error(R.drawable.ic_soccer_ball)
                    .into(imageViewAwayCrest)

                // Show score if match is finished
                if (fixture.status == "FINISHED" && fixture.homeScore != null && fixture.awayScore != null) {
                    textViewScore.text = "${fixture.homeScore} - ${fixture.awayScore}"
                    textViewScore.visibility = View.VISIBLE
                } else {
                    textViewScore.visibility = View.GONE
                }

                root.setOnClickListener {
                    onItemClick(fixture)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Fixture>() {
        override fun areItemsTheSame(oldItem: Fixture, newItem: Fixture): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Fixture, newItem: Fixture): Boolean {
            return oldItem == newItem
        }
    }
}