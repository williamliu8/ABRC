package com.hgs.abrc.ui.connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hgs.abrc.R

class ConnectFragment : Fragment() {

    private lateinit var connectViewModel: ConnectViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        connectViewModel =
                ViewModelProviders.of(this).get(ConnectViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_connect, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        connectViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}