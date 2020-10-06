# Software Design Document Template

|                 | **Names/Ids**  |
|----------------:|:---------------|
| *Team members:* |s2002159, s2210037, s2310651, s2331993, s2332000, s2310015 |
| *Team ID:*      |cs20-35|

**Instructions:**
* Make a document by including all the suggested sections.
* The document should be 1500-2500 words.
 
## Introduction

Most apartments don’t have a security system, or have one that can be disarmed from the inside of the apartment. Therefore, we thought it would be a nice project to create a security system for the home wit hthe help of Raspberry Pi. The project will be a security system with a web interface. The raspberry pi will be the hub of connected sensors, and the user will be able to see the current state of the sensors and control the state of system through a web application. 

Below you find the extended description in:
    User interface
    Requirements and system overview
    Milestones
    Requirement priorities
    Conclusions
    References

**(The overview of the entire document is mentioned.)**

## Product User Interface

The general idea for the user interface we are following is to make it as simple and intuitive as possible. That means we want to provide the user with only the most important information such as the current state of the whole system. 

**(Create illustrations and design of your product and explain the tools used for the design. Include UML diagrams of your application for better understanding.)**

* Wireframing Tools 
* UML diagrams

## Requirements/System Overview 
Due to limited timeframe we prioritized these requirements as the build up the MVP.
### Functional requirements
When the application detects a change through the sensor it notifies the user
System can be armed/disarmed through the web interface
### Nonfunctional requirements
Easy to use web interface
Self installment of newly added sensors
Secure use of the product (e.g. 2-factor authentication)

**(Overall functionality of the system is explained with user requirements and functional requirements. Describe the scenario when your system can fail.)**
The application consists of two main features: the web interface and the sensor control. 
Initially, the user needs to set up the system. Connect the sensors and connect the Pi to the server. The user can then connect to their system via the internet. The web interface is used to arm or disarm the system. When the system user wants to arm the system, it checks the state of all connected sensors and verifies all are closed. If at least one of the sensors is not closed, the system will not arm.
The system is now armed. It monitors the state of all systems and if one becomes open while the system is still armed, sends a notification to the user. If the user wants to disarm the system, they need to enter the pin code. The system is now disarmed.

## Milestones
The main milestones we identified is the finishing of the MVP. That means the basic web application is ready, and the pi and sensors are providing the correct information. - The MVP is expected to be delivered by October 16th.

This we consider the most important milestone, after this date we are planning to be testing our application for bugs as well as improving the visual aesthetics of the web application.

We are expecting to be fully finished with project and the presentation by November 4th. Before this date, the application is expected to be completed and working as expected under the specified instructions.

**(This is basically to understand the project scope. It should be described according to the number of jobs required for your application to the final deliverable.)**

## Requirement Priorities

**(You are required to prioritize the functionalities to build your Minimal Viable Product. It should be stated clearly.)**

## Conclusion

**(You can give the concluding statements of your document.)**

## Reference