package com.presentation.ui.fixtures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.model.Competition
import com.domain.model.Fixture
import com.domain.repository.CompetitionRepository
import com.domain.repository.FixtureRepository
import com.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixturesViewModel @Inject constructor(
    private val fixtureRepository: FixtureRepository,
    private val competitionRepository: CompetitionRepository
) : ViewModel() {

    private val _fixtures = MutableLiveData<Resource<List<Fixture>>>()
    val fixtures: LiveData<Resource<List<Fixture>>> = _fixtures

    private val _competitions = MutableLiveData<Resource<List<Competition>>>()
    val competitions: LiveData<Resource<List<Competition>>> = _competitions

    private val _selectedCompetition = MutableLiveData<Int?>()
    val selectedCompetition: LiveData<Int?> = _selectedCompetition

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _filteredFixtures = MutableLiveData<Resource<List<Fixture>>>()
    val filteredFixtures: LiveData<Resource<List<Fixture>>> = _filteredFixtures

    init {
        loadFixtures()
        loadCompetitions()
    }

    fun loadFixtures() {
        viewModelScope.launch {
            fixtureRepository.getFixtures().collect {
                _fixtures.value = it
            }
        }
    }

    fun loadCompetitions() {
        viewModelScope.launch {
            competitionRepository.getCompetitions().collect {
                _competitions.value = it
            }
        }
    }

    fun filterByCompetition(competitionId: Int?) {
        _selectedCompetition.value = competitionId
        viewModelScope.launch {
            if (competitionId == null) {
                fixtureRepository.getFixtures().collect {
                    _fixtures.value = it
                }
            } else {
                fixtureRepository.getFixturesByCompetition(competitionId).collect {
                    _fixtures.value = it
                }
            }
        }
    }


    fun filteredCompetitions(competitionId: Int?) {
        viewModelScope.launch {
            fixtureRepository.getFixturesByCompetition(competitionId!!.toInt()).collect {
                _filteredFixtures.value = it
            }
        }
    }


    fun refreshFixtures() {
        _isRefreshing.value = true
        viewModelScope.launch {
            try {
                fixtureRepository.refreshFixtures()
                if (_selectedCompetition.value != null) {
                    filterByCompetition(_selectedCompetition.value)
                } else {
                    loadFixtures()
                }
            } catch (e: Exception) {
                _fixtures.value = Resource.Error(e.message ?: "Refresh failed")
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}