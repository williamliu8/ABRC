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

#include <stdlib.h>
#include <SoftwareSerial.h>
SoftwareSerial mySerial(10, 11); // use software UART RX, TX ,so that we can hot update firmware 
int lspeedval=255;
int rspeedval=252;
int lturnspeed = 80;
int rturnspeed = 80;
bool rfw,rbw,lfw,lbw;
void setup() {
  //Serial.begin(9600);

  mySerial.begin(38400); //baud rate of our bluetooth board is 38400
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
  char i=0;
  char input[6];
  char *token = NULL;
  char *command[2];

  if(mySerial.available())
  //if(Serial.available())
  {
    mySerial.readBytes(input,6);
    //Serial.readBytes(input,6);
    token = strtok(input," ");
    while (token)
    {
      command[i] = token;
      i++;
      token = strtok(NULL," ");
      
    }
    
    if (!strcmp(command[0],"f"))
    {
      direct_con(HIGH,LOW,HIGH,LOW);
      speed_con(lspeedval,rspeedval);
    }
    else if(!strcmp(command[0],"b"))
    {
      direct_con(LOW,HIGH,LOW,HIGH);
      speed_con(lspeedval,rspeedval);
    }
    else if(!strcmp(command[0],"l"))
    {
      speed_con(lspeedval*lturnspeed/100,rspeedval);
    }
    else if(!strcmp(command[0],"r"))
    {
      speed_con(lspeedval,rspeedval*rturnspeed/100);
    }
    else if(!strcmp(command[0],"s"))
    {
      direct_con(HIGH,HIGH,HIGH,HIGH);
    }
    else if(!strcmp(command[0],"lf"))
    {
      speed_con(atoi(command[1]),rspeedval);
    }
    else if(!strcmp(command[0],"rf"))
    {
      speed_con(lspeedval,atoi(command[1]));
    }
  }
}
