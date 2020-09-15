package pw.prsk.gallery.ui.home

import android.graphics.Bitmap

data class News (
    val author: String,
    val date: String,
    val title: String,
    var image: Bitmap? = null,
    val imageUrl: String = "https://picsum.photos/640/480"
)