package pw.prsk.gallery.data

import android.content.ContentResolver
import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val DEBUG_TAG = "PhotosProvider"

class PhotosProvider(var contentResolver: ContentResolver?) {
    suspend fun loadPhotos(): MutableList<Photo> {
        val list: MutableList<Photo> = mutableListOf()
        withContext(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.Images.Media._ID
            )

            val query = contentResolver?.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED + " DESC"
            )
            query?.use { cursor ->
                if (cursor.count != 0) {
                    Log.i(DEBUG_TAG, "Photos found: ${cursor.count}")
                    val idIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                    while (cursor.moveToNext()) {
                        val id = cursor.getLong(idIndex)

                        val contentUri: Uri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id
                        )
                        val photo = Photo(id, contentUri)
                        list.add(photo)
                    }
                }
            }
        }
        return list
    }

    suspend fun loadThumbnail(photo: Photo): Bitmap {
        return withContext(Dispatchers.IO) {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ->
                    contentResolver!!.loadThumbnail(photo.uri, Size(150, 150), null)
                else ->
                    MediaStore.Images.Thumbnails.getThumbnail(
                        contentResolver,
                        photo.id,
                        MediaStore.Images.Thumbnails.MICRO_KIND,
                        null
                    )
            }
        }
    }
}