package pw.prsk.gallery.data.shortcuts

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShortcutData(
    val action: String,
    val iconResource: Int,
    val shortLabelResource: Int,
    val longLabelResource: Int
) : Parcelable