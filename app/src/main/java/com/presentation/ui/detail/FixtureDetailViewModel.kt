package com.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.remote.dto.fixturedto.MatchDto
import com.domain.repository.FixtureRepository
import com.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixtureDetailViewModel @Inject constructor(
    private val fixtureRepository: FixtureRepository,
) : ViewModel() {


    private val _fixture = MutableLiveData<Resource<MatchDto>>()
    val fixture: LiveData<Resource<MatchDto>> = _fixture



     fun loadFixtureDetail(id: Int) {
        viewModelScope.launch {
            fixtureRepository.getMatchById(id).collect { resource ->
                _fixture.value = resource
            }
        }
    }

}
