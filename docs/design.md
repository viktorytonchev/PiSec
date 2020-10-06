# Software Design Document Template

|                 | **Names/Ids**  |
|----------------:|:---------------|
| *Team members:* |s2002159, s2210037, s2310651, s2331993, s2332000, s2310015 |
| *Team ID:*      |cs20-35|

**Instructions:**
* Make a document by including all the suggested sections.
* The document should be 1500-2500 words.
 
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
The system is controlled from the web application. 

The user can arm or disarm the system, depending on the state. To do that, the user needs to provide the pin code. The default pincode is 0000 and the user can change the incode to the desired.

To arm the system, the user must close all the doors(windows) - so that all sensors show as closed in the web interface, press the arm button and enter the correct pin.
If all three requirements are satisfied, the system changes its state to arm.

While the system is armed, if a sensor changes its state to open, the system moves to Alarm state. That means all users are notified by notification means specified below that the system is now in alarm state.
The alarm state also shows on the web page. To cancel the alarm state, the user must login to the web app, press 'cancel alarm' enter their pin code. The state of the system is now disarmed.

### Functional requirements
The delay between the change of state of sensor and change of state in the web app are less than 1 second.
When the application detects a change through the sensor it notifies the user.
System can be armed/disarmed through the web interface.

### Nonfunctional requirements
Easy to use web interface.
The login process is secure and not injectable.
The users passwords are stored in a secure (hash + salt) manner.

The application consists of two main features: the web interface and the sensor control. 
Initially, the user needs to set up the system. Connect the sensors and connect the Pi to the server. The user can then connect to their system via the internet. The web interface is used to arm or disarm the system. When the system user wants to arm the system, it checks the state of all connected sensors and verifies all are closed. If at least one of the sensors is not closed, the system will not arm.
The system is now armed. It monitors the state of all systems and if one becomes open while the system is still armed, sends a notification to the user. If the user wants to disarm the system, they need to enter the pin code. The system is now disarmed.

## Milestones
The first milestone we identify is being able to read data and interpret it correctly from the sensor. That means that dependent on the output of the Pi we are 100% sure of the current state of the sensor.

The second milestone we identified is finishing the bare web application. That means the web app displays the current state of the system.

Next milestone is the ability to arm/disarm the system. That means that the user can arm the system or disarm it. 

The main milestones we identified is the finishing of the MVP. That means the basic web application is ready, and the pi and sensors are providing the correct information. - The MVP is expected to be delivered by October 16th.

This we consider the most important milestone, after this date we are planning to be testing our application for bugs as well as improving the visual aesthetics of the web application.

We are expecting to be fully finished with project and the presentation by November 4th. Before this date, the application is expected to be completed and working as expected under the specified instructions.

## Requirement Priorities

For the MVP we prioritize the sensor manipulation. 


Add telegram bot.
**(You are required to prioritize the functionalities to build your Minimal Viable Product. It should be stated clearly.)**

## Conclusion

**(You can give the concluding statements of your document.)**

## Reference