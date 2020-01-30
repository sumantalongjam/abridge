package com.longjam.sumanta.abridge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.longjam.sumanta.abridge.R
import com.longjam.sumanta.abridge.db.entity.Issue
import com.longjam.sumanta.abridge.ui.issue_detail.IssueDetailFragment
import com.longjam.sumanta.abridge.ui.issue_list.IssueListFragment

class MainActivity : AppCompatActivity(), IssueListFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, IssueListFragment.newInstance())
                .commitNow()
        }
    }

    override fun onListFragmentInteraction(issue: Issue) {
        IssueDetailFragment.show(supportFragmentManager, issue)
    }
}
