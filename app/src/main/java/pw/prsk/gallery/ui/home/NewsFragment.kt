package pw.prsk.gallery.ui.home

import android.os.Bundle
import android.view.*
import pw.prsk.gallery.ui.home.MainActivity.MainTabs
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import pw.prsk.gallery.R

class NewsFragment : Fragment(), NewsViewInterface {
    private val presenter = NewsPresenter()
    private var newsContainer: RecyclerView? = null
    private var refreshLayout: SwipeRefreshLayout? = null
    private lateinit var scrollListener: RecyclerView.OnScrollListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        with(view) {
            newsContainer = findViewById(R.id.rvNewsContainer)
            refreshLayout = findViewById(R.id.srlNewsRefresh)
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
            addOnScrollListener(scrollListener)
        }
        refreshLayout?.setOnRefreshListener {
            presenter.reloadNews()
        }
        presenter.initNewsList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.miSettings -> {
            showToast("Settings not implemented.")
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun init() {
        presenter.attachView(this)
        setHasOptionsMenu(true)
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val adapter = recyclerView.adapter as NewsAdapter
                val itemCount = adapter.itemCount
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (itemCount > 0) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == itemCount - 1 && !adapter.dataLoading) {
                        adapter.dataLoading = true
                        recyclerView.removeOnScrollListener(scrollListener)
                        presenter.loadNews()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        newsContainer = null
        refreshLayout = null
    }

    override fun onDataUpdated(news: List<News>) {
        val adapter = newsContainer?.adapter as NewsAdapter
        adapter.addListItems(news)
        val activity: MainActivity = activity as MainActivity
        activity.updateTabBadge(MainTabs.TAB_NEWS, adapter.itemCount)
        adapter.dataLoading = false
        newsContainer?.addOnScrollListener(scrollListener)
    }

    override fun showToast(resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar(show: Boolean) {
        refreshLayout?.isRefreshing = show
    }

    override fun clearNewsList() {
        val adapter = newsContainer?.adapter as NewsAdapter
        adapter.clearList()
    }
}