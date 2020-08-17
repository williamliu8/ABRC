/*
William Liu Aug 17 2020

 This program tests:
 1.speed control
 2.rotate direction control

 expected:
 speed toggle between 128 and 255
 1..Move Backward for 2 secs ( Motor R CCW & Motor L CW)
 2.Move forward for 2 secs ( Motor R CW & Motor L CCW)
 under each speed
 */
#define RSP 6  //Right Speed(ENA ,PWM)
#define LSP 5  //Left Speed(ENB ,PWM)

#define RFW 4  // Right Foward (IN2 ,Clock Wise)
#define RBW 12 // Right Backward (IN1 ,Counter Clock Wise)

#define LFW 7 //Left Foward (IN3 ,Counter Clock Wise)
#define LBW 8 //Left Backward (IN4, ClockWise)


void setup() {
  pinMode(RSP,OUTPUT);
  pinMode(RFW,OUTPUT);
  pinMode(RBW,OUTPUT);  
  
  pinMode(LSP,OUTPUT);
  pinMode(LBW,OUTPUT);
  pinMode(LFW,OUTPUT);
}
int speed=255;
void loop() {
    
    analogWrite(RSP,speed);
    analogWrite(LSP,speed);
    speed = (speed==255)?128:255; //speed toggle between 128 and 255
    //move backward for 2 secs
    digitalWrite(RBW,HIGH);
    digitalWrite(RFW,LOW);
    digitalWrite(LBW,HIGH);
    digitalWrite(LFW,LOW);
    delay(2000);
    //move forward for 2 secs
    digitalWrite(RFW,HIGH);
    digitalWrite(RBW,LOW);
    digitalWrite(LFW,HIGH);
    digitalWrite(LBW,LOW);
    delay(2000);
}
