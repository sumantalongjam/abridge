package com.longjam.sumanta.abridge.ui.issue_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.longjam.sumanta.abridge.databinding.DetailCommentSectionLayoutBinding
import com.longjam.sumanta.abridge.databinding.DetailFirstSectionLayoutBinding
import com.longjam.sumanta.abridge.db.entity.Comment
import com.longjam.sumanta.abridge.db.entity.Issue

class IssueDetailAdapter(private val itemList: ArrayList<Any>): RecyclerView.Adapter<IssueDetailAdapter.DetailViewHolder>() {

    private val TYPE_DETAIL = 0
    private val TYPE_COMMENT = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_DETAIL) {
            val itemBinding = DetailFirstSectionLayoutBinding.inflate(layoutInflater, parent, false)
            DetailSectionViewHolder(itemBinding)
        } else {
            val itemBinding =
                DetailCommentSectionLayoutBinding.inflate(layoutInflater, parent, false)
            CommentSectionViewHolder(itemBinding)
        }
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val any = itemList[position]
        holder.bind(any)
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        return if ((itemList[position]) is Issue) TYPE_DETAIL else TYPE_COMMENT
    }

    fun addItem(issues: Any) {
        itemList.add(issues)
        this.notifyDataSetChanged()
    }

    fun submitList(comments: List<Any>) {
        itemList.addAll(comments)
        this.notifyDataSetChanged()
    }

    abstract inner class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(any: Any)
    }

    inner class DetailSectionViewHolder(private val binding: DetailFirstSectionLayoutBinding) :
        DetailViewHolder(binding.root) {
        override fun bind(any: Any) {
            if (any is Issue) {
                binding.issue = any
            }
            binding.executePendingBindings()
        }
    }

    inner class CommentSectionViewHolder(private val binding: DetailCommentSectionLayoutBinding) :
        DetailViewHolder(binding.root) {
        override fun bind(any: Any) {
            if (any is Comment) {
                binding.comment = any
            }
            binding.executePendingBindings()
        }
    }
}