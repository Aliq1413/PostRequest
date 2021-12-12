package com.example.postrequest
import UserItem
import Users
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var recyclerView: RecyclerView
lateinit var usersList: ArrayList<UserItem>

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addUserButton = findViewById<Button>(R.id.new_user_btn)
        addUserButton.setOnClickListener {
            startActivity(Intent(this, UserInfoActivity::class.java))
            finish()
        }

        usersList = arrayListOf()
        setUsersFromAPI()
        Log.d("users", "After setting $usersList")

        recyclerView = findViewById(R.id.users_rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setUsersFromAPI() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<Users?>? = apiInterface!!.getUsers()
        call?.enqueue(object: Callback<Users?>{
            override fun onResponse(call: Call<Users?>, response: Response<Users?>) {
                usersList = response.body()!!
                recyclerView.adapter = UsersAdapter(usersList)
            }
            override fun onFailure(call: Call<Users?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong, please try again",
                    Toast.LENGTH_SHORT).show()
                call.cancel()
            }
        })
    }
}