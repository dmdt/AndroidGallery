package pw.prsk.gallery.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import pw.prsk.gallery.R

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private var news: List<News> = listOf()

    var dataLoading: Boolean = false

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAuthor: TextView = view.findViewById(R.id.tvNewsAuthor)
        val tvDate: TextView = view.findViewById(R.id.tvNewsDate)
        val tvTitle: TextView = view.findViewById(R.id.tvNewsTitle)
        val ivImage: ImageView = view.findViewById(R.id.ivNewsImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.tvAuthor.text = news[position].author
        holder.tvDate.text = news[position].date
        holder.tvTitle.text = news[position].title
        holder.ivImage.setImageBitmap(news[position].image)
    }

    override fun getItemCount(): Int = news.size

    fun setData(news: List<News>) {
        val currentListSize = this.news.size
        this.news = news
        notifyItemRangeInserted(currentListSize, news.size)
//        notifyDataSetChanged()
    }
}