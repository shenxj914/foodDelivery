# Food delivery
This sample project creates a real-time system that emulates the fulfillment of delivery orders for a kitchen. 
Detail requirements can be found in the .pdf file.

## System Requirements
JDK 1.8
Maven Build tool (3.X.X)

## Build project
mvn clean package

## Run
java -jar target/foodDelivery-1.0-SNAPSHOT.jar

## Unit Test
to be added

## Code structure
- src
    - main
        - java
            - com.css.kitchen.application.Application //main thread, keep simulate order
            - com.css.kitchen.couriers.CourierService //courier thread, keep pick up order
            - com.css.kitchen.orders.OrderService // receive the order and cook, notify shelf and courier thread to handle order.
            - com.css.kitchen.shelves.ShelfService // shelf thread, receive the order and put on right shelf, or discard.
            - com.css.kitchen.orders.Order // java bean.
