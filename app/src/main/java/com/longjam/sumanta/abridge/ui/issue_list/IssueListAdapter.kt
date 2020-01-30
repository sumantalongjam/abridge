package com.longjam.sumanta.abridge.ui.issue_list

import android.text.Html
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.longjam.sumanta.abridge.databinding.IssueRowBinding
import com.longjam.sumanta.abridge.db.entity.Issue
import com.longjam.sumanta.abridge.ui.issue_list.IssueListFragment.OnListFragmentInteractionListener

class IssueListAdapter(
    private val issueList: ArrayList<Issue>,
    private val listener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<IssueListAdapter.IssueViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Issue
            listener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val binding = IssueRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val issue = issueList[position]
        holder.bind(issue)
    }

    override fun getItemCount(): Int = issueList.size

    fun submitList(issues: List<Issue>) {
        issueList.addAll(issues)
        this.notifyDataSetChanged()
    }

    inner class IssueViewHolder(private val binding: IssueRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(issue: Issue) {
            binding.issue = issue
            binding.executePendingBindings()
            binding.descriptionTv.text = Html.fromHtml(issue.body.take(200))
            with(binding.rowLayout) {
                tag = issue
                setOnClickListener(onClickListener)
            }
        }
    }
}
