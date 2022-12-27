package com.codenode.findmate

import android.app.Activity
import android.widget.TextView
import android.os.Bundle
import android.util.Log
import com.codenode.findmate.R
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.json.JSONException
import java.io.IOException

class DetailActivity : Activity() {
    private lateinit var detail_title: TextView
    private lateinit var detail_des: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_detail)
        detail_title = findViewById(R.id.detail_title)
        detail_des = findViewById(R.id.detail_des)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://www.omdbapi.com/?i=tt3896198&apikey=65af756")
            .build()
        try {
            val response = client.newCall(request).execute()
            val responseData = response.body().string()
            Log.i("222222", responseData)
            val jsonObject = JSONObject(responseData)
            Log.i("222222", jsonObject.getString("Title"))
            val title = jsonObject.getString("Title")
            val Plot = jsonObject.getString("Plot")
            detail_title.setText(title)
            detail_des.setText(Plot)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        //        SharedPreferences userData=getSharedPreferences("userData",MODE_PRIVATE);
//        String savedUserName = userData.getString("userName","");
//        String savedPassword = userData.getString("userPwd","");
    }

    override fun onStart() {
        super.onStart()
    }
}