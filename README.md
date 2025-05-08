# Train Management System

## Overview
This is a simple console-based Train Management System built in Java. It provides functionalities for both admin and customers to manage and book train tickets. Data such as trains, customers, and orders are stored in CSV files.

## Features
- **Admin Panel**: 
  - Add/Delete Trains
  - View and manage all available trains

- **Customer Panel**:
  - View Available Trains
  - Search Trains based on Source and Destination
  - Book Train Tickets

## Project Structure
- **TrainType.java**: Enum class for train types (EXPRESS, LOCAL, SUPERFAST)
- **Train.java**: Model class representing a train
- **Customer.java**: Model class representing a customer
- **Order.java**: Model class representing an order for booking tickets
- **TrainService.java**: Service class that contains the business logic for managing trains, customers, and orders
- **TrainMgmtApp.java**: Entry point for the application

## File Storage
- **trains.csv**: Stores train data
- **customers.csv**: Stores customer information
- **orders.csv**: Stores order details

## Requirements
- Java 8 or higher
- File system to store CSV files

## Running the Application
1. Open the project in a Java IDE (e.g., IntelliJ IDEA or VS Code)
2. Run `TrainMgmtApp.java`
3. Follow the menu options in the console to use the system

## How It Works
The system uses file I/O for storing and reading data:
- Trains, customers, and orders are read from the respective CSV files.
- Data is processed and displayed to the user as per the action they choose.
- Any updates made by the user are written back to the CSV files to keep the data persistent.


## Running the Application
1.Compile the Java Files:
Open your command line (PowerShell, Terminal, or Command Prompt) and navigate to the project directory. Then, run the following command to compile all the Java files:
javac -d out/production/TrainMgmtApp src/main/java/com/bnpp/wm/actimize/*.java src/main/java/com/bnpp/wm/actimize/service/*.java src/main/java/com/bnpp/wm/actimize/enums/*.java src/main/java/com/bnpp/wm/actimize/model/*.java

2.Run the Application:
After compiling, run the following command to start the application:
java -cp out/production/TrainMgmtApp com.bnpp.wm.actimize.TrainMgmtApp
