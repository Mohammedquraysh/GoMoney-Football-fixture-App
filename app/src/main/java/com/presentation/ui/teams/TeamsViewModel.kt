package com.presentation.ui.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.remote.dto.teamdto.Team
import com.domain.repository.FootballRepository
import com.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val footballRepository: FootballRepository
) : ViewModel() {

    private val _teams = MutableLiveData<Resource<List<Team>>>()
    val teams: LiveData<Resource<List<Team>>> = _teams

    fun loadTeams(competitionCode: String) {
        viewModelScope.launch {
            footballRepository.getTeams(competitionCode).collect { resource ->
                _teams.value = resource
            }
        }
    }
}