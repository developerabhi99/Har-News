package com.example.harnews

import android.content.ContentValues.TAG
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), handleclick {
    val userlist = arrayListOf<newsdata>()
    lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById(R.id.rv_news)
        recyclerView.layoutManager=LinearLayoutManager(this)
//         rv_news.apply {
//             this.layoutManager= LinearLayoutManager(this@MainActivity)
//
//         }

        fetchData()
    }


    private fun fetchData() {
        //volly library
        val url =  "https://newsapi.org/v2/top-headlines?country=in&apiKey=f82d8d503628498cb29cb2ab189f8d02"
        //making a request
        val jsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
//                val newsArray = ArrayList<newsdata>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = newsdata(
                        newsJsonObject.getString("urlToImage"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("description"),
                        newsJsonObject.getString("publishedAt")

                    )
                    userlist.add(news)
                }

                recyclerView.adapter=Adapter(userlist,this)
                userlist.add(newsdata("a","b","c","d","2-08-2021"))
               // userlist.addAll(newsArray)
            },
            Response.ErrorListener {
                Log.d(TAG, "load: abhijit",it)
              Toast.makeText(this,"CouldnotLoad",Toast.LENGTH_SHORT).show()
            }

        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: newsdata) {
       // Toast.makeText(this,"${item.url}",Toast.LENGTH_SHORT).show()
        val builder = CustomTabsIntent.Builder().build()
        builder.launchUrl(this, Uri.parse(item.url))



    }


}