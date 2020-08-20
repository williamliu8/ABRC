/*
William Liu Aug 18 2020

 This program tests:
 1.receive bluetooth command to control the car moves
   forward
   backward
   turn left
   turn right
   stop
 */
#define RSP 6  //Right Speed(ENA ,PWM)
#define LSP 5  //Left Speed(ENB ,PWM)

#define RFW 4  // Right Foward (IN2 ,Clock Wise)
#define RBW 12 // Right Backward (IN1 ,Counter Clock Wise)

#define LFW 7 //Left Foward (IN3 ,Counter Clock Wise)
#define LBW 8 //Left Backward (IN4, ClockWise)

#define HW_UART 0

#include <stdlib.h>
#include <SoftwareSerial.h>
SoftwareSerial mySerial(10, 11); // use software UART RX, TX ,so that we can hot update firmware 
char command[1];
int speedval=255;

void setup() {
  #if(HW_UART==1)
  Serial.begin(9600);
  #endif
  mySerial.begin(38400); //baud rate of our bluetooth board is 38400
  pinMode(RSP,OUTPUT);
  pinMode(RFW,OUTPUT);
  pinMode(RBW,OUTPUT);  
  
  pinMode(LSP,OUTPUT);
  pinMode(LBW,OUTPUT);
  pinMode(LFW,OUTPUT);

  speed(speedval);
}

void forward()
{
  digitalWrite(RFW,HIGH);
  digitalWrite(RBW,LOW);
  digitalWrite(LFW,HIGH);
  digitalWrite(LBW,LOW);  
}

void backward()
{
  digitalWrite(RBW,HIGH);
  digitalWrite(RFW,LOW);
  digitalWrite(LBW,HIGH);
  digitalWrite(LFW,LOW);  
}

void left()
{
  digitalWrite(RBW,LOW);
  digitalWrite(RFW,HIGH);
  digitalWrite(LBW,LOW);
  digitalWrite(LFW,LOW);  
}

void right()
{
  digitalWrite(RBW,LOW);
  digitalWrite(RFW,LOW);
  digitalWrite(LBW,LOW);
  digitalWrite(LFW,HIGH);
}

void carstop()
{
  digitalWrite(RBW,HIGH);
  digitalWrite(RFW,HIGH);
  digitalWrite(LBW,HIGH);
  digitalWrite(LFW,HIGH);  
}
void speed(char speedval)
{
  analogWrite(RSP,speedval);
  analogWrite(LSP,speedval);  
}

void loop()
{
  if(mySerial.available())
  {
    command[0] = mySerial.read();

    if (command[0] >= '0' and command[0] <= '5')
    { 
      if (command[0]=='5')
        speedval = 255;
      else
      {
        command[0] += 5;      
        speedval = atoi(command)*255/10;
      }
      speed(speedval);
    }
    
    switch(command[0])
    {
      case 'f':
        forward();
        break;
      case 'b':
        backward();
        break;
      case 'l':
        left();
        break;
      case 'r':
        right();
        break;
      case 's':
        carstop();
        break;   
    }    
  }
  
}
