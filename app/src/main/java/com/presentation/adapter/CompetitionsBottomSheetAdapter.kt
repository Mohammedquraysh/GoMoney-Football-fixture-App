package com.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.domain.model.Competition
import com.example.gomoneyfootballfixtureassessment.R
import com.example.gomoneyfootballfixtureassessment.databinding.ItemCompetitionBottomSheetBinding

class CompetitionsBottomSheetAdapter(
    private val onItemClick: (Competition?) -> Unit
) : ListAdapter<Competition, CompetitionsBottomSheetAdapter.CompetitionViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionViewHolder {
        val binding = ItemCompetitionBottomSheetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CompetitionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompetitionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CompetitionViewHolder(
        private val binding: ItemCompetitionBottomSheetBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(competition: Competition) {
            binding.textViewCompetitionName.text = competition.name

            // Handle "All Competitions" case
            if (competition.id == null) {
                binding.imageViewCompetition.setImageResource(R.drawable.ic_soccer_ball)
            } else {
                Glide.with(binding.imageViewCompetition.context)
                    .load(competition.emblem)
                    .transform(CircleCrop())
                    .placeholder(R.drawable.ic_soccer_ball)
                    .error(R.drawable.ic_soccer_ball)
                    .into(binding.imageViewCompetition)
            }

            binding.root.setOnClickListener {
                onItemClick(if (competition.id == null) null else competition)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Competition>() {
        override fun areItemsTheSame(oldItem: Competition, newItem: Competition): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Competition, newItem: Competition): Boolean {
            return oldItem == newItem
        }
    }
}