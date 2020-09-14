package pw.prsk.gallery.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class PicturesRepository {
    suspend fun getPicture(url: String): Bitmap {
        val response = withContext(Dispatchers.IO) {
            val client: OkHttpClient = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).execute()
        }
        return withContext(Dispatchers.Default) {
            BitmapFactory.decodeStream(response.body?.byteStream())
        }
    }
}