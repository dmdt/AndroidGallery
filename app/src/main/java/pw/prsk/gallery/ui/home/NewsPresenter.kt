package pw.prsk.gallery.ui.home

import android.view.View

class NewsPresenter {
    var view: NewsViewInterface? = null

    fun attachView(view: NewsViewInterface) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}