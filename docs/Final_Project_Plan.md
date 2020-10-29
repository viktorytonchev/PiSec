# Final Project Plan report

|                  | **Names/Ids**  |
|-----------------:|:---------------|
| *Team ID:*       |cs20-35|
| *Team Members:*  |Yordan Tsintsov, Viktor Tonchev, Dimitar Popov, Vladimirs Popovs, Mauricio Merchan,David Roelofsen|
| *Scrum  Mentors:*|Triet Ngo, Alexander Stekelenburg|

**Project Title:**
PiSec
## 1. Introduction and Background

There is nothing worse than coming back from a vacation or a visit to your friends and noticing that there have been people breaking into your house and stealing all of your valuable possessions. Burglaring is a well-known term nowadays. We want to protect our belongings as best as we can. That is why we can lock our doors and windows so that nobody can enter our house without permission. Security systems can vary from a simple door-lock to a digital barrier at for example datacenters.

According to a survey conducted on 3000 Americans, 38% of the people have a home security product installed in their home (Ferron, 2020). This is by far not everyone. The pros of security systems are obvious, but there can be reasons why people do not want to get this extra level of security in their houses. A few causes are that it can be difficult to set up, there can be false alarms which can be annoying if you need concentration or are in a hurry, or it can be too expensive. Prizes of these systems are often higher than €300 and can easily reach above €1000, which can be an obstacle for a lot of people (Perez, 2019). 

The aim of this project is to create a security system (PiSec) that detects the illegal accessing of the house/room that the system is used on. The system must be easy to set up and understand. The system must have a clear user interface like a web application. Also the user must get notified when the alarm goes off. The price of the system must be kept low in order to make it affordable for everyone.


## 2. Project Scope

The security system will be made using a raspberry pi. The system will be called PiSec, short for Pi Security. 

Security systems are sometimes difficult to set up and understand. Also the interfaces can be ugly and not really effective and clean. The price of security systems is very high most of the time. PiSec will try to solve these problems.

The goal is to have a system that involves sensors that check if the doors/windows are being opened (depends on where the sensors are placed). Expected is that people will use the sensors in the front- and backdoor most of the time so this will be used often for examples.  The results will be monitored and displayed in a simple web interface, which can be used for operating the system. A step by step installation manual and sensor setup must be made as well.

The exact product requirements have to be identified. This will be done before September 21th. This will also be pitched in order to get feedback on our system. The software design document will be ready before October 6th. The minimal valuable product (MVP) is to be delivered by October 16th. The final polished system, which includes the software, the web interface and the setup guide is to be delivered before November 4th.

There will be weekly meetings to catch up with what everyone has done. Also there is a scrum meeting once or twice a week where a scrum meeting is being held, which means that the next three questions are asked to every group member:
1.What did you do since last meeting?
2.What will you do till the next meeting?
3.Do you experience or expect to experience any problems with your tasks?
At these scrum meetings there will be a student assistant involved so questions about the, for example, deliverables can be asked. The answers will be saved so they can be viewed at any possible time.

The tools that will be used for communication and storing all documents and software are:
1.Gitlab (for documents, code and meeting reports).
2.Discord and Whatsapp (for communication between team members).
3.Google Drive (for documents as well).


## 3. Brief description of the hardware (Raspberry Pi) and software

Hardware
For the hardware part of the system we use a Raspberry Pi 4 which will control the reed switch sensors. These reed switches are triggered when the door/window is opened. The reed switch makes use of a magnetic field and two parts that are connected when the door is closed and disconnected when the door is open.

List of all hardware:
Raspberry Pi 4 - a small single-board computer
Reed switch sensors - a sensor using an electrical switch operated by an applied magnetic field

Software
The software part of the system uses PostgreSQL as a database and phppgadmin to edit and update the database. IntelliJ IDEA is used for software development in Java. For the Pi and it’s GPIO, Raspberry Pi OS and RPI GPIO Python library are used. Because Python is used, psycopg2 is also necessary for database management. 

