package com.example.kudago.ui.news

import android.util.Log
import androidx.lifecycle.*
import com.example.kudago.ApiStatus
import com.example.kudago.App
import com.example.kudago.NewsElem
import kotlinx.coroutines.launch

class NewsViewModel(val app: App) : ViewModel() {

    private val _news = MutableLiveData<List<NewsElem>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val news: LiveData<List<NewsElem>>
        get() = _news
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus>
        get() = _status
    private val _navigateToSelectedNews = MutableLiveData<NewsElem?>()
    val navigateToSelectedProperty: LiveData<NewsElem?>
        get() = _navigateToSelectedNews
    init {
        getMarsRealEstateProperties()
    }
    private fun getMarsRealEstateProperties() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _news.value = app.appComponent.retrofit().getNews().results
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.d("ERROR", e.message?:"")
                _status.value = ApiStatus.ERROR
                _news.value = ArrayList()
            }
        }
    }
    /**
     * When the property is clicked, set the [_navigateToSelectedNews] [MutableLiveData]
     * @param newsElem The [NewsData] that was clicked on.
     */
    fun displayPropertyDetails(newsElem: NewsElem) {
        _navigateToSelectedNews.value = newsElem
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedNews.value = null
    }

}
class MainViewModelFactory(
    private val application: App
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}