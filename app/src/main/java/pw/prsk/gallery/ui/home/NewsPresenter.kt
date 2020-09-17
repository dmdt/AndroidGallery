package pw.prsk.gallery.ui.home

import kotlinx.coroutines.*
import pw.prsk.gallery.data.NewsRepository

class NewsPresenter {
    private var view: NewsViewInterface? = null
    private val newsRepository = NewsRepository()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun attachView(view: NewsViewInterface) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun initNewsList() {
        view?.showProgressBar(true)
        scope.launch {
            val news = newsRepository.loadNews(5)
            view?.showProgressBar(false)
            view?.onDataUpdated(news)
        }
    }

    fun loadNews() {
        view?.showProgressBar(true)
        scope.launch {
            val news = newsRepository.loadNews(5)
            view?.showProgressBar(false)
            view?.onDataUpdated(news)
        }
    }

    fun reloadNews() {
        view?.clearNewsList()
        loadNews()
    }
}