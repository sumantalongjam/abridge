package com.longjam.sumanta.abridge.ui.issue_list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.longjam.sumanta.abridge.R
import com.longjam.sumanta.abridge.databinding.FragmentIssueListBinding
import com.longjam.sumanta.abridge.db.entity.Issue

class IssueListFragment : Fragment() {

    private lateinit var binding: FragmentIssueListBinding
    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var viewModel: IssueListViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IssueListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentIssueListBinding>(inflater, R.layout.fragment_issue_list, container, false)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val issueAdapter = IssueListAdapter(ArrayList(), listener)
        with(binding.issueRv) {
            layoutManager = LinearLayoutManager(context)
            adapter = issueAdapter
        }
        viewModel.getAllIssues().observe(viewLifecycleOwner, Observer { issueList ->
            if (issueList != null) {
                issueAdapter.submitList(issueList)
            } else {
                issueAdapter.submitList(emptyList())
            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        fun newInstance() = IssueListFragment()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(issue: Issue)
    }
}
