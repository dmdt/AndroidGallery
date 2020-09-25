package pw.prsk.gallery.utils

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.os.PersistableBundle
import android.util.Log
import androidx.annotation.RequiresApi
import pw.prsk.gallery.R
import pw.prsk.gallery.data.shortcuts.ShortcutData
import pw.prsk.gallery.ui.home.MainActivity
import pw.prsk.gallery.ui.preferences.SettingsActivity

private const val DEBUG_TAG = "ShortcutsHelper"

@RequiresApi(Build.VERSION_CODES.N_MR1)
class ShortcutsHelper(private val context: Context) {
    val shortcutsReferences: HashMap<String, ShortcutData> = hashMapOf()

    init {
        shortcutsReferences.put(
            SHORTCUT_ID_NEWS, ShortcutData(
                MainActivity.ACTION_OPEN_NEWS,
                R.drawable.ic_news,
                R.string.shortcut_news_short,
                R.string.shortcut_news_long
            )
        )

        shortcutsReferences.put(
            SHORTCUT_ID_SETTINGS, ShortcutData(
                MainActivity.ACTION_OPEN_SETTINGS,
                R.drawable.ic_settings,
                R.string.shortcut_settings_short,
                R.string.shortcut_settings_long
            )
        )
    }

    private val shortcutManager: ShortcutManager =
        context.getSystemService(ShortcutManager::class.java)

    fun reportShortcutUsed(id: String) {
        shortcutManager.reportShortcutUsed(id)
    }

    fun restoreDynamicShortcuts() {
        if (shortcutManager.dynamicShortcuts.size == 0) {
            val newsShortcut = createShortcut(
                SHORTCUT_ID_NEWS,
                shortcutsReferences.get(SHORTCUT_ID_NEWS)!!
            )
            val settingsShortcut = createShortcut(
                SHORTCUT_ID_SETTINGS,
                shortcutsReferences.get(SHORTCUT_ID_SETTINGS)!!
            )
            shortcutManager.dynamicShortcuts = listOf(newsShortcut, settingsShortcut)
        }
    }

    fun refreshShortcuts(force: Boolean = false) {
        val currentTime = System.currentTimeMillis()
        val timeThreshold = if (force) {
            currentTime
        } else {
            currentTime - REFRESH_INTERVAL
        }

        val updateList = ArrayList<ShortcutInfo>()

        for (shortcut in getShortcuts()) {
            val shortcutExtra = shortcut.extras

            if (shortcutExtra != null) {
                if (shortcutExtra.getLong(LAST_REFRESH_FIELD) >= timeThreshold) {
                    continue
                }
                Log.i(DEBUG_TAG, "Refreshing shortcut \"${shortcut.id}\".")
                val shortcutData = ShortcutData(
                    shortcutExtra.getString(BUNDLE_ACTION_KEY, Intent.ACTION_DEFAULT),
                    shortcutExtra.getInt(BUNDLE_ICON_KEY),
                    shortcutExtra.getInt(BUNDLE_SHORT_LABEL_KEY),
                    shortcutExtra.getInt(BUNDLE_LONG_LABEL_KEY)
                )
                updateList.add(createShortcut(shortcut.id, shortcutData))
            }
        }

        if (updateList.size > 0) {
            shortcutManager.updateShortcuts(updateList)
        }
    }

    private fun getShortcuts(): List<ShortcutInfo> {
        val shortcuts = ArrayList<ShortcutInfo>()
        val shortcutIds = HashSet<String>()

        for (shortcut in shortcutManager.dynamicShortcuts) {
            if (!shortcut.isImmutable) {
                shortcuts.add(shortcut)
                shortcutIds.add(shortcut.id)
            }
        }

        for (shortcut in shortcutManager.pinnedShortcuts) {
            if (!shortcut.isImmutable && !shortcutIds.contains(shortcut.id)) {
                shortcuts.add(shortcut)
                shortcutIds.add(shortcut.id)
            }
        }

        return shortcuts
    }

    private fun createShortcut(
        shortcutId: String,
        data: ShortcutData
    ): ShortcutInfo {
        val intent = Intent(context, MainActivity::class.java).apply {
            this.action = data.action
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP + Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val shortcutBuilder = ShortcutInfo.Builder(context, shortcutId)
            .setShortLabel(context.resources.getString(data.shortLabelResource))
            .setLongLabel(context.resources.getString(data.longLabelResource))
            .setIcon(Icon.createWithResource(context, data.iconResource))
            .setIntent(intent)

        return setShortcutExtra(shortcutBuilder, data).build()
    }

    private fun setShortcutExtra(
        shortcutBuilder: ShortcutInfo.Builder,
        data: ShortcutData
    ): ShortcutInfo.Builder {
        val extra: PersistableBundle = PersistableBundle().apply {
            putLong(LAST_REFRESH_FIELD, System.currentTimeMillis())
            putString(BUNDLE_ACTION_KEY, data.action)
            putInt(BUNDLE_ICON_KEY, data.iconResource)
            putInt(BUNDLE_SHORT_LABEL_KEY, data.shortLabelResource)
            putInt(BUNDLE_LONG_LABEL_KEY, data.longLabelResource)
        }
        shortcutBuilder.setExtras(extra)

        return shortcutBuilder
    }

    companion object {
        private const val LAST_REFRESH_FIELD = "pw.prsk.gallery.shortcuts_last_refresh"
        private const val REFRESH_INTERVAL = 60 * 60 * 1000

        //        Shortcut ids
        private const val SHORTCUT_ID_NEWS = "news"
        private const val SHORTCUT_ID_SETTINGS = "settings"
        //        Persistable Bundle keys
        private const val BUNDLE_ACTION_KEY = "bundle.action"
        private const val BUNDLE_ICON_KEY = "bundle.icon"
        private const val BUNDLE_SHORT_LABEL_KEY = "bundle.short_label"
        private const val BUNDLE_LONG_LABEL_KEY = "bundle.long_label"
    }
}