List of all software:
PostgreSQL -  a free and open-source relational database management system
phppgadmin -  a web-based administration tool for PostgreSQL
IntelliJ IDEA - an integrated development environment written in Java for developing computer software
Raspberry Pi OS - a Debian-based operating system for Raspberry Pi
RPI GPIO Python library - a module to control Raspberry Pi GPIO channels using Python scripts
psycopg2 - a PostgreSQL database adapter for the Python programming language


## 4. Application Specification

The application consists of two main features: the web interface and the sensor control. To use the product, the user first has to set up the system. To achieve that the sensors need to be connected. The Pi needs to be connected to the server as well. The user can then connect to their system via the internet. 
Control over the system is achieved through the use of a web application. Users can log into the system by entering a password. If an incorrect password is provided the user will be redirected to a new page where he/she will be informed that an incorrect password was entered and will be provided with a link leading back to the login page.
Once the user has entered the web app he/she can see the system information on the left side of the main page and can change the settings of the system on the right side.
The system information side shows a list of the sensors and their state:  closed or open. If the alarm is triggered, the whole page will start flashing in red showing the message “ALARM” and will want a PIN in order to stop the system. 
On the right side the user can see a settings window where the system can be armed/disarmed. Below the arm/disarm, there is a change PIN button so that a user can change their PIN for the alarm and at the bottom there is an edit email button. 
The edit button takes the user to a new page where he/she can add and remove emails. On the email page there is an add email window where users can enter the email they want to add to the system and then add it with the “Add” button. 
Below the “Add E-mail” window users can see a list of all the emails currently subscribed to the system. Every email on the list has a remove button next to it. Moreover, emails subscribed to the system receive notifications informing them if; the alarm was triggered, their email was removed or their PIN was changed. Emails also get a notification if they are added to the system.


## 5. Design (How to map application onto Hardware and Software)

The design of the product consists of a mockup, database schema and 3 different types of UML diagrams(state machine, class and use case).

The mockup is a scale or full-size model of a design or device, used for teaching, demonstration, design evaluation, promotion, and other purposes. Our mockup provided us with a basic idea of how to start off the system interface and helped us design the overall page layout.

State machine diagram is a behavior diagram which shows discrete behavior of a part of a designed system through finite state transitions. Through the state machine diagram users can see the lifecycle of our system and get an accurate idea of how system states are changed.
Our state machine has three states - system armed, system disarmed and alarm state. The armed and alarm states always lead to the disarmed state and the armed state leads to both other states, depending on whether the user disarms the system or the alarm is triggered.

A use case diagram at its simplest is a representation of a user's interaction with the system that shows the relationship between the user and the different use cases in which the user is involved.  Use case diagrams provide users with all the possible interactions they could have with the system. 
Our use case diagram consists of two actors - the user and the system. The user can arm/disarm the system and can change the PIN. Once the system is armed by the user, users can also check their notifications and possibly - stop the alarm if it is triggered.
The system, when armed, is always checking if the sensors are triggered. If they are triggered, the system will set off the alarm and afterwards - send a notification that the alarm is triggered.

With a combination of use case and state machine diagrams users can easily get an idea of how the system works and help them learn how to operate with the provided interface.

Class diagrams are a type of static structure diagram that describes the structure of a system by showing the system's classes, their attributes, operations (or methods), and the relationships among objects. Through our class diagrams one can better understand the code of the product. They are also used for data modelling. The database used for the project is derived from the class diagram making it a building block and a core part of designing the system.
Our class diagram consists of four classes - Sensor, System, Email and Notifications. The way they are connected is as follows:
Many Notifications are sent to one or more Emails.
One System notifies many Emails.
One System oversees many Sensors.
Every class has a primary key(PK). The Notifications class uses nid as a PK, Email - eid as PK, System - sid. Sensor uses a combination of the FK sid and name as a PK.

