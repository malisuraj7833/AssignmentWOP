package com.example.assignment.ui.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.assignment.R
import com.example.assignment.util.ApplicationConstants
import com.example.assignment.util.ApplicationConstants.Companion.url


class DetailPageFragment : Fragment() {

    private lateinit var title: String
    private lateinit var textViewTitle: TextView
    private lateinit var mWebview: WebView

    companion object {
        fun newInstance() =
            DetailPageFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.detail_page_fragment, container, false)

        initializeView(view)
        getDataFromBundle()

        return view
    }

    private fun getDataFromBundle() {
        url = arguments!!.getString(ApplicationConstants.url)!!
        initialiseWebView(url)

        title = arguments!!.getString(ApplicationConstants.title).toString()
        textViewTitle.text = title
    }

    private fun initializeView(view: View?) {
        textViewTitle = view?.findViewById(R.id.textViewTitle)!!
        mWebview = view.findViewById(R.id.webView)!!
    }

    private fun initialiseWebView(url: String?) {
        mWebview.settings.javaScriptEnabled = true
        mWebview.loadUrl(url)
        mWebview.webViewClient = MyWebViewClient()
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return true
        }
    }
}