package com.hgs.abrc.ui.control

import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.hgs.abrc.R
import com.hgs.abrc.ui.connect.ShareSocketViewModel
import kotlinx.android.synthetic.main.fragment_control.*
import java.io.IOException

class ContralFragment : Fragment() {

    val receiveSocketmodel: ShareSocketViewModel by activityViewModels()
    var m_bluetoothSocket: BluetoothSocket? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        receiveSocketmodel.shared_socket.observe(viewLifecycleOwner, Observer {
            m_bluetoothSocket = it
        })
        return inflater.inflate(R.layout.fragment_control, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fun sendCommand(input: String) {
            if (m_bluetoothSocket != null) {
                try{
                    m_bluetoothSocket!!.outputStream.write(input.toByteArray())
                } catch(e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        forward.setOnClickListener {
            if (m_bluetoothSocket == null){
                Toast.makeText(
                    requireContext(),
                    "Please connect to a car",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                sendCommand("w")
            }
        }
        backward.setOnClickListener {
            if (m_bluetoothSocket == null){
                Toast.makeText(
                    requireContext(),
                    "Please connect to a car",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                sendCommand("x")
            }
        }
        left.setOnClickListener {
            if (m_bluetoothSocket == null){
                Toast.makeText(
                    requireContext(),
                    "Please connect to a car",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                sendCommand("a")
            }
        }
        right.setOnClickListener {
            if (m_bluetoothSocket == null){
                Toast.makeText(
                    requireContext(),
                    "Please connect to a car",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                sendCommand("d")
            }
        }

        lspin.setOnClickListener {
            if (m_bluetoothSocket == null){
                Toast.makeText(
                    requireContext(),
                    "Please connect to a car",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                sendCommand("q")
            }
        }
        rspin.setOnClickListener {
            if (m_bluetoothSocket == null){
                Toast.makeText(
                    requireContext(),
                    "Please connect to a car",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                sendCommand("e")
            }
        }
        stop.setOnClickListener {
            if (m_bluetoothSocket == null){
                Toast.makeText(
                    requireContext(),
                    "Please connect to a car",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                sendCommand("s")
            }
        }
        fun disconnect() {
            if (m_bluetoothSocket != null) {
                try {
                    m_bluetoothSocket!!.close()
                    m_bluetoothSocket = null
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        disconnect.setOnClickListener {
            if (m_bluetoothSocket == null){
                Toast.makeText(
                    requireContext(),
                    "Please connect to a car",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                disconnect()
            }
        }

    }
}