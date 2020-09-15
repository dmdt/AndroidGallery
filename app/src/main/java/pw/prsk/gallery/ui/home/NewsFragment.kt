package pw.prsk.gallery.ui.home

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pw.prsk.gallery.R

class NewsFragment: Fragment(), NewsViewInterface {
    private val presenter = NewsPresenter()
    private var newsContainer: RecyclerView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        with(view) {
            newsContainer = findViewById(R.id.rvNewsContainer)
            progressBar = findViewById(R.id.pbNews)
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        newsContainer?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = NewsAdapter()
        }
        presenter.initNewsList()
    }

    private fun init() {
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        newsContainer = null
    }

    override fun onDataUpdated(news: List<News>) {
        (newsContainer?.adapter as NewsAdapter?)?.setData(news)
    }

    override fun showToast(resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar(show: Boolean) {
        if (show) {
            progressBar?.visibility = View.VISIBLE
        } else {
            progressBar?.visibility = View.GONE
        }
    }
}