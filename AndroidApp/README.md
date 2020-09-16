<img src="https://drive.google.com/uc?export=view&id=1eE8MdbIhKJGlsQB1qWiPZafB-OgRPxRF" width="64" title="Appicon">  

## Introduction  
I've test functions with Samsung Note 3, Note 8 and Sony Xperia Z4 tablet.  

|Connect|Control|
|--------|--------|
|<img src="https://drive.google.com/uc?export=view&id=1_PI6GuUjeai_eey-yLW3g-x-qMEZKJz7" width="256" title="Connect">|<img src="https://drive.google.com/uc?export=view&id=1e6PgEim29MLYSggxGM27V3wvvR7OylTP" width="256" title="Control">|
  

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

General Keywords:  
* BottomNavigationView
* Fragments(connect and control fragments)  
* Button  
* TextView  
* Toast  
* ViewModel  
  * The data life cicle in ViewModel is very long, please refer to:  
  [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)  
  <img src="https://drive.google.com/uc?export=view&id=1wpvI_zTRmFbVtvRuEwXEaB4jeyv8Qh2e" width="256" title="ViewModelLifeCycle">  
  * After connect with the car in "connect fragment", I need to disconnect it in "control fragment", so I use it to share Bluetooth socket between fragments.  
* ListView  
  * ArrayAdapter:  
  After I get 
*
