package com.hgs.abrc.ui.connect



import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hgs.abrc.R
import kotlinx.android.synthetic.main.fragment_connect.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class ShareSocketViewModel : ViewModel() {
    var shared_socket = MutableLiveData<BluetoothSocket?>()
    fun sendSocket(socket: BluetoothSocket?) {
        shared_socket.value = socket
    }
}

class ConnectFragment : Fragment() {

    val shareSocketmodel: ShareSocketViewModel by activityViewModels()
    //BT Step 2 : get the BluetoothAdapter
    var bAdapter = BluetoothAdapter.getDefaultAdapter()
    //BT Step 2 Ends
    lateinit var devices:Set<BluetoothDevice>
    // 1101 means Serial Port Profile(SPP)
    // 0000-1000-8000-00805F9B34FB are BASE_UUID
    // reference to https://www.bluetooth.com/specifications/assigned-numbers/service-discovery/
    var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    var m_bluetoothSocket: BluetoothSocket? = null
    val name_list = ArrayList<Any>()
    val dev_list : ArrayList<BluetoothDevice> = ArrayList()
    private val REQUEST_CODE_ENABLE_BT:Int = 1
    // BT Step 4 : Query paired devices
    fun show_pairedlist(){
        devices = bAdapter.bondedDevices
        // name_list stores "name of paired devices"
        // adapter is for "device_list" in connect fragment, it will show content in "name_list"
        //
        // dev_list stores "address of paired devices"
        // We need address so that we can connect with it.
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, name_list)

        if (!devices.isEmpty()) {
            // If we don't clear here, when press "update paired devices list" the list will append repetitively.
            name_list.clear()
            dev_list.clear()
            for (cur_device in devices) {
                name_list.add(cur_device.name)
                dev_list.add(cur_device)
            }
            device_list.adapter = adapter
        } else {
            makeText(
                requireContext(),
                "no paired bluetooth devices found",
                LENGTH_LONG
            ).show()
        }
    }
    // BT Step 4 Ends
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_connect, container, false)
        //val bt_status: TextView = root.findViewById(R.id.bt_status)

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
                    show_pairedlist()
                }
                else{
                    makeText(
                    requireContext(),
                    "Couldn't turn on BT",
                    LENGTH_LONG
                ).show()
                }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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

            if (!bAdapter.isEnabled) {
                //
                makeText(
                    requireContext(),
                    "Please turn on BT",
                    LENGTH_LONG
                ).show()
            }
            else{
                show_pairedlist()
            }
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
                //BT Step 3 : turn on BT
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
                //BT Step 3 Ends
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
                device_list.setAdapter(null)
                makeText(
                    requireContext(),
                    "BT is off now",
                    LENGTH_LONG
                ).show()
            }
        }

        // display paired devices
        btn_show_pired_dev.setOnClickListener {
            show_pairedlist()
        }

        //connect to selected item
        device_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            try {
                //BT Step 5 : Connect and get a socket
                if (m_bluetoothSocket == null) {
                    val address: String = dev_list[position].address // get address of selected device.
                    val device: BluetoothDevice = bAdapter.getRemoteDevice(address) // get remote device
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID) //create a socket with remote device
                    shareSocketmodel.sendSocket(m_bluetoothSocket) //save socket into Viewmodel, so that "control fragment" can see it.
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect() // connect with remote device
                //BT Step 5 Ends
                } else {
                    makeText(
                        requireContext(),
                        "Already Connected",
                        LENGTH_LONG
                    ).show()
                }
            } catch(e: IOException){
                makeText(
                    requireContext(),
                    "Couldn't connect",
                    LENGTH_LONG
                ).show()
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
            }
            //val device: BluetoothDevice = list[position]
            //val address: String = device.address
        }
    }
}