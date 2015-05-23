#include <SPI.h>
#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

// If using software SPI (the default case):
#define OLED_MOSI   9
#define OLED_CLK   10
#define OLED_DC    11
#define OLED_CS    12
#define OLED_RESET 13
Adafruit_SSD1306 display(OLED_MOSI, OLED_CLK, OLED_DC, OLED_RESET, OLED_CS);

int led = 4;
boolean flag = false;
boolean notificationDone = false;

String incomingString;
String incomingNotification;
String notificationFeed;
void setup()   {                
    Serial.begin(9600);
    pinMode(led, OUTPUT);
    // BUFFER
    // by default, we'll generate the high voltage from the 3.3v line internally! (neat!)
    //display.begin(SSD1306_SWITCHCAPVCC);
    display.begin(SSD1306_SWITCHCAPVCC);
    //display.clearDisplay();
    //display.drawBitmap(1, 1, mki, 128, 64, 1);
    //display.display();
    //delay for buffering (how long splash screen appears) doesnt seem to be necessary...
    //display.clearDisplay();
    //display.drawBitmap(1, 1, mki, 128, 64, 1);
    //display.display();
    //delay(5000);
    display.clearDisplay();
    display.display();
    
    //display.drawBitmap(1, 1, mki, 128, 64, 1);
    //DISPLAY (Static outdated)
    /*
    drawMailIcon(98, 10, 20);
    drawSMSIcon(108, 44, 10);
    drawVertSeperator(85, 40);
    display.setCursor(10, 25);
    display.setTextSize(2);
    display.setTextColor(WHITE);
    display.print("00:00");
    display.display();
    delay(2000);
    display.clearDisplay();
  /*
    display.setTextSize(1);
    display.setCursor(0, 0);
    display.setTextColor(WHITE);
    display.setTextWrap(true);
    display.print("This is an example email, [insert import top secret school information here...] Typing lots of things to test text wrapping.");
    display.display();
   */

}

void loop() {
    /*
    drawMailIcon(98, 10, 20);
    drawSMSIcon(108, 44, 10);
    drawVertSeperator(85, 40);
    */
    //////////////////////////////////////////////////////////////OLD
//    if (Serial.available() > 0) {
//        //display.fillCircle(10, 10, 5, WHITE);
//        // Read a byte from the serial buffer.
//        display.clearDisplay();
//        char incomingByte = (char)Serial.read();
//        //if(incomingByte != NULL){
//        if(incomingByte == 'n' && flag == false) {
//            flag = true;
//        } else if (flag == true) {
//            incomingNotification += incomingByte;
//            display.setTextSize(1);
//            display.setCursor(0, 0);
//            display.setTextColor(WHITE);
//            display.print(incomingNotification);
//            display.display();
//
//        } else {
//            incomingString += incomingByte;
//            display.setTextSize(3);
//            display.setCursor(25, 25);
//            display.setTextColor(WHITE);
//            display.print(incomingString);
//            display.display();
//        }
//        //}
//
//    } else {
//
//        incomingString= "";
//        notificationFeed += incomingNotification;
//        incomingNotification= "";
//        flag = false;
//    }
///////////////////////////////////////////end old

    if (Serial.available() > 0) {

        char incomingByte = (char)Serial.read();
        incomingString += incomingByte;

    } else {
        if (incomingString.charAt(0) == 'n') {
            display.clearDisplay();
            display.setTextSize(1);
            display.setCursor(0, 0);
            display.setTextColor(WHITE);
            display.print(incomingNotification);
            display.display();
        } else {
            display.clearDisplay();
            display.setTextSize(3);
            display.setCursor(25, 25);
            display.setTextColor(WHITE);
            display.print(incomingString);
            display.display();
        }
        incomingString= "";
        //notificationFeed += incomingNotification;
        incomingNotification= "";
        flag = false;
    }
}
void drawVertSeperator(int pos, int length) {
    int y0 = 32-length/2;
    int y1 = 32+length/2;
    display.drawLine(pos, y0, pos, y1, WHITE);  
}

void drawHorSeperator(int pos, int length, int center) {
    int x0 = center-length/2;
    int x1 = center+length/2;
    display.drawLine(x0, pos, x1, pos, WHITE);  
}

void drawMailIcon(int mailX, int mailY, int mailSize) {
    int mailWidth = mailSize;
    int mailHeight = mailSize*0.66666;
    display.drawRect(mailX, mailY, mailWidth, mailHeight, WHITE);
    display.drawTriangle(mailX, mailY, mailX+mailWidth, mailY, mailX+mailWidth/2, mailY+mailHeight/2, WHITE);

}

