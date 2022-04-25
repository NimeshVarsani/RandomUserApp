package com.example.randomuserapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.randomuserapp.data.Result
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MovieAdapter(private val context: Context, val listener: OnItemListener, val lListener: OnLongItemListener): RecyclerView.Adapter<MovieAdapter.MoviesViewHolder>() {

    val user = ArrayList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return user.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
//        return holder.bind(user[position])
        val currentUser = user[position]
//        holder.phoneNumber.text = currentUser.phone
        holder.fname.text = currentUser.name.first
        holder.lname.text = currentUser.name.last
        val url = currentUser.picture.large
        Glide.with(context).load(url).override(220,320).into(holder.image)
    }

    fun updateList(newList: List<Result>){
        user.clear()
        user.addAll(newList)
        notifyDataSetChanged()
    }

    inner class MoviesViewHolder(itemView : View): RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {


        val fname: TextView = itemView.findViewById(R.id.fname)
        val lname: TextView = itemView.findViewById(R.id.lname)
//        val phoneNumber: TextView = itemView.findViewById(R.id.phone)
        val image: ImageView = itemView.findViewById(R.id.img)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = this.layoutPosition
            listener.onItemClick(v, position)
        }

        override fun onLongClick(v: View?): Boolean {
            val position = this.bindingAdapterPosition
            lListener.onLongItemClick(v, user[position])
            return true
        }

//        fun bind(user: Result) {
////            Glide.with(itemView.context).load("http://image.tmdb.org/t/p/w500${movie.poster_path}")
////                .into(photo)
//            Glide.with(itemView.context).load(user.picture.thumbnail).into(image)
//            name.text = user.name.toString()
//            val oldDob = user.dob.date
//            val formattedDate = dateTimeFormatter(oldDob)
//            dob.text = formattedDate
//            phoneNumber.text = user.phone
//            Log.d("lolo", "old date - $oldDob --  new Date- $formattedDate")
//        }
    }

    interface OnItemListener {
        fun onItemClick(v: View?, position: Int)
    }

    interface OnLongItemListener {
        fun onLongItemClick(v: View?, user: Result)
    }


}
