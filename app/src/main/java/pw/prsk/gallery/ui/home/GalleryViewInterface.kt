package pw.prsk.gallery.ui.home

interface GalleryViewInterface {
    fun showPermissionNotGrantedMessage(show: Boolean)
    fun showToast(resId: Int)
    fun showToast(str: String)
}