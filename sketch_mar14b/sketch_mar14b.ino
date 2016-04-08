#include <SoftwareSerial.h>
#include <LiquidCrystal.h>
#include <NewPing.h>

#define Rx1 10
#define Tx1 11


SoftwareSerial BTOne(10, 9); // RX | TX
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
int j=1;
String message;
char inData[64];
char inChar=-1;

void setup()
{
  pinMode(8, OUTPUT);  // this pin will pull the HC-05 pin 34 (key pin) HIGH to switch module to AT mode
  digitalWrite(8, HIGH);
  
  Serial.begin(9600);
  Serial.println("Enter AT commands:");

  BTOne.begin(9600);  // HC-05 default speed in AT command more
  BTOne.flush();
 
  pinMode(Tx1, OUTPUT);                                                    // Configure Tx as OUTPUT (Transmitter)
  pinMode(Rx1, INPUT);  
  
  lcd.begin(16, 2);
  lcd.print("setup");

}

void loop()
{

 
  
  
 if (Serial.available()){
    
  
   char a = Serial.read();
    
   if(a=='0'){
     
     lcd.clear();
      lcd.setCursor(1,2 );           // Moves  the cursor of the display to the next line
      lcd.print( BTOne.println("AT"));
   }
    if(a=='1')
      BTOne.println("AT+INIT");
      if(a=='2')
      BTOne.println("AT+ROLE?");
      if(a=='3')
      BTOne.println("AT+INQ");
      if(a=='4')
      BTOne.println("AT+ROLE=1");
      if(a=='5')
      BTOne.println("AT+RNAME?3014,11,50039");
       if(a=='6')
      BTOne.println("AT+LINK=3014,11,50039");
      if(a=='7'){
      lcd.clear();
      lcd.setCursor(1,2 );           // Moves  the cursor of the display to the next line
      lcd.print("hello");
      }
       else{
      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print("Total Slots : 16");
      lcd.setCursor(0,1 );           // Moves  the cursor of the display to the next line
      lcd.print("Empty Slots :");
      lcd.setCursor(15,1 );           // Moves  the cursor of the display to the next line
      lcd.print(a);
      }
  // BTOne.println("HI NIkhit from BtOne");
  //delay(50);
}


   if (BTOne.available()){
     
      byte numBytesAvailable= BTOne.available();
    lcd.clear();
    // if there is something to read
    if (numBytesAvailable > 0){
        // store everything into "inData"
        int i;
        for (i=0;i<numBytesAvailable;i++){
            inChar= BTOne.read();
            inData[i] = inChar;
        }

       inData[i] = '\0';
    Serial.write(inData);
       lcd.clear();
       lcd.setCursor(0, 0);
       lcd.print("Total Slots : 16");
        lcd.setCursor(1, 2);   
  //    String text = (String)BTOne.read();
         
     // lcd.print(inData);
    
   
      
  }
 
   }
   // BTOne.flush();
 //  delay(1000);
}

