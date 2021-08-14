# Iot Devices Management
The IotDevicesManagement project provides REST apis to retrieve and manipulate 2 million IOT devices that could be connected to sim cards and monitor those cards' statuses and connect the devices to the sim cards.

## Built With
- Spring Boot
- Maven
- SQL server database

## The exposed apis
- **getDevicesWaitingForActivation** to get a list of devices that are connected to sim cards with the status waiting for activation
- **updateDeviceConfiguration** to configure a device by updating its temperature and connecting it to a sim card
- **getDevicesAvailableForSale** to get an ordered list *ordered by id descending* of devices that have the tempereture between 25 and 85 and are connected to sim cards

The apis could be viewed through swagger2 after running or deploying the application via link  
http://localhost:8080/swagger-ui.html  
     *the port could be changed in [application.properties](IotDevicesManagement/src/main/resources/application.properties) file property server.port*

## Installation
To run the application import the project content under [IotDevicesManagement](IotDevicesManagement) to an IDE(Eclipse/Intellij) and run it as a spring boot application

## Database
The application uses sql server database which could be created through the provided [DbScript.sql](DbScript.sql) file.

## Unit Testing   
The project contains unit testing for the apis and the service layers using Junit5.

