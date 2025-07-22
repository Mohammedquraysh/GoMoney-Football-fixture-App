package com.presentation.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.domain.model.Competition
import com.example.gomoneyfootballfixtureassessment.databinding.FragmentCompetitionsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.presentation.adapter.CompetitionsBottomSheetAdapter
import com.presentation.ui.fixtures.FixturesViewModel
import com.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompetitionsBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCompetitionsBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var competitionsAdapter: CompetitionsBottomSheetAdapter
    private val viewModel: FixturesViewModel by viewModels()
    private var listener: OnCompetitionSelectedListener? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompetitionsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        loadCompetitions()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        competitionsAdapter = CompetitionsBottomSheetAdapter { competition ->
            listener?.onCompetitionSelected(competition)
            dismiss()
        }

        binding.recyclerViewCompetitions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = competitionsAdapter
        }
    }

    private fun setupClickListeners() {
        binding.imageViewClose.setOnClickListener {
            dismiss()
        }
    }

    private fun observeViewModel() {
        viewModel.competitions.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.textViewError.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textViewError.visibility = View.GONE
                    resource.data?.let { competitions ->
                        competitionsAdapter.submitList(competitions)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textViewError.visibility = View.VISIBLE
                    binding.textViewError.text = resource.message
                }
            }
        }
    }


    private fun loadCompetitions() {
        binding.progressBar.visibility = View.VISIBLE
        binding.textViewError.visibility = View.GONE
        viewModel.loadCompetitions()
    }

    /** Callback interface for selection **/
    interface OnCompetitionSelectedListener {
        fun onCompetitionSelected(competition: Competition?)
    }

    companion object {
        fun newInstance(listener: OnCompetitionSelectedListener): CompetitionsBottomSheetFragment {
            return CompetitionsBottomSheetFragment().apply {
                this.listener = listener
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}