package com.example.assignment.ui.main.dashboard

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.models.DetailAPIResponse
import com.example.assignment.network.RetrofitConstants
import com.example.assignment.network.RetrofitServices
import com.example.assignment.ui.main.details.DashboardClickListener
import com.example.assignment.ui.main.details.DetailPageFragment
import com.example.assignment.util.ApplicationConstants
import com.example.assignment.util.CustomProgressDialog
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashBoardFragment : Fragment(), DetailResponseListener, DashboardClickListener {

    private var adapterSet: Boolean = false
    private var currentSize: Int = 0
    private lateinit var viewModel: DashboardViewModel
    private var detailedApiResponseArrayList = ArrayList<DetailAPIResponse?>()
    private var detailedApiUrls = ArrayList<String?>()
    private lateinit var dashboardDataAdapter :  DashBoardDataAdapter
    private lateinit var recyclerView :  RecyclerView
    private lateinit var customProgressDialog: CustomProgressDialog

    companion object {
        fun newInstance() = DashBoardFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.dashboard_fragment, container, false)
        initializeView(view)
        initScrollListener()
        return view
    }

    override fun onResume() {
        super.onResume()
        getAllHackersResponse()
    }

    private fun initScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null &&
                    linearLayoutManager.findLastCompletelyVisibleItemPosition() == detailedApiResponseArrayList.size - 1)
                {
                    loadMore()
                }
            }
        })
    }

    private fun loadMore() {
        detailedApiResponseArrayList.add(null)
        dashboardDataAdapter.notifyItemInserted(detailedApiResponseArrayList.size - 1)

        val handler = Handler()
        handler.postDelayed(Runnable {
            detailedApiResponseArrayList.removeAt(detailedApiResponseArrayList.size - 1)
            val scrollPosition: Int = detailedApiResponseArrayList.size
            dashboardDataAdapter.notifyItemRemoved(scrollPosition)
            var currentSize = scrollPosition
            val nextLimit = currentSize + 20

            if(currentSize <= this.currentSize)
            {
                onShowProgress()
                while (currentSize - 1 < nextLimit) {
                    val url = detailedApiUrls[currentSize]
                    if (url != null) {
                        viewModel.makeAPICall(url)
                    }
                    currentSize++
                }
            }
            dashboardDataAdapter.notifyDataSetChanged()
        }, 1000)
    }

    private fun initializeView(view: View?) {
        recyclerView = view?.findViewById(R.id.recyclerView)!!
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        customProgressDialog = CustomProgressDialog(context)
    }

    private fun getAllHackersResponse()
    {
        onShowProgress()
        val url = RetrofitConstants.BASE_URL+"topstories.json";
        RetrofitServices().getJsonResponse(url).enqueue(object : Callback<JsonArray?> {
            override fun onFailure(call: Call<JsonArray?>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<JsonArray?>, response: Response<JsonArray?>) {
                Log.d("Data is ---> ",response.body().toString())
                createDetailedApis(response.body())
            }
        })
    }

    private fun createDetailedApis(response: JsonArray?) {
        for (res in 0 until response?.size()!!) {
            val url = RetrofitConstants.BASE_URL + "item/" + "${response.get(res)}" + ".json"
            Log.d("url is --> ",url)
            detailedApiUrls.add(url)
        }

        for (i in 0..20)
        {
            Log.d("Individual url is --> ", RetrofitConstants.BASE_URL+"item/"+"${response.get(i)}"+".json")
            val url = RetrofitConstants.BASE_URL+"item/"+"${response.get(i)}"+".json"
            viewModel.makeAPICall(url)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        viewModel.setListener(this)
    }

    override fun onSuccess(detailAPIResponse: DetailAPIResponse?) {
        Log.d("************", detailAPIResponse.toString())
        detailedApiResponseArrayList.add(detailAPIResponse)
        Log.d("ResponseArrayList --> ", ""+detailedApiResponseArrayList.size)

        if(detailedApiResponseArrayList.size>20 && !adapterSet)
        {
            dashboardDataAdapter = DashBoardDataAdapter(detailedApiResponseArrayList)
            dashboardDataAdapter.listener = this
            recyclerView.adapter = dashboardDataAdapter

            adapterSet = true;
        }
        else
            recyclerView.adapter?.notifyDataSetChanged()

        onHideProgress()
        currentSize = detailedApiResponseArrayList.size
    }

    override fun onFailure(message: String) {
        Toast.makeText(activity,""+message,Toast.LENGTH_SHORT).show()
    }

    fun onHideProgress() {
        if (customProgressDialog.isShowing) {
            customProgressDialog.dismiss()
        }
    }

    fun onShowProgress() {
        if (!customProgressDialog.isShowing) {
            customProgressDialog.show()
        }
    }

    override fun onStop() {
        super.onStop()
        adapterSet = false;
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(activity, detailedApiResponseArrayList[position]?.url,Toast.LENGTH_LONG).show()

        // send url in bundle form to next fragment
        val bundle = Bundle()
        bundle.putString(ApplicationConstants.url, detailedApiResponseArrayList[position]?.url)
        bundle.putString(ApplicationConstants.title, detailedApiResponseArrayList[position]?.title)
        val detailPageFragment = DetailPageFragment.newInstance()
        detailPageFragment.arguments = bundle
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.container, detailPageFragment, "DetailPageFragment")
            .addToBackStack(null)
            .commit()
    }
}
