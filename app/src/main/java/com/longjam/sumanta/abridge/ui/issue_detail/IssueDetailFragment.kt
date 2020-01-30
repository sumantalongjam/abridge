package com.longjam.sumanta.abridge.ui.issue_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.longjam.sumanta.abridge.R
import com.longjam.sumanta.abridge.databinding.FragmentIssueDetailBinding
import com.longjam.sumanta.abridge.db.entity.Issue

class IssueDetailFragment : DialogFragment() {

    private lateinit var binding: FragmentIssueDetailBinding
    private lateinit var viewModel: IssueDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
        viewModel = ViewModelProvider(this).get(IssueDetailViewModel::class.java)
        arguments?.let {
            viewModel.issue = it.getSerializable(ARG_ISSUE) as Issue
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = DataBindingUtil.inflate<FragmentIssueDetailBinding>(inflater, R.layout.fragment_issue_detail, container, false)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailAdapter = IssueDetailAdapter(ArrayList())
        with(binding.detailRv) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            adapter = detailAdapter
        }
        viewModel.issue?.let {
            viewModel.getAllComment(it.number.toString())
                .observe(viewLifecycleOwner, Observer { commentList ->
                    detailAdapter.addItem(it)
                    if (commentList != null) {
                        detailAdapter.submitList(commentList)
                    }
                })
        }
    }

    companion object {
        private const val ARG_ISSUE = "arg_issue"
        fun show(fragmentManager: FragmentManager, issue: Issue) = IssueDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_ISSUE, issue)
            }
            show(fragmentManager, "IssueDetailFragment")
        }
    }

}
