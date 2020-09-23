package pw.prsk.gallery.data

import android.content.ContentResolver
import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotosProvider(var contentResolver: ContentResolver?) {
    suspend fun loadPhotos(): MutableList<Photo> {
        val list: MutableList<Photo> = mutableListOf()
        withContext(Dispatchers.IO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val projection = arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DATE_TAKEN,
                    MediaStore.Images.Media.ORIENTATION,
                    MediaStore.Images.Media.SIZE
                )

                val query = contentResolver?.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    MediaStore.Images.Media.DATE_TAKEN + " DESC"
                )
                query?.use { cursor ->
                    if (cursor.count != 0) {
                        Log.i("ASD", "${cursor.count}")
                        val idIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                        val sizeIndex = cursor.getColumnIndex(MediaStore.Images.Media.SIZE)
                        while (cursor.moveToNext()) {
                            val id = cursor.getLong(idIndex)
                            val size = cursor.getInt(sizeIndex)

                            val contentUri: Uri = ContentUris.withAppendedId(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                id
                            )
                            val photo = Photo(contentUri, size)
                            list.add(photo)
                        }
                    }
                }
            } else {
                val projection = arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.RELATIVE_PATH
                )
                //FIXME: Make photos get method for versions below Q
            }
        }
        return list
    }

    suspend fun loadThumbnail(path: Uri): Bitmap {
        return withContext(Dispatchers.IO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentResolver!!.loadThumbnail(path, Size(150, 150), null)
            } else {
                //FIXME: Make thumbnail get method for versions below Q
                BitmapFactory.decodeFile("TEMP")
            }
        }
    }
}