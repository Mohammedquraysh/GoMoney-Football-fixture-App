package com.presentation.ui.teams

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.remote.dto.teamdto.Team
import com.example.gomoneyfootballfixtureassessment.R
import com.example.gomoneyfootballfixtureassessment.databinding.ItemTeamGridBinding

class TeamsAdapter(
    private val onTeamClick: (Team) -> Unit
) : ListAdapter<Team, TeamsAdapter.TeamViewHolder>(TeamDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = ItemTeamGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TeamViewHolder(
        private val binding: ItemTeamGridBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(team: Team) {
            with(binding) {
                textTeamName.text = team.shortName ?: team.name

                // Load team logo
                Glide.with(itemView.context)
                    .load(team.crest)
                    .placeholder(R.drawable.ic_team_placeholder)
                    .error(R.drawable.ic_team_placeholder)
                    .into(imageTeamLogo)

                root.setOnClickListener {
                    onTeamClick(team)
                }
            }
        }
    }

    class TeamDiffCallback : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem == newItem
        }
    }
}