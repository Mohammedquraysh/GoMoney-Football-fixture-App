package com.presentation.ui.league

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.remote.dto.teamdto.TeamStanding
import com.domain.repository.FootballRepository
import com.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueTableViewModel @Inject constructor(
    private val footballRepository: FootballRepository
) : ViewModel() {

    private val _standings = MutableLiveData<Resource<List<TeamStanding>>>()
    val standings: LiveData<Resource<List<TeamStanding>>> = _standings

    fun loadStandings(competitionCode: String) {
        viewModelScope.launch {
            footballRepository.getStandings(competitionCode).collect { resource ->
                _standings.value = resource
            }
        }
    }
}
