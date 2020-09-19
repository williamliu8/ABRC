<img src="https://user-images.githubusercontent.com/50005966/93687112-05465d00-fa70-11ea-8058-377a8074266f.png" width="64" title="Appicon">  

## Introduction  
I've tested functions with  
* Samsung Note 3  
  * 5.7" 1080 x 1920
* Note 8  
  * 6.3" 1440 x 2960
* Sony Xperia Z4 tablet  
  * 10.1" 2560 x 1600
* ZTE Amazing A30  
  * 5" 480 x 854  

When I tested Note 3 and ZTE Amazing A30, I couldn't pair with HC-06, but after I restart my device, they can pair properly.  
## Functions and Screen Shoot    
|Connect|Control|
|--------|--------|
|<img src="https://user-images.githubusercontent.com/50005966/93687331-cc0eec80-fa71-11ea-94c9-4fde8530816f.jpg" width="256" title="Connect">|<img src="https://user-images.githubusercontent.com/50005966/93687333-d204cd80-fa71-11ea-812b-7b48d1e005ff.jpg" width="256" title="Control">|  

Connect Functions:  
* Detect your Android device support Bluetooth or not.
* Turn on/off Bluetooth if available.
* Update paired devices list ( show paired device list automatically when turn on Bluetooth.)
* Connect with a device from the list.(We should select HC-06 of course.)

Control Functions:  
* All functions are one-touch.  
* Control the car go foward/backward/turn left/turn right/stop.  
* Control the car do left/right 360 degree spinning.  
* Disconnect with the car.  

## General Keywords:  
* BottomNavigationView
* Fragments(connect and control fragments)  
* Button  
* TextView  
* Toast  
* ViewModel  
  * The data life cicle in ViewModel is very long, please refer to:  
  [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)  
  * After connect with the car in "connect fragment", user needs to disconnect in "control fragment", so I use ViewModel to share Bluetooth socket between fragments.  
  <img src="https://user-images.githubusercontent.com/50005966/93687334-da5d0880-fa71-11ea-9124-137f56c2405d.JPG" width="256" title="ViewModelLifeCycle">  
* ListView  
  * ArrayAdapter:  
  Stores names of paired devices so that ListView can display them.
* ScrollView  
If user has too many paired devices, one can scroll the list.  

## Introduction to Android Bluetooth API
I placed comment in each file.
|Comment|location|explaination|
|--------|--------|--------|
|BT Step 1|AndroidManifest.xml|Get Bluetooth permissions|
|BT Step 2|ConnectFragment.kt|Get the BluetoothAdapter|
|BT Step 3|ConnectFragment.kt|Turn on Bluetooth|
|BT Step 4|ConnectFragment.kt|Query paired devices|
|BT Step 5|ConnectFragment.kt|Connect and get a socket|
|BT Step 6|ContralFragment.kt(sorry for the typo..)|Send command(OutputStream.write)|
|BT Step 7|ContralFragment.kt|Disconnect|

Please refer to these two websites  
1.[Bluetooth overview](https://developer.android.com/guide/topics/connectivity/bluetooth#ConnectDevices)  
2.[android.bluetooth](https://developer.android.com/reference/android/bluetooth/package-summary)  

## About UUID
UUID means Universally Unique Identifier  
We need to use one to get the socket to communicate with the car.  
In this project, I use : 00001101-0000-1000-8000-00805F9B34FB  
00001101 means Serial Port Profile(SPP)  
0000-1000-8000-00805F9B34FB is BASE_UUID  
Please reference to this website:[UUID](https://www.bluetooth.com/specifications/assigned-numbers/service-discovery/)  

## Reference codes and video  
1. https://github.com/bauerjj/Android-Simple-Bluetooth-Example
2. https://github.com/appsinthesky/Kotlin-Bluetooth
3. https://www.youtube.com/watch?v=PtN6UTIu7yw  
Have Fun!
