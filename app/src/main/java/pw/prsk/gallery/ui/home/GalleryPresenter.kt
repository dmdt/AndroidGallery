package pw.prsk.gallery.ui.home

import android.Manifest
import android.content.pm.PackageManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import pw.prsk.gallery.data.PhotosProvider

class GalleryPresenter {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private var view: GalleryViewInterface? = null
    private var photosProvider: PhotosProvider? = null

    fun attachView(view: GalleryViewInterface) {
        this.view = view

        val context = (view as GalleryFragment).context
        photosProvider = PhotosProvider(context?.applicationContext?.contentResolver!!)
    }

    fun detachView() {
        this.view = null
        photosProvider = null
    }

    private fun loadPhotos() {
        scope.launch {
            val list = photosProvider?.loadPhotos() ?: mutableListOf()
            view?.initDataset(list)
        }
    }

    fun checkPermissions() {
        val fragment = view as GalleryFragment
        val context = fragment.context
        val permssionState = context?.checkSelfPermission(READ_STORAGE_PERM)
        when (permssionState) {
            PackageManager.PERMISSION_DENIED -> {
                view?.showPermissionRationale(true)
                requestPermission()
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
                    view?.showPermissionRationale(false)
                    loadPhotos()
                } else {
                    view?.showPermissionRationale(true)
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