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
#define DEBUG 0
#include <stdlib.h>
#include <SoftwareSerial.h>
SoftwareSerial mySerial(10, 11); // use software UART RX, TX ,so that we can hot update firmware 
int lspeedval=255;
int rspeedval=252;
int lturnspeed = 60;
int rturnspeed = 60;
bool rfw,rbw,lfw,lbw;
void setup() {
  #if DEBUG == 1
  Serial.begin(9600);
  #else
  mySerial.begin(38400); //baud rate of our bluetooth board is 38400
  #endif
  pinMode(RSP,OUTPUT);
  pinMode(RFW,OUTPUT);
  pinMode(RBW,OUTPUT);  
  
  pinMode(LSP,OUTPUT);
  pinMode(LBW,OUTPUT);
  pinMode(LFW,OUTPUT);

  analogWrite(RSP,rspeedval);
  analogWrite(LSP,lspeedval);  
}

//foward H,L,H,L
//backward L,H,L,H
//stop H,H,H,H
void direct_con(bool lfw, bool lbw,bool rfw,bool rbw)
{
  digitalWrite(LFW,lfw);
  digitalWrite(LBW,lbw);    
  digitalWrite(RFW,rfw);
  digitalWrite(RBW,rbw);
}

void speed_con(int lspeedval,int rspeedval)
{
  analogWrite(RSP,rspeedval);
  analogWrite(LSP,lspeedval);    
}
void loop()
{
  char command[1];
  int ft_value;
  
  #if DEBUG == 1
  if(Serial.available())
  #else
  if(mySerial.available())
  #endif
  {    
    #if DEBUG == 1
    command[0]=Serial.read();
    #else
    command[0]=mySerial.read();
    #endif
    
    if (command[0]=='w')
    {
      direct_con(HIGH,LOW,HIGH,LOW);
      speed_con(lspeedval,rspeedval);
    }
    else if(command[0]=='x')
    {
      direct_con(LOW,HIGH,LOW,HIGH);
      speed_con(lspeedval,rspeedval);
    }
    else if(command[0]=='a')
    {
      speed_con(lspeedval*lturnspeed/100,rspeedval);
    }
    else if(command[0]=='d')
    {
      speed_con(lspeedval,rspeedval*rturnspeed/100);
    }
    else if(command[0]=='s')
    {
      direct_con(HIGH,HIGH,HIGH,HIGH);
    }
    else if(command[0]=='q')
    {
      direct_con(LOW,HIGH,HIGH,LOW);
    }
    else if(command[0]=='e')
    {
      direct_con(HIGH,LOW,LOW,HIGH);
    }
    else //finetune car goes straight
    {
      ft_value = (int)command[0];
      //A~M leftspeed - 1 ~ 13
      if(ft_value >= 65 and ft_value <= 77)
      {
        ft_value -= 64;
        speed_con(lspeedval-ft_value ,rspeedval);  
      }
      //N~Z rightspeed - 1 ~ 13
      else if(ft_value >= 78 and ft_value <= 90)
      {
        ft_value -= 77;
        speed_con(lspeedval , rspeedval-ft_value);
      }
    }
  }
}
