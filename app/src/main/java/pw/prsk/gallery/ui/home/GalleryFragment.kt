package pw.prsk.gallery.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pw.prsk.gallery.R

class GalleryFragment: Fragment(), GalleryViewInterface {
    private val presenter: GalleryPresenter = GalleryPresenter()
    private var galleryContainer: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        with(view) {
            galleryContainer = findViewById(R.id.rvGallery)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        galleryContainer?.apply {
            adapter = GalleryAdapter()
            layoutManager = GridLayoutManager(this.context, 3)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        presenter.attachView(this)
        presenter.checkPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        galleryContainer = null
    }

    override fun showPermissionNotGrantedMessage(show: Boolean) {
        if (show) {
            showToast("Permission not granted.")
        } else {

        }
    }

    override fun showToast(resId: Int) {
        Toast.makeText(this.context, resId, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(str: String) {
        Toast.makeText(this.context, str, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        presenter.requestPermissionsResult(requestCode, permissions, grantResults)
    }
}