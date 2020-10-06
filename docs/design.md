# Software Design Document

|                 | **Names/Ids**  |
|----------------:|:---------------|
| *Team members:* |s2002159, s2210037, s2310651, s2331993, s2332000, s2310015 |
| *Team ID:*      |cs20-35|

## Introduction

Most apartments don’t have a security system, or have one that can be disarmed from the inside of the apartment. 
Therefore, we thought it would be a nice project to create a security system for the home with the help of Raspberry Pi.
The project will be a security system with a web interface. The raspberry pi will be the hub of connected sensors, 
and the user will be able to see the current state of the sensors and control the state of system through a web application. 
 
Below you find the extended description in:
* User interface
* Requirements and system overview
* Milestones
* Requirement priorities
* Conclusions
* References

For reference, in the gitlab repository, UML diagrams and a mock-up are included for better understanding. Use at your own discretion.
UML(Use case diagram, State machine diagram, Class diagram): https://gitlab.utwente.nl/cs20-35/mod5/-/tree/master/docs/uml
Mock-up: https://gitlab.utwente.nl/cs20-35/mod5/-/tree/master/docs/mockup

## Product User Interface
For the mock-up of the web application we are using Mockplus Classic.

The general idea for the user interface we are following is to make it as simple and intuitive as possible.
That means we want to provide the user with only the most important information such as the current state of the system, sensors.
Next to that, the user can arm/disarm the system and change their pin code.  

When initially opening the web app, you are presented with a login form. The user logs in with passcode. In essence, there is only one user for the system. 
If the passcode is correct, the user is redirected to the main page where they can see the state of all sensors connected and if the system is armed or disarmed.

On a separate page, the user will be able to add and remove emails to receive notifications.

Those are visualised in the mock-ups provided in the folder 'Mock-up'.

Notification means - we identified the following default as well as additional means of system-user communication.
Additional methods of communication can be implemented if the final product is ready before the final deadline specified below. 
If the additional methods of communication are implemented and tested successfully before the final deadline, they are also to be added to the final product.

Default and considered part of the MVP:
* Email notifications,
* Web alerts.

Additional:
* SMS notifications.
* Telegram notifications. 

Other means of communication not specified in this document are considered out of scope.

## Requirements/System Overview 
The system is controlled from the web application and has three main states: disarmed, armed, alarm. 

The user can arm or disarm the system, depending on the state. To do that, the user needs to provide the pin code. The default pincode is 0000 and the user can change the pin code to the desired.

To arm the system, the user must close all the doors(windows) - so that all sensors show as closed in the web interface, press the arm button and enter the correct pin.
If all three requirements are satisfied, the system changes its state to arm.

While the system is armed, if a sensor changes its state to open, the system moves to Alarm state. That means all users are notified by notification means specified below that the system is now in alarm state.
The alarm state also shows on the web page. To cancel the alarm state, the user must login to the web app, press 'cancel alarm' enter their pin code. The state of the system is now disarmed.

While the system is armed, if the user presses disarm and enters the correct pin code, the state changes to disarmed.

### Functional requirements
The delay between the change of state of sensor and change of state in the web app are less than 1 second.
When the system is armed and detects a change through the sensor it notifies the user via email and web app.
System can be armed/disarmed through the web interface.
#### Extensions 
When the system is armed and detects a change through the sensor it notifies the user via SMS and Telegram.

### Nonfunctional requirements
The login process is secure and not injectable.
The users passwords are stored in a secure (hash + salt) manner.
Easy to use web interface.
The web design is visually pleasing.
#### Extensions 

The application consists of two main features: the web interface and the sensor control. 
Initially, the user needs to set up the system. Connect the sensors and connect the Pi to the server. The user can then connect to their system via the internet. The web interface is used to arm or disarm the system. When the system user wants to arm the system, it checks the state of all connected sensors and verifies all are closed. If at least one of the sensors is not closed, the system will not arm.
The system is now armed. It monitors the state of all systems and if one becomes open while the system is still armed, sends a notification to the user. If the user wants to disarm the system, they need to enter the pin code. The system is now disarmed.

## Milestones
The first milestone we identify is being able to read data and interpret it correctly from the sensor. That means that dependent on the output of the Pi we are 100% sure of the current state of the sensor.

The second milestone we identified is finishing the basic web application. That means the web app displays the current state of the system.

Next milestone is the ability to arm/disarm the system. That means that the user can arm the system or disarm it, and the system can enter alarm state if a sensor was opened while the system was armed. 

Final step before the MVP will be to add support for notifications. If the system goes to alarm state, the users receive email notifications.

The main milestones we identified is the finishing of the MVP. 
That means the basic web application is ready, and the pi and sensors are providing the correct information, the user can arm/disarm the system and receives notifications if the system is in alarm state. - The MVP is expected to be delivered by October 16th.

This we consider the most important milestone, after this date we are planning to be testing our application for bugs as well as improving the visual aesthetics of the web application.

We are expecting to be fully finished with project and the presentation by November 4th. Before this date, the application is expected to be completed and working as expected under the specified instructions. 

## Requirement Priorities

For the MVP we prioritize the sensor manipulation. - that is also represented by out first milestone. One of two main features of our application is the sensors.
Without the sensors there wouldn't be any security system, so setting up the sensorts will be our number one priority.

Before starting on the web application, we need to be certain that the backend sensor control is behaving as expected by testing. That will ensure we are not having any strange bugs in the future. 

Our next priority will be the web app. When we are certain the sensor functionality behaves correctly, we can start working on the webapp. 
As represented by the next milestone, our next priority will be to have the web app display the sensor status online.  

When the system can display the sensor status online, we can start the development of arm/disarm and alarm state.

The next priority will be the notification system.

By completing the notification system, the MVP will be ready.

After the testing of the MVP is complete and shows expected operation, the development moves to the visual design of the web app.
The team wants to see the visuals of the web app close to the design in the mock-up.

When the design phase for the MVP is finished, the development team will shift to implementing additional requirements. 
That includes (possibly and not necessarily) SMS notifications, Telegram notifications, support for additional sensors.

## Conclusion
The security system solutions available on the market are usually much more extensive and cover a lot more functionality, 
however, the concept of our system, we think, is very similar. A simple and secure solution, 
but due to a highly limited timeframe, we are constraint in resources to implement more functionality, allow several systems to work as one, or allow simple
addition of more sensors. However, we will still follow a 'modular' approach to allow, if the schedule allow us, simple extension of the functionality. 

## Reference
https://www.ryansouthgate.com/2015/08/10/raspberry-pi-door-sensor/  
https://medium.com/conectric-networks/playing-with-raspberry-pi-door-sensor-fun-ab89ad499964  
https://tutorials-raspberrypi.com/raspberry-pi-door-window-sensor-with-reed-relais/ 