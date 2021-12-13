package com.example.postrequest

import UserItem
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        val nameEditText = findViewById<EditText>(R.id.name_et)
        val locationEditText = findViewById<EditText>(R.id.location_et)

        val saveButton = findViewById<Button>(R.id.save_btn)
        saveButton.setOnClickListener {
            insertUser(UserItem(nameEditText.text.toString(), locationEditText.text.toString(),0))
        }

        val viewButton = findViewById<Button>(R.id.view_btn)
        viewButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun insertUser(user: UserItem){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface!!.addUser(user)?.enqueue(object: Callback<UserItem?> {
            override fun onResponse(call: Call<UserItem?>, response: Response<UserItem?>) {
                Toast.makeText(this@UserInfoActivity, "User added",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<UserItem?>, t: Throwable) {
                Toast.makeText(this@UserInfoActivity, "Something went wrong, please try again",
                    Toast.LENGTH_SHORT).show()
                call.cancel()
            }

        })

    }
}