package com.bookify.ui.catalogo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bookify.R
import com.bookify.data.Book

class BookAdapter(private val onClick: (Book) -> Unit) :
    ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback) {

    class BookViewHolder(itemView: View, val onClick: (Book) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val idTextView: TextView = itemView.findViewById(R.id.id_text)
        private val nameTextView: TextView = itemView.findViewById(R.id.name_text)
        private val authorTextView: TextView = itemView.findViewById(R.id.author_text)
        private var currentBook: Book? = null

        init {
            itemView.setOnClickListener {
                currentBook?.let {
                    onClick(it)
                }
            }
        }

        fun bind(book: Book) {
            currentBook = book
            idTextView.text = book.id.toString()
            nameTextView.text = book.name
            authorTextView.text = book.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
    }


}

object BookDiffCallback : DiffUtil.ItemCallback<Book>() {
     override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
         return oldItem == newItem
     }

     override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
         return oldItem.id == newItem.id
     }
 }
