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
<img src="https://drive.google.com/uc?export=view&id=1OCxtIBjtbVYXp89iXSmIkKA6XPe_b_cu" width="256" title="L298N">  

L298N x 1:  
<img src="https://drive.google.com/uc?export=view&id=15L7OjhvyxIVTLsguzDvnxdhUIrZoXzcV" width="256" title="L298N">  
* A dual H-Bridge motor driver which allows speed and direction control of two DC motors at the same time.  
* Can drive DC motors that have voltages between 5 and 35V, with a peak current up to 2A.  
* We use PWM to control speed,a good website to understand [L298N,PWM and H-Bridge](https://lastminuteengineers.com/l298n-dc-stepper-driver-arduino-tutorial/)  
* **In this project,please close 5VEN with a jumber**

Bluetooth module HC-06 x 1:  
<img src="https://drive.google.com/uc?export=view&id=1aVFuakOrTW34kmkH0ub_FxJGJESoDFuP" width="384" title="L298N">  
* A Bluetooth module that uses CSR BC-417 chip.
* Simply press "Scan" on your android device, you will see "HC-06" and pair with it. (password is 1234)

Schematic(Please Click on the picture to enlarge it):  
<img src="https://drive.google.com/uc?export=view&id=1U_cqYKbb0YQoDSV1TalG-92-98ioZb4T" width="256" title="RC Car 1">

After assemble:  
|Top|Bottom|
|--------|--------
|<img src="https://drive.google.com/uc?export=view&id=1JGI4K7iLmEa5d8gfoWp30khSrUcvD67R" width="256" title="RC Car 1">|<img src="https://drive.google.com/uc?export=view&id=1R0J8BOFk0adDOwCiwhyO7AaHJ1ML7Ssp" width="256" title="RC Car 2">|<img src="https://drive.google.com/uc?export=view&id=1JGI4K7iLmEa5d8gfoWp30khSrUcvD67R" width="256" title="RC Car 1">  
## Arduino UNO Firmware:  
Receiving data from Bluetooth HC-06 module is exactly the same as receving data from a regular UART port.  
The default baudrate of HC-06 is 38400
In this project, I used software UART(D10 for RX, D11 for TX) for HC-06 instead of using default UART for these reasons:  
* When upload code to Arduino UNO, I don't have to remove HC-06
* If I set "#define DEBUG 1", I can send command through "Serial Monitor" in Arduino IDE to test my firmware without Bluetooth module.  

The commands are in ASCII format:  
'w': Forward, 'x': Backward, 'a': Left, 'd': Right, 's': Stop, 'q': Left Spin, 'e': Right Spin  

I set motors to full speed, the reason for using PWM is I can have 
'A'~'Z': can finetune the car go straight.  
* 'A' to 'M': Decrease Left mortor speed 1~13  
* 'N' to 'Z': Decrease Right mortor speed 1~13  
After you found the proper value, modify :  
```int lspeedval=255;```  
```int rspeedval=252;```  

