package pw.prsk.gallery.ui.home

interface NewsViewInterface {
    fun onDataUpdated(news: List<News>)
    fun showToast(resId: Int)
    fun showToast(string: String)
}