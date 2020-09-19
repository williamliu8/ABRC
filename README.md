## Introduction  
Project Name: Android Bluetooth Remote-controlled Car (ABRC)  
Author : William Liu  
Date : Aug/28/2020  
Depository includes : Android App(software) + Arduino UNO(firmware) files  

This is an interesting project that includes `hardware,firmware and software.`  
You will be able to use the app to connect to the car with bluetooth and control it.  
That's fun !  
[Demo Video 1](https://drive.google.com/file/d/1XKBzJSUhsBJ8SS74sKQXuq67VgESFzT3/view?usp=sharing)  
[Demo Video 2](https://drive.google.com/file/d/1YboXPtHKsL6V_Dz8LkaTyKyxLYmLadPt/view?usp=sharing)

## Hardware:   
Arduino UNO Rev 3 x 1  
DC gear motor x 2  
<img src="https://user-images.githubusercontent.com/50005966/93687062-a1bc2f80-fa6f-11ea-9aec-49348508bb95.JPG" width="256" title="DCMotor">  

L298N x 1:  
<img src="https://user-images.githubusercontent.com/50005966/93687075-b4366900-fa6f-11ea-850f-af79314d4fd4.JPG" width="256" title="L298N">  
* A dual H-Bridge motor driver which allows speed and direction control of two DC motors at the same time.  
* Can drive DC motors that have voltages between 5 and 35V, with a peak current up to 2A.  
* We use PWM to control speed,a good website to understand [L298N,PWM and H-Bridge](https://lastminuteengineers.com/l298n-dc-stepper-driver-arduino-tutorial/)  
* **In this project,please close 5VEN with a jumber**

Bluetooth module HC-06 x 1:  
<img src="https://user-images.githubusercontent.com/50005966/93687079-c0222b00-fa6f-11ea-922e-4c96458aeae3.JPG" width="256" title="HC-06">  
* CSR BC-417 chip.
* Simply press "Scan" in Bluetooth setting on your android device, you will see "HC-06" and pair with it. (password is 1234)

Schematic(Please Click on the picture to enlarge it):  
<img src="https://user-images.githubusercontent.com/50005966/93687082-cadcc000-fa6f-11ea-8c6f-ffe849a240f0.JPG" width="512" title="Circuit">

After assemble:  
|Top|Bottom|
|--------|--------|
|<img src="https://user-images.githubusercontent.com/50005966/93687095-e2b44400-fa6f-11ea-92bf-d5d43031a99c.jpg" width="256" title="RC Car 1">|<img src="https://user-images.githubusercontent.com/50005966/93687099-ea73e880-fa6f-11ea-9756-89d5cc8c742d.jpg" width="256" title="RC Car 2">|
## Arduino UNO Firmware:  
### Software UART
Receiving data from Bluetooth HC-06 module is exactly the same as receving data from a regular UART device.  
The default baudrate of my HC-06 is 38400.  

In this project, I used software UART(D10 for RX, D11 for TX) for HC-06 instead of using default UART for these reasons:  
* When upload code to Arduino UNO, I don't have to remove HC-06.
* If I set "#define DEBUG 1", firmware receives commands from "Serial Monitor" in Arduino IDE, I can test firmware without Bluetooth module.  
* If I set "#define DEBUG 0", firmware receives commands from "HC-06", I can print message to Serial Monitor throught default UART to get more information.  

### Commands
The commands are in ASCII format:  
'w': Forward, 'x': Backward, 'a': Left, 'd': Right, 's': Stop, 'q': Left Spin, 'e': Right Spin  

### Make your car go straight  
We use PWM to control individual motor speed.  
```analogWrite(RSP,rspeedval);//rspeedval and lspeedval are from 0~255```  
```analogWrite(LSP,lspeedval);```  

You will propably find that even you set 255 for both motors, they still have different speed, this will cause the car can't go straight.  

We can use command 'A'~'Z' to finetune the car :    
* 'A' to 'M': rspeedval=255, lspeedval = 255 - 1 to 13  
* 'N' to 'Z': lspeedval=255, rspeedval = 255 - 1 to 13  

After you found the car goes straight, re-assign the speedval, in my case:  
```int lspeedval=255;```  
```int rspeedval=252;```  

### Make sure Hardware, Firmware, Bluetooth are working properly before you start to design Android app.
Android app is the most difficult part in this project.  
It is a good idea to make sure Hardware, Firmware and Bluetooth are working properly before you start to design the app.  

Here is how I do it in Windows 10:  
* Pair your car (Shows HC-06) with Windows 10
* Install "Bluetooth Serial Terminal", which you can find that in Microsoft Store.  
* Connect HC-06 with Bluetooth Serial Terminal  
* Type your command(for example w) into "Transmit" and "Send", you should see your car response correctly !
<img src="https://user-images.githubusercontent.com/50005966/93687105-fbbcf500-fa6f-11ea-88ed-d4efa2dc8a66.JPG" width="512" title="Serial Terminal">  

## Software ( Android App)  
See you in :  [README.md of the app](https://github.com/williamliu8/ABRC/blob/master/AndroidApp/README.md)  

