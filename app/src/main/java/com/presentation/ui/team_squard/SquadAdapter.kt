package com.presentation.ui.team_squard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.data.remote.dto.teamdto.Player
import com.example.gomoneyfootballfixtureassessment.databinding.ItemSquadPlayerBinding

class SquadAdapter : ListAdapter<Player, SquadAdapter.SquadViewHolder>(SquadDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquadViewHolder {
        val binding = ItemSquadPlayerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SquadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SquadViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SquadViewHolder(
        private val binding: ItemSquadPlayerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(player: Player) {
            with(binding) {
                textJerseyNumber.text = player.jerseyNumber?.toString() ?: "-"
                textPlayerName.text = player.name
                textPosition.text = formatPosition(player.position)
            }
        }

        private fun formatPosition(position: String?): String {
            return when (position) {
                "Goalkeeper" -> "Keeper"
                "Centre-Back" -> "Centre-Back"
                "Left-Back" -> "Left-Back"
                "Right-Back" -> "Right-Back"
                "Defensive Midfield" -> "Defensive Midfield"
                "Central Midfield" -> "Central Midfield"
                "Attacking Midfield" -> "Attacking Midfield"
                "Left Midfield" -> "Left Midfield"
                "Right Midfield" -> "Right Midfield"
                "Left Winger" -> "Left Wing"
                "Right Winger" -> "Right Wing"
                "Centre-Forward" -> "Centre-Forward"
                else -> position ?: "Unknown"
            }
        }
    }

    class SquadDiffCallback : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }
    }
}