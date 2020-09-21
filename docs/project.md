Assignment 1.2
Selection of your application
Team ID: cs20-35

David Roelofsen s2002159  
Mauricio Merchan s2210037  
Vladimirs Popovs s2310651  
Yordan Tsintsov s2331993  
Viktor Tonchev s2332000  
Dimitar Popov s2310015  

## Introduction
The project we will be developing is a security system to control the state of doors or windows (i.e. closed or open) When armed (turned on), the system monitors the sensors connected to the security system (the Raspberry Pi - the Pi from now on), and if a sensor detects a door(or a window) was opened, sends an alarm notification to the users.
### Motivation
Most apartments don’t have a security system, or have one that can be disarmed from the inside of the apartment. Our system, however, is more secure, because if an intruder breaks in, they will not be able to disarm your security system, because it is not controlled from the inside.

###  Scope
In scope: 
A security system (software only) monitoring door(window) opening sensors
A simple web interface for operating the system
A step by step installation manual and sensor setup
Out of scope:
Sensor installation manual
Raspberry Pi setup manual
The MVP is to be delivered by October 16th.
The polished system (software, web interface, setup guide) is to be delivered before November 4th. 

### Limitations of the current system (if any)
A lot of the current security systems on the Pi lack an efficient and easy overview that can be setup easily. There are systems controlled from the terminal. However, we were not able to find a fully operational system that was easy to set up and use. We also did not find any systems with a web interface. 

### Objectives
Create an intuitive web interface.
Create a secure way of arming and disarming the system.
Create a system that allows simple expansion with additional sensors.
 
### Definitions and Abbreviations
The Pi - Raspberry Pi 3 / 4 computer.
Sensor - Magnetic contact sensor/relay
 
### Overview of the selected application
The application consists of two main features: the web interface and the sensor control. 
Initially, the user needs to set up the system. Connect the sensors and connect the Pi to the server. The user can then connect to their system via the internet. The web interface is used to arm or disarm the system. When the system user wants to arm the system, it checks the state of all connected sensors and verifies all are closed. If at least one of the sensors is not closed, the system will not arm.
The system is now armed. It monitors the state of all systems and if one becomes open while the system is still armed, sends a notification to the user. If the user wants to disarm the system, they need to enter the pin code. The system is now disarmed.

## Product Requirements
### Functional requirements
When the application detects a change through the sensor it notifies the user
System can be armed/disarmed through the web interface
### Nonfunctional requirements
 Easy to use web interface
Self installment of newly added sensors
Secure use of the product (e.g. 2-factor authentication)

## Conclusion
The project as a security system is not a complete security system. However, we are planning a modular project so that adding support for other types of sensors should be reasonably simple.

## Reference
https://www.ryansouthgate.com/2015/08/10/raspberry-pi-door-sensor/  
https://medium.com/conectric-networks/playing-with-raspberry-pi-door-sensor-fun-ab89ad499964  
https://tutorials-raspberrypi.com/raspberry-pi-door-window-sensor-with-reed-relais/  
