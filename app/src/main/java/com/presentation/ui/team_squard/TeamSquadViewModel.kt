package com.presentation.ui.team_squard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.remote.dto.teamdto.TeamDetails
import com.domain.repository.FootballRepository
import com.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamSquadViewModel @Inject constructor(
    private val footballRepository: FootballRepository
) : ViewModel() {

    private val _teamDetails = MutableLiveData<Resource<TeamDetails>>()
    val teamDetails: LiveData<Resource<TeamDetails>> = _teamDetails

    fun loadTeamDetails(teamId: Int) {
        viewModelScope.launch {
            footballRepository.getTeamDetails(teamId).collect { resource ->
                _teamDetails.value = resource
            }
        }
    }
}