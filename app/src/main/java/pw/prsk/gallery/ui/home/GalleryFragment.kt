package pw.prsk.gallery.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pw.prsk.gallery.R
import pw.prsk.gallery.data.Photo
import pw.prsk.gallery.data.PhotosProvider

class GalleryFragment: Fragment(), GalleryViewInterface {
    private val presenter: GalleryPresenter = GalleryPresenter()
    private var galleryContainer: RecyclerView? = null
    private var tvPermissionRationale: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        with(view) {
            galleryContainer = findViewById(R.id.rvGallery)
            tvPermissionRationale = findViewById(R.id.tvPermissionRationale)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    private fun init() {
        galleryContainer?.apply {
            adapter = GalleryAdapter()
            (adapter as GalleryAdapter).provider = PhotosProvider(this.context.contentResolver)
            layoutManager = GridLayoutManager(this.context, 3)
        }

        presenter.attachView(this)
        presenter.checkPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        val adapter = galleryContainer?.adapter as GalleryAdapter
        adapter.provider?.contentResolver = null
        galleryContainer = null
        presenter.detachView()
    }

    override fun showPermissionNotGrantedMessage(show: Boolean) {
        if (show) {
            galleryContainer?.visibility = View.GONE
            tvPermissionRationale?.visibility = View.VISIBLE
            showToast("Permission not granted.")
        } else {
            galleryContainer?.visibility = View.VISIBLE
            tvPermissionRationale?.visibility = View.GONE
        }
    }

    override fun showToast(resId: Int) {
        Toast.makeText(this.context, resId, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(str: String) {
        Toast.makeText(this.context, str, Toast.LENGTH_SHORT).show()
    }

    override fun initDataset(list: MutableList<Photo>) {
        val adapter = galleryContainer?.adapter as GalleryAdapter
        (activity as MainActivity).updateTabBadge(MainActivity.MainTabs.TAB_GALLERY, list.size)
        adapter.setList(list)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        presenter.requestPermissionsResult(requestCode, permissions, grantResults)
    }
}