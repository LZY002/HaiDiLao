import json
import urllib
import urllib2
import time
import grovepi
import paho.mqtt.client as mqtt

button = 5
ledbar = 8
display = 5

HOST = "172.20.10.3"
PORT = 1884
clientid = 'jiehsouzhe'

grovepi.pinMode(button,"INPUT")
grovepi.pinMode(ledbar,"OUTPUT")
grovepi.pinMode(display,"OUTPUT")

grovepi.digitalWrite(button,1)

def on_connect(client, userdata, flags, rc):
        print("Connected with result code "+str(rc))

grovepi.ledBar_init(ledbar, 0)
grovepi.fourDigit_init(display)
grovepi.fourDigit_brightness(display,0)
grovepi.fourDigit_off(display)
time.sleep(.5)

leading_zero = 1
global information
global client
i=0

def on_message(client, userdata, msg):
        information = str(msg.payload)
        print(information)
        level=information[0]
        if(level == 'e'):
                print("No Person")
                grovepi.ledBar_setLevel(ledbar,0)
                grovepi.fourDigit_digit(display,0,9)
                grovepi.fourDigit_digit(display,1,0)
                grovepi.fourDigit_digit(display,2,0)
                grovepi.fourDigit_digit(display,3,125)
                time.sleep(5)
                 #grovepi.fourDigit_off(display)
                print("NEXT")

        else:
                num=information[1:]
                i=int(num)

                binary5=i//32
                binary6=(i%32)//16
                binary7=(i%16)//8
                binary8=(i%8)//4
                binary9=(i%4)//2
                binary10=i%2

                print("Client "+information)

                if(level == 'A'):
                        grovepi.ledBar_setLevel(ledbar,3)
                        grovepi.ledBar_setLed(ledbar, 5 ,binary5)
                        grovepi.ledBar_setLed(ledbar, 6 ,binary6)
                        grovepi.ledBar_setLed(ledbar, 7 ,binary7)
                        grovepi.ledBar_setLed(ledbar, 8 ,binary8)
                        grovepi.ledBar_setLed(ledbar, 9 ,binary9)
                        grovepi.ledBar_setLed(ledbar, 10 ,binary10)
                        grovepi.fourDigit_digit(display,0,10)
                        grovepi.fourDigit_digit(display,3,i)
                        time.sleep(5)
                        #grovepi.fourDigit_off(display)
                        print("NEXT")

                if(level == 'B'):
                        grovepi.ledBar_setLevel(ledbar,2)
                        grovepi.ledBar_setLed(ledbar, 5 ,binary5)
                        grovepi.ledBar_setLed(ledbar, 6 ,binary6)
                        grovepi.ledBar_setLed(ledbar, 7 ,binary7)
                        grovepi.ledBar_setLed(ledbar, 8 ,binary8)
                        grovepi.ledBar_setLed(ledbar, 9 ,binary9)
                        grovepi.ledBar_setLed(ledbar, 10 ,binary10)
                        grovepi.fourDigit_digit(display,0,11)
                        grovepi.fourDigit_digit(display,3,i)
                        time.sleep(5)
                        #grovepi.fourDigit_off(display)
                        print("NEXT")

                if(level == 'C'):
                        grovepi.ledBar_setLevel(ledbar,1)
                        grovepi.ledBar_setLed(ledbar, 5 ,binary5)
                        grovepi.ledBar_setLed(ledbar, 6 ,binary6)
                        grovepi.ledBar_setLed(ledbar, 7 ,binary7)
                        grovepi.ledBar_setLed(ledbar, 8 ,binary8)
                        grovepi.ledBar_setLed(ledbar, 9 ,binary9)
                        grovepi.ledBar_setLed(ledbar, 10 ,binary10)
                        grovepi.fourDigit_digit(display,0,12)
                        grovepi.fourDigit_digit(display,3,i)
                        time.sleep(5)
                        #grovepi.fourDigit_off(display)
                        print("NEXT")






def table_size():
        while True:
                try:
                        #print(grovepi.digitalRead(button))
                        if(grovepi.digitalRead(button)==0):
                                time.sleep(1)
                                if(grovepi.digitalRead(button)==1):
                                        print("small")
                                        return "small"
                                else:
                                        time.sleep(2)
                                        if(grovepi.digitalRead(button)==1):
                                                print("middle")
                                                return "middle"
                                        else:
                                                time.sleep(3)
                                                if(grovepi.digitalRead(button)==1):
                                                        print("big")
                                                        return "big"
                                                else:
                                                        print("WAIT 5 SECONDS and PRESS AGAIN ")
                                                        time.sleep(5)
                                                        print("NEXT")
                                                        return "No"
                        return "No"

                except KeyboardInterrupt:
                        grovepi.ledBar_setBits(ledbar, 0)
                        break
                except IOError:
                        print ("Error")
                        time.sleep(3)



def getvalue():
        size = table_size()
        while(size=="No"):
                size=table_size()
        client.publish("nextperson",size,0,False)
        time.sleep(1)
        #client.on_message = on_message
        #client.loop_forever()

client=mqtt.Client()
client.connect(HOST,PORT,60)
client.subscribe('nextsmallpersonnumber')
client.subscribe('nextmiddlepersonnumber')
client.subscribe('nextbigpersonnumber')
client.on_connect=on_connect
client.on_message = on_message
client.loop_start()


while True:

        try:
                getvalue()

        except KeyboardInterrupt:
                grovepi.ledBar_setBits(ledbar, 0)
                break
        except IOError:
                print ("Error")
                time.sleep(3)



