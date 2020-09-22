package pw.prsk.gallery.ui.home

import pw.prsk.gallery.data.PhotosProvider

class GalleryPresenter {
    private var view: GalleryViewInterface? = null
    private var photosProvider: PhotosProvider? = null

    fun attachView(view: GalleryViewInterface) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun loadPhotos() {
        if (photosProvider == null) {
            val v = view as GalleryFragment
            photosProvider = PhotosProvider(v.context?.contentResolver!!)
        }
        photosProvider?.loadPhotos()
    }
}