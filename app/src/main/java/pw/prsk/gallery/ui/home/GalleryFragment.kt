package pw.prsk.gallery.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        presenter.loadPhotos()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        galleryContainer = null
    }

    override fun loadPhotos(count: Int) {
        TODO("Not yet implemented")
    }
}