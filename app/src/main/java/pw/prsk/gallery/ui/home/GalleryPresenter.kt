package pw.prsk.gallery.ui.home

import android.Manifest
import android.content.pm.PackageManager
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
            val context = (view as GalleryFragment).context
            photosProvider = PhotosProvider(context?.contentResolver!!)
        }
        photosProvider?.loadPhotos()
    }

    fun checkPermissions() {
        val fragment = view as GalleryFragment
        val context = fragment.context
        val permssionState = context?.checkSelfPermission(READ_STORAGE_PERM)
        when (permssionState) {
            PackageManager.PERMISSION_DENIED -> {
                val canRequestPermission = fragment.shouldShowRequestPermissionRationale(READ_STORAGE_PERM)
                if (canRequestPermission) {
                    requestPermission()
                }
                view?.showPermissionNotGrantedMessage(true)
            }
            PackageManager.PERMISSION_GRANTED -> {
                loadPhotos()
            }
        }
    }

    private fun requestPermission() {
        val fragment = view as GalleryFragment
        fragment.requestPermissions(arrayOf(READ_STORAGE_PERM), PERMISSION_REQUEST_CODE)
    }

    fun requestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    view?.showPermissionNotGrantedMessage(false)
                    loadPhotos()
                } else {
                    view?.showPermissionNotGrantedMessage(true)
                }
                return
            }
            else -> return
        }
    }

    companion object {
        private const val READ_STORAGE_PERM = Manifest.permission.READ_EXTERNAL_STORAGE

        private const val PERMISSION_REQUEST_CODE = 101
    }
}