package com.example.randomuserapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.randomuserapp.data.Result
import com.example.randomuserapp.data.User
import com.example.randomuserapp.room.UserViewModel
import com.example.randomuserapp.webservice.ApiInterface
import com.example.randomuserapp.webservice.ServiceBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity(), MovieAdapter.OnItemListener, MovieAdapter.OnLongItemListener{

    private lateinit var progress_bar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    lateinit var viewModel: UserViewModel
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress_bar = findViewById(R.id.progress_bar)
        recyclerView = findViewById(R.id.recyclerView)
        fab = findViewById(R.id.fab)


        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MovieAdapter(this, this, this)
        recyclerView.adapter = adapter

        fab.setOnClickListener{
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }

//        val request = ServiceBuilder.buildService(ApiInterface::class.java)
//        val call = request.getUsers(getString(R.string.api_key))

//        call.enqueue(object : Callback<User> {
//            override fun onResponse(call: Call<User>, response: Response<User>) {
//                if (response.isSuccessful){
//                    progress_bar.visibility = View.GONE
//                    recyclerView.apply {
//                        setHasFixedSize(true)
//                        layoutManager = LinearLayoutManager(this@MainActivity)
//                        adapter = MovieAdapter(response.body()!!.results)
//                        Log.d("lolo", "onResponse: ${response.body()!!.results}")
//                    }
//                }
//            }
//            override fun onFailure(call: Call<User>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Failed ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(UserViewModel::class.java)

//       fetchUser()

        viewModel.allUser.observe(this, {list->
            list.let {
                adapter.updateList(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting){
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }
        if (item.itemId == R.id.refresh){
            progress_bar.visibility = View.VISIBLE
            fetchUser()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(v: View?, position: Int) {
        viewModel.allUser.observe(this, {list->
            list.let {
                val fName = it[position].name.first
                val lName = it[position].name.last
//                val dob = it[position].dob.date
                val city = it[position].location.city
                val country = it[position].location.country
                val phone = it[position].phone
                val pic  = it[position].picture.large
                val intent = Intent(this, DetailView::class.java)
                intent.putExtra("fname", fName)
                intent.putExtra("lname", lName)
//                intent.putExtra("dob", dob)
                intent.putExtra("phone", phone)
                intent.putExtra("pic", pic)
                intent.putExtra("city", city)
                intent.putExtra("country", country)
                startActivity(intent)
            }
        })
//        Toast.makeText(this, "position$position", Toast.LENGTH_SHORT).show()
    }

    override fun onLongItemClick(v: View?, user: Result) {
        try {
            showLogoutPopUp(user)
        }
        catch (e: Exception){
            Log.d("loko", "onLongItemClick: error - $e")
        }
//        viewModel.deleteUser(user)
    }

    private fun fetchUser(){
        val request = ServiceBuilder.buildService(ApiInterface::class.java)
        val call = request.getUsers(getString(R.string.api_key))

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful){
                    progress_bar.visibility = View.GONE

                    val result = response.body()!!.results
//                    Collections.reverse(result)

                    viewModel.addAllUser(result)

//                    recyclerView.apply {
//                        setHasFixedSize(true)
//                        layoutManager = LinearLayoutManager(this@MainActivity)
//                        adapter = MovieAdapter(response.body()!!.results)
//                        Log.d("lolo", "onResponse: ${response.body()!!.results}")
                    Log.d("lolo", "onResponse: $result")
//                    }
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLogoutPopUp(user: Result) {
        val alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setMessage("You Want to Delete").setPositiveButton("Delete", DialogInterface.OnClickListener{
                dialog, id->viewModel.deleteUser(user)
            Toast.makeText(this, "Delete successfully", Toast.LENGTH_SHORT).show()

        }).setNegativeButton("Cancel",null)

        val alertDialog: AlertDialog = alert.create()
        alertDialog.show()
    }
}