The database schema of a database is its structure described in a formal language supported by the database management system. The term "schema" refers to the organization of data as a blueprint of how the database is constructed. Our schema is another core element when it comes to building the system as a whole. Without a schema there wouldn’t be a database to store the users and their passwords.
Our schema takes the information provided from the class diagram and creates a table for every class plus a table “pisec.is_sent_to”. The schema also adds the necessary constraints for the tables.

In the appendix, readers can find all the relevant material concerning the project design.


## 6. Implementation (How to implement the different parts)

The execution of this project will be handled in phases:
1.Requirement Analysis
2.Design
3.Implementation
4.Testing

Before these phases will start there is expected from the project members that they know the basics of the Raspberry Pi that will be used, the programming languages that will be used and how the project is scheduled in order to be effective and efficient. These phases will be split up into sprints. These sprints are two weeks in which you have to work on one of the phases (or multiple sometimes) from above. After these weeks you will report your improvements and will receive feedback which you can use for the next sprint/phase

The timeline of these phases/sprints for this project, including the assignments that have to be completed in these sprints, is as follows:

|   **Phase/Sprint**  | **Starts**  |  **Ends**   | **Assignment**  |
|-----------------:|:---------------|-----------------:|:---------------|
| Planning       |August 31|September 13  |Get familiar with the Pi|
| Requirement Analysis/Sprint 1  |September 14|September 27 |Requirement analysis, setup of a GitLab repository, Project epics, User stories|
| Design and Development/Sprint 2|September 28|October 11|Software design document, Team contract, Upload project mockup to GitLab|
| Development/Sprint 3    |12 October|October 18|MVP demo of 5 minutes (+/- 2), Upload all materials used to demonstrate the product increment to GitLab|
| Development/Sprint 4  |October 26|October 30  |MVP demo of 5 minutes (+/- 2), Upload all materials used to demonstrate the product increment to GitLab, Write this report!|
| Closure/Sprint 5|November 2|November 6 |Upload all the materials used to demonstrate the final product to GitLab, MVP demo of 5 minutes (+/- 2)|

Table : Timeline of the phases and sprints including the assignments that have to be completed. The dates in the table are all in the year ‘2020’.

In sprint 1 the requirements are set up. This includes functional and nonfunctional requirements of the security system. When these are clear, user stories can be made about what the function is of the software that will be implemented inside the system. When the system we want to build is all clear, the second sprint starts.

In the second sprint a mockup will be made that will show us what the end result is going to be. This mockup will be lo-fi which means low fidelity. This means that it is easy to change some properties that the system has. This way the system can be improved fast. A software design document will be made. This is an overview of how the software will be structured in order for the final product to work properly. A plan is made on what parts are needed and this will be created during next sprints. A team contract will be made. In this you as a group (in this case; us) will create rules which we all agree on. This can be punishments if somebody is late, or rules that let the group respect each other. Just to have everything structured and clear.

In the middle of the second sprint and the third and fourth sprint, the development of the product will start. A planning will be made and the tasks will be divided every sprint. After each sprint there is room to get feedback out of presentations of our product. A video about the minimum viable product (MVP) will be created to demonstrate how it works / what it will look like. Nearing the end of the fourth sprint, this report will be created to put everything that has been done and used on paper. 

The 4th of November the finalized polished system has to be ready. There needs to be an MVP demo video as well, which demonstrates the final product. This has to be done in sprint five in which everything has to be finalized.


## 7. Testing

The testing of the web application consists of two major methods. The first one is rather obvious: launching the web application and the python code and testing everything manually to make sure it works at the time of deployment. For example, launching the web app, checking whether the information on the website correctly reflects the state of the system and the sensors in the database, testing the arm/disarm, PIN change, notifications, etc. In other words, checking all the basic functionalities of the system. This is done during development, whenever implementing a new feature. First checking that the new feature is functional and then checking that it does not interfere with the functionality of the previously implemented features.

