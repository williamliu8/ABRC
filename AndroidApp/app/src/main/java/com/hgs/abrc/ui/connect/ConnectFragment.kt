package com.hgs.abrc.ui.connect



import android.app.Activity
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hgs.abrc.R
import kotlinx.android.synthetic.main.fragment_connect.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class ConnectFragment : Fragment() {

    private lateinit var connectViewModel: ConnectViewModel
    //bluetooth adapter
    lateinit var bAdapter:BluetoothAdapter
    private val REQUEST_CODE_ENABLE_BT:Int = 1
    private val REQUEST_CODE_DISCOVERABLE_BT:Int = 2
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //connectViewModel =
        //    ViewModelProviders.of(this).get(ConnectViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_connect, container, false)
        //val bt_status: TextView = root.findViewById(R.id.bt_status)
        //connectViewModel.text.observe(viewLifecycleOwner, Observer {
        //    bt_status.text = it
        //})
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            REQUEST_CODE_ENABLE_BT ->
                if(resultCode == Activity.RESULT_OK){
                    makeText(
                    requireContext(),
                    "BT is on now",
                    LENGTH_LONG
                    ).show()
                }
                else{
                    makeText(
                    requireContext(),
                    "Couldn't turn on BT",
                    LENGTH_LONG
                ).show()
                }
            REQUEST_CODE_DISCOVERABLE_BT ->
                if(resultCode == Activity.RESULT_OK){
                    makeText(
                    requireContext(),
                    "Is discoverable",
                    LENGTH_LONG
                    ).show()
                }
                else{
                    makeText(
                    requireContext(),
                    "Couldn't be discoverable",
                    LENGTH_LONG
                    ).show()
                }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //init bluetooth adapter
        bAdapter = BluetoothAdapter.getDefaultAdapter()

        //check if bluetooth is available or not
        if (bAdapter == null) {
            makeText(
                requireContext(),
                "Your device doesn't support BT",
                LENGTH_LONG
            ).show()
        } else {
            makeText(
                requireContext(),
                "Your device supports BT",
                LENGTH_LONG
            ).show()
        }

        //turn on bluetooth
        btn_bton.setOnClickListener {
            if (bAdapter.isEnabled) {
                //already turn on
                makeText(
                    requireContext(),
                    "BT is already on",
                    LENGTH_LONG
                ).show()
            } else {
                //turn on BT
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
            }
        }

        //turn off bluetooth
        btn_btoff.setOnClickListener {
            if (!bAdapter.isEnabled) {
                //already turn off
                makeText(
                    requireContext(),
                    "BT is already off",
                    LENGTH_LONG
                ).show()
            } else {
                //turn off BT
                bAdapter.disable()
                makeText(
                    requireContext(),
                    "BT is off now",
                    LENGTH_LONG
                ).show()
            }
        }
        //search new devices
        btn_search_dev.setOnClickListener {
            if (!bAdapter.isDiscovering) {
                makeText(
                    requireContext(),
                    "Discovering devices",
                    LENGTH_LONG
                ).show()
                val intent = Intent(Intent(BluetoothAdapter.ACTION_DISCOVERY_STARTED))
                startActivityForResult(intent, REQUEST_CODE_DISCOVERABLE_BT)
            }
        }

        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_address: String
        val devices = bAdapter.bondedDevices
        //display paired devices
        //can make a function
        // private fun pairedDeviceList() { why private?
        btn_show_pired_dev.setOnClickListener {

            val name_list = ArrayList<Any>()
            val dev_list : ArrayList<BluetoothDevice> = ArrayList()
          //what's the difference between this?
          //val list : ArrayList<BluetoothDevice> = ArrayList()
            if (!devices.isEmpty()) {
                for (cur_device in devices) {
                    name_list.add(cur_device.name)
                    dev_list.add(cur_device)
                }
            } else {
                makeText(
                    requireContext(),
                    "no paired bluetooth devices found",
                    LENGTH_LONG
                ).show()
            }

            fun sendCommand(input: String) {
                if (m_bluetoothSocket != null) {
                    try{
                        m_bluetoothSocket!!.outputStream.write(input.toByteArray())
                    } catch(e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, name_list)
            device_list.adapter = adapter
            device_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val address: String = dev_list[position].address
                val device: BluetoothDevice = bAdapter.getRemoteDevice(address)
                m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                m_bluetoothSocket!!.connect()

                sendCommand("r")
                //val device: BluetoothDevice = list[position]
                //val address: String = device.address
            }
        }
    }
}