void drawSMSIcon(int SMSX, int SMSY, int SMSSize) {

    display.fillCircle(SMSX, SMSY, SMSSize, WHITE);
    display.fillCircle(SMSX-3*(SMSSize/4), SMSY, SMSSize/4, BLACK);
    display.fillCircle(SMSX, SMSY, SMSSize/4, BLACK);
    display.fillCircle(SMSX+3*(SMSSize/4), SMSY, SMSSize/4, BLACK);
}


void testdrawchar(void) {
    display.setTextSize(1);
    display.setTextColor(WHITE);
    display.setCursor(0,0);

    for (uint8_t i=0; i < 168; i++) {
        if (i == '\n') continue;
        display.write(i);
        if ((i > 0) && (i % 21 == 0))
        display.println();
    }    
    display.display();
}

void testdrawcircle(void) {
    for (int16_t i=0; i<display.height(); i+=2) {
        display.drawCircle(display.width()/2, display.height()/2, i, WHITE);
        display.display();
    }
}

void testfillrect(void) {
    uint8_t color = 1;
    for (int16_t i=0; i<display.height()/2; i+=3) {
        // alternate colors
        display.fillRect(i, i, display.width()-i*2, display.height()-i*2, color%2);
        display.display();
        color++;
    }
}

void testdrawtriangle(void) {
    for (int16_t i=0; i<min(display.width(),display.height())/2; i+=5) {
        display.drawTriangle(display.width()/2, display.height()/2-i,
        display.width()/2-i, display.height()/2+i,
        display.width()/2+i, display.height()/2+i, WHITE);
        display.display();
    }
}

void testfilltriangle(void) {
    uint8_t color = WHITE;
    for (int16_t i=min(display.width(),display.height())/2; i>0; i-=5) {
        display.fillTriangle(display.width()/2, display.height()/2-i,
        display.width()/2-i, display.height()/2+i,
        display.width()/2+i, display.height()/2+i, WHITE);
        if (color == WHITE) color = BLACK;
        else color = WHITE;
        display.display();
    }
}

void testdrawroundrect(void) {
    for (int16_t i=0; i<display.height()/2-2; i+=2) {
        display.drawRoundRect(i, i, display.width()-2*i, display.height()-2*i, display.height()/4, WHITE);
        display.display();
    }
}

void testfillroundrect(void) {
    uint8_t color = WHITE;
    for (int16_t i=0; i<display.height()/2-2; i+=2) {
        display.fillRoundRect(i, i, display.width()-2*i, display.height()-2*i, display.height()/4, color);
        if (color == WHITE) color = BLACK;
        else color = WHITE;
        display.display();
    }
}

void testdrawrect(void) {
  for (int16_t i=0; i<display.height()/2; i+=2) {
    display.drawRect(i, i, display.width()-2*i, display.height()-2*i, WHITE);
    display.display();
  }
}

void testdrawline() {  
  for (int16_t i=0; i<display.width(); i+=4) {
    display.drawLine(0, 0, i, display.height()-1, WHITE);
    display.display();
  }
  for (int16_t i=0; i<display.height(); i+=4) {
    display.drawLine(0, 0, display.width()-1, i, WHITE);
    display.display();
  }
  delay(250);

  display.clearDisplay();
  for (int16_t i=0; i<display.width(); i+=4) {
    display.drawLine(0, display.height()-1, i, 0, WHITE);
    display.display();
  }
  for (int16_t i=display.height()-1; i>=0; i-=4) {
    display.drawLine(0, display.height()-1, display.width()-1, i, WHITE);
    display.display();
  }
  delay(250);

  display.clearDisplay();
  for (int16_t i=display.width()-1; i>=0; i-=4) {
    display.drawLine(display.width()-1, display.height()-1, i, 0, WHITE);
    display.display();
  }
  for (int16_t i=display.height()-1; i>=0; i-=4) {
    display.drawLine(display.width()-1, display.height()-1, 0, i, WHITE);
    display.display();
  }
  delay(250);

  display.clearDisplay();
  for (int16_t i=0; i<display.height(); i+=4) {
    display.drawLine(display.width()-1, 0, 0, i, WHITE);
    display.display();
  }
  for (int16_t i=0; i<display.width(); i+=4) {
    display.drawLine(display.width()-1, 0, i, display.height()-1, WHITE); 
    display.display();
  }
  delay(250);
}

void testscrolltext(void) {
  display.setTextSize(2);
  display.setTextColor(WHITE);
  display.setCursor(10,0);
  display.clearDisplay();
  display.println("scroll");
  display.display();

  display.startscrollright(0x00, 0x0F);
  delay(2000);
  display.stopscroll();
  delay(1000);
  display.startscrollleft(0x00, 0x0F);
  delay(2000);
  display.stopscroll();
  delay(1000);    
  display.startscrolldiagright(0x00, 0x07);
  delay(2000);
  display.startscrolldiagleft(0x00, 0x07);
  delay(2000);
  display.stopscroll();
}


