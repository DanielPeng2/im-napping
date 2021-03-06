package com.pengdaniel.imnapping.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pengdaniel.imnapping.R
import com.pengdaniel.imnapping.model.CustomMessage
import com.pengdaniel.imnapping.model.SharedPrefManager
import com.pengdaniel.imnapping.model.SharedPrefType
import com.pengdaniel.imnapping.presenter.CustomMessagesPresenter
import kotlinx.android.synthetic.main.activity_custom_messages.*

class CustomMessagesActivity : AppCompatActivity(), CustomMessagesView, CustomMessageDialogListener {

    private lateinit var presenter: CustomMessagesPresenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_messages)

        setSupportActionBar(custom_messages_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.title_custom_messages)

        presenter = CustomMessagesPresenter(this,
                SharedPrefManager(this, SharedPrefType.MESSAGES))

        fab.setOnClickListener {
            presenter.onFloatingActionButtonClick()
        }

        viewManager = LinearLayoutManager(this)
        recyclerView = custom_messages_list.apply {
            layoutManager = viewManager
        }

        presenter.onCreate()
    }

    override fun initializeMessageList(customMessages: ArrayList<CustomMessage>) {
        viewAdapter = CustomMessagesAdapter(customMessages, presenter, this)
        custom_messages_list.apply {
            adapter = viewAdapter
        }
    }

    override fun deleteMessageListItem(position: Int) {
        viewAdapter.notifyItemRemoved(position)
    }

    override fun addMessageListItem(position: Int) {
        viewAdapter.notifyItemInserted(position)
    }

    override fun editMessageListItem(position: Int) {
        viewAdapter.notifyItemChanged(position)
    }

    override fun openCustomMessagesDialog() {
        CustomMessageDialogFragment.display(supportFragmentManager)
    }

    override fun openCustomMessagesDialog(customMessage: CustomMessage) {
        CustomMessageDialogFragment.display(supportFragmentManager, customMessage)
    }

    override fun onAddDialogPositiveClick(address: String, name: String, message: String) {
        presenter.onAddDialogPositiveClick(address, name, message)
    }

    override fun onEditDialogPositiveClick(address: String, name: String, message: String, delete: String) {
        presenter.onEditDialogPositiveClick(address, name, message, delete)
    }

    override fun displayExistingAddressError() {
        Snackbar.make(layout_custom_messages,
                R.string.error_existing_address, Snackbar.LENGTH_LONG).show()
    }
}