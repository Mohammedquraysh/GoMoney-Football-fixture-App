package com.presentation.ui.league

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.remote.dto.teamdto.TeamStanding
import com.example.gomoneyfootballfixtureassessment.R
import com.example.gomoneyfootballfixtureassessment.databinding.ItemTableRowBinding

class LeagueTableAdapter(
    private val onTeamClick: (TeamStanding) -> Unit
) : ListAdapter<TeamStanding, LeagueTableAdapter.TableViewHolder>(TableDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        val binding = ItemTableRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TableViewHolder(
        private val binding: ItemTableRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(teamStanding: TeamStanding) {
            with(binding) {
                textPosition.text = teamStanding.position.toString()
                textTeamName.text = teamStanding.team.shortName ?: teamStanding.team.name
                textMatches.text = teamStanding.playedGames.toString()
                textGoalDiff.text = if (teamStanding.goalDifference >= 0) {
                    "+${teamStanding.goalDifference}"
                } else {
                    teamStanding.goalDifference.toString()
                }
                textPoints.text = teamStanding.points.toString()


                Glide.with(itemView.context)
                    .load(teamStanding.team.crest)
                    .placeholder(R.drawable.ic_team_placeholder)
                    .error(R.drawable.ic_team_placeholder)
                    .into(imageTeamLogo)

                /** Set position indicator color **/
                val indicatorColor = when (teamStanding.position) {
                    in 1..4 -> R.color.champions_league_color
                    in 5..6 -> R.color.europa_league_color
                    in 18..20 -> R.color.relegation_color
                    else -> R.color.transparent
                }
                positionIndicator.setBackgroundColor(
                    ContextCompat.getColor(itemView.context, indicatorColor)
                )

                root.setOnClickListener {
                    onTeamClick(teamStanding)
                }
            }
        }
    }

    class TableDiffCallback : DiffUtil.ItemCallback<TeamStanding>() {
        override fun areItemsTheSame(oldItem: TeamStanding, newItem: TeamStanding): Boolean {
            return oldItem.team.id == newItem.team.id
        }

        override fun areContentsTheSame(oldItem: TeamStanding, newItem: TeamStanding): Boolean {
            return oldItem == newItem
        }
    }
}