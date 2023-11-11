package com.example.programmer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var rvMain : RecyclerView
    lateinit var rvAdapter: UserAdapter

    var BaseURL = "https://api.github.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain = findViewById(R.id.rv_page)

        rvMain.layoutManager = LinearLayoutManager(this)

        getAllData()
    }

    private fun getAllData() {
        var retrofit = Retrofit
            .Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiInterface::class.java)

        var retroData = retrofit.getData()

        retroData.enqueue(object : Callback<List<UsersItem>> {
            override fun onResponse(
                call: Call<List<UsersItem>>,
                response: Response<List<UsersItem>>
            ) {
                var data = response.body()!!

                rvAdapter = UserAdapter(baseContext, data)

                rvMain.adapter = rvAdapter

                Log.d("data", data.toString())
            }

            override fun onFailure(call: Call<List<UsersItem>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Koneksi error",
                    Toast.LENGTH_LONG).show()
            }

        })
    }
}