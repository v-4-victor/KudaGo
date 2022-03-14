/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.kudago.ui.news

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kudago.NewsElem
import com.example.kudago.databinding.NewsElemBinding

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class NewsAdapter(private val onClickListener: OnClickListener) :
        ListAdapter<NewsElem,
                NewsAdapter.ResultsViewHolder>(DiffCallback) {

    /**
     * The ResultsViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Results] information.
     */
    class ResultsViewHolder(private var binding: NewsElemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(marsProperty: NewsElem) {
            binding.elem = marsProperty
            Log.d("ERROR", marsProperty.title)
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Results]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<NewsElem>() {
        override fun areItemsTheSame(oldItem: NewsElem, newItem: NewsElem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: NewsElem, newItem: NewsElem): Boolean {
            return oldItem.title == newItem.title
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ResultsViewHolder {
        return ResultsViewHolder(NewsElemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
        holder.bind(marsProperty)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Results]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Results]
     */
    class OnClickListener(val clickListener: (marsProperty: NewsElem) -> Unit) {
        fun onClick(marsProperty: NewsElem) = clickListener(marsProperty)
    }
}

