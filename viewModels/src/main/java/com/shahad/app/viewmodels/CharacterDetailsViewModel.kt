package com.shahad.app.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahad.app.core.Constants
import com.shahad.app.core.DetailsScreenState
import com.shahad.app.core.models.Character
import com.shahad.app.core.models.Story
import com.shahad.app.usecases.GetCharacterDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDetails: GetCharacterDetails,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val character = MutableLiveData<DetailsScreenState<Character?>>()
    val stories = MutableLiveData<DetailsScreenState<List<Story>?>>()

    fun getDetails(characterId: Long){
        viewModelScope.launch {
            getCharacterDetails.getCharacter(characterId).collect{
                character.value = it
            }
        }
    }

    fun getStories(characterId: Long) {
        viewModelScope.launch {
            getCharacterDetails.getCharacterSeries(characterId).collect{
                stories.value = it
            }
        }
    }
    init {
        Log.i("RRR",savedStateHandle.get<Long>(Constants.ID_KEY).toString())
    }

}