The second method is using the Jersey and Junit frameworks. Jersey is a framework that provides support for JAX-RS APIs. We use it as a means of creating GET and POST requests to use for the testing of the web application. Junit is a framework that allows for automatic testing of Java classes. We use it in combination with Jersey to make automatic tests of the functions used to implement the server of the web application. All the GET and POST methods used are tested using Jersey and assertions. 

The testing of the Python code covering the sensor part of the project turned out harder to deal with. This is because it requires additional hardware setup before it can be done. In the end we concluded there was no way to make automatic testing and settled for manual testing. For example using an LED to simulate current running through the sensor, using the GPIO libraries and checking whether changes in the sensor are reflected on the Pi and then in the database. This would be very hard and impractical to do automatically so it has to be done manually.


## 8. Planning and definition of tasks for the individual members

For the planning we have extra meetings outside of the mandatory ones to discuss division of tasks, when we’re going to work and anything else related to the project. For this we use discord for communication during meetings and during the agreed working hours, and whatsapp to set these meetings up or if anything else comes up.

For the division of tasks, we considered the Belbin roles of our team members and came up with the following planning according to what everyone's abilities are (Belbin, 2020):

|   **Task**       | **Person**     |
|-----------------:|:---------------|
| *Web application back-end*       |Viktor, Dimitar, Mauricio, Vladimirs|
| *Web application front-end*  |Viktor, Dimitar, Mauricio|
| *Sensor implementation with python*|Viktor, Vladimirs|
| *Testing*       |Viktor, Dimitar, Vladimirs|
| *Design*  |Dimitar, Mauricio, David, Yordan|
| *Documentation and others*|David, Yordan|

Table : Task division of individual team members according to the Belbin roles given to everyone.

## 9. Conclusion

The security system solutions available on the market are usually much more extensive and cover a lot more functionality, however, the concept of our system, we think, is very similar. A simple and secure solution, but due to a highly limited timeframe, we are constrained in resources to implement more functionality, allow several systems to work as one, or allow simple addition of more sensors. However, we will still follow a 'modular' approach to allow, if the schedule allows us, simple extension of the functionality.

##  Reference

##   I. Acknowledgement

Thanks to Triet Ngo and Alexander Stekelenburg for being present at our scrum meetings as student assistants and giving help to us where needed.
Thanks to all the students for their effective and positive feedback.
Special thanks to Dipti for her fast and effective responses.

##   II. Reflection

The overall experience for the team was quite positive. All the team members are happy with the current progress. All the deadlines and requirements were met and all the presentations were overall successful. The team addressed internal issues and conflicts and solved them easily. Moreover, all the members actively participated in the project development, provided useful ideas and tried to help each other when faced with an issue.

##   III. Appendix and Glossary

Pi - Raspberry Pi
MVP - Minimum viable product
PiSec - Name of our security system
PK - primary key
FK - foreign key
Junit - a framework used for automatic testing of java classes
Jersey - a framework used for the implementation of RESTFUL web services

UML Diagrams: https://gitlab.utwente.nl/cs20-35/mod5/-/tree/master/docs/uml
Mockup:
https://gitlab.utwente.nl/cs20-35/mod5/-/tree/master/docs/mockup
Bibliography
Belbin. (2020, October 28). The Nine Belbin Team Roles. The Nine Belbin Team Roles. Retrieved October 28, 2020, from https://www.belbin.com/about/belbin-team-roles/
Ferron, E. (2020, October 12). Only 38% of Americans own a home security product - and maybe not for the reasons you think. Only 38% of Americans own a home security product - and maybe not for the reasons you think. Retrieved October 28, 2020, from https://www.safety.com/home-security-survey/
Perez, K. (2019, March 13). The Pros, Cons, and Economics of Home Security Systems. The Pros, Cons, and Economics of Home Security Systems. Retrieved October 28, 2020, from https://centsai.com/life/real-estate/real-estate-blogs/pros-and-cons-of-home-security-systems/
