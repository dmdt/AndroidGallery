package pw.prsk.gallery.ui.home

import android.content.ContentResolver
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import pw.prsk.gallery.R
import pw.prsk.gallery.data.Photo
import pw.prsk.gallery.data.PhotosProvider
import java.io.FileNotFoundException

class GalleryAdapter(contentResolver: ContentResolver) :
    RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var photos: List<Photo>? = null
    private val provider: PhotosProvider = PhotosProvider(contentResolver)

    class ImageViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val ivPhoto: ImageView = v.findViewById(R.id.ivPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        coroutineScope.launch {
            var bm: Bitmap? = null
            try {
                bm = provider.loadThumbnail(photos?.get(position)!!.path)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            if (bm != null)
            holder.ivPhoto.setImageBitmap(bm)
        }
}

override fun getItemCount(): Int = photos?.size ?: 0

fun setList(list: MutableList<Photo>) {
    photos = list
    notifyDataSetChanged()
}
}