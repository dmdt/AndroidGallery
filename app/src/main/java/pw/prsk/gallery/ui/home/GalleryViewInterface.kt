package pw.prsk.gallery.ui.home

import pw.prsk.gallery.data.Photo

interface GalleryViewInterface {
    fun showPermissionRationale(show: Boolean)
    fun showToast(resId: Int)
    fun showToast(str: String)
    fun initDataset(list: MutableList<Photo>)
}