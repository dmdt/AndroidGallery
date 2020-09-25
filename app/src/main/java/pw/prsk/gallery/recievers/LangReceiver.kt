package pw.prsk.gallery.recievers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import pw.prsk.gallery.utils.ShortcutsHelper

class LangReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_LOCALE_CHANGED.equals(intent?.action)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                ShortcutsHelper(context!!).refreshShortcuts(true)
            }
        }
    }
}