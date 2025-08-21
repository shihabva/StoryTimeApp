package com.example.storytimeadventures.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storytimeadventures.data.Story
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class StoryViewModel : ViewModel() {

    private val _stories = MutableStateFlow<List<Story>>(emptyList())
    val stories: StateFlow<List<Story>> = _stories

    fun loadStories(libraryUrl: String) {
        viewModelScope.launch {
            try {
                val jsonText = withContext(Dispatchers.IO) {
                    URL(libraryUrl).readText()
                }
                val storyListType = object : TypeToken<List<Story>>() {}.type
                _stories.value = Gson().fromJson(jsonText, storyListType)
            } catch (e: Exception) {
                // Handle error, e.g., show an error message
                e.printStackTrace()
            }
        }
    }
}
