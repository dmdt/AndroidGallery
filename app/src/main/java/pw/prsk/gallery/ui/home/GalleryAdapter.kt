package pw.prsk.gallery.ui.home

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import pw.prsk.gallery.R

class GalleryAdapter: RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {
    class ImageViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val ivPhoto: ImageView = v.findViewById(R.id.ivPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = 0
}