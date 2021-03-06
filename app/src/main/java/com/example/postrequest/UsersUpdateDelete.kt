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

class UsersUpdateDelete : AppCompatActivity() {
    private lateinit var etUserID: EditText
    private lateinit var etName: EditText
    private lateinit var etLocation: EditText
    private lateinit var btDelete: Button
    private lateinit var btUpdate: Button

    private val apiInterface by lazy { APIClient().getClient()?.create(APIInterface::class.java) }

    private var userID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_update_delete)

        etUserID = findViewById(R.id.etUserID)
        etName = findViewById(R.id.etName)
        etLocation = findViewById(R.id.etLocation)

        btDelete = findViewById(R.id.btDelete)
        btDelete.setOnClickListener {
            try{
                userID = etUserID.text.toString().toInt()
                deleteUser()
            }catch(e: Exception){
                Toast.makeText(this@UsersUpdateDelete, "Invalid User ID", Toast.LENGTH_LONG).show()
            }
        }
        btUpdate = findViewById(R.id.btUpdate)
        btUpdate.setOnClickListener {
            if(etUserID.text.isNotEmpty() && etName.text.isNotEmpty() && etLocation.text.isNotEmpty()){
                try{
                    userID = etUserID.text.toString().toInt()
                    updateUser()
                }catch(e: Exception){
                    Toast.makeText(this@UsersUpdateDelete, "Invalid data", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this@UsersUpdateDelete, "All fields are required", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateUser(){
        apiInterface?.updateUser(
            userID,
            UserItem(
                etLocation.text.toString(),
                etName.text.toString(),
                userID
            ))?.enqueue(object: Callback<UserItem> {
            override fun onResponse(call: Call<UserItem>, response: Response<UserItem>) {
                Toast.makeText(this@UsersUpdateDelete, "User Updated", Toast.LENGTH_LONG).show()
                val intent = Intent(this@UsersUpdateDelete, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onFailure(call: Call<UserItem>, t: Throwable) {
                Toast.makeText(this@UsersUpdateDelete, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun deleteUser(){
        apiInterface?.deleteUser(userID)?.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@UsersUpdateDelete, "User Deleted", Toast.LENGTH_LONG).show()
                val intent = Intent(this@UsersUpdateDelete, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@UsersUpdateDelete, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        })
    }
}