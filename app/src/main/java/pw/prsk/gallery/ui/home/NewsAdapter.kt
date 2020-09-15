package pw.prsk.gallery.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import kotlinx.coroutines.*
import pw.prsk.gallery.R
import com.squareup.picasso.Picasso

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private var news: List<News>? = null

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
        holder.tvAuthor.text = news?.get(position)?.author ?: "Error"
        holder.tvDate.text = news?.get(position)?.date ?: "Error"
        holder.tvTitle.text = news?.get(position)?.title ?: "Error"
        Picasso.get()
            .load(news?.get(position)?.imageUrl)
            .noPlaceholder()
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .fit()
            .centerCrop()
            .into(holder.ivImage)
    }

    override fun getItemCount(): Int = news?.size ?: 0

    fun setData(news: List<News>) {
        val currentListSize = this.news?.size ?: 0
        this.news = news
        notifyItemRangeInserted(currentListSize, news.size)
    }
}