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
import com.example.autofittextviewsample.databinding.ActivityMain2Binding

class Main2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = object : Adapter<ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return object : ViewHolder(
                    LayoutInflater.from(this@Main2Activity).inflate(R.layout.item, parent, false)
                ) {

                }
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val sb = StringBuilder("item:")
                for (i in 0..position)
                    sb.append(position.toString())
                holder.textView.text = sb
            }

            override fun getItemCount(): Int {
                return 50
            }
        }
    }

    private open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView

        init {
            textView = itemView.findViewById<View>(android.R.id.text1) as TextView
        }
    }
}
