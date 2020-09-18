package pw.prsk.gallery.ui.home

data class News (
    val author: String,
    val date: String,
    val title: String,
    val imageUrl: String = "https://picsum.photos/640/480"
)