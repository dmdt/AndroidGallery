package pw.prsk.gallery.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pw.prsk.gallery.R

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvAuthor: TextView = view.findViewById(R.id.tvNewsAuthor)
        val tvDate: TextView = view.findViewById(R.id.tvNewsDate)
        val tvTitle: TextView = view.findViewById(R.id.tvNewsTitle)
        val ivImage: ImageView = view.findViewById(R.id.ivNewsImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.tvAuthor.text = "ASDasd"
        holder.tvDate.text = "asdasdasd"
        holder.tvTitle.text = "$position Title title title title title title"
    }

    override fun getItemCount(): Int = 10
}