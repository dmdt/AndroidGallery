package pw.prsk.gallery.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import pw.prsk.gallery.ui.home.News

class NewsRepository {
    private val news: MutableList<News> = mutableListOf()
    private val pictureRep = PicturesRepository()

    fun getNewsList(): List<News> = news

    suspend fun loadNews(count: Int) {
        repeat(count) {
            val article = News(
                "Dmitriy",
                "Today",
                loadTitle()
            )
            article.image = pictureRep.getPicture(article.imageUrl)
            news.add(article)
        }
    }

    private suspend fun loadTitle(): String = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val req = Request.Builder().url("https://api.kanye.rest/").build()
        val response = client.newCall(req).execute()
        val gson = Gson()
        val res = gson.fromJson(response.body?.string(), JsonObject::class.java)
        Log.i("Parsed_quote", res.get("quote").asString)
        res.get("quote").asString
    }
}