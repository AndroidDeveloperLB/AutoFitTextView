package com.example.autofittextviewsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = object : Adapter<ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return object : ViewHolder(LayoutInflater.from(this@Main2Activity).inflate(R.layout.item, parent, false)) {

                }
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val sb = StringBuilder("item:")
                for (i in 0..position)
                    sb.append(Integer.toString(position))
                holder.textView.text = sb
            }

            override fun getItemCount(): Int {
                return 50
            }
        }
    }

    private open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val textView: TextView

        init {
            textView = itemView.findViewById<View>(android.R.id.text1) as TextView
        }
    }
}
