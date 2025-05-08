package com.bnpp.wm.actimize.model;

public class Order {
    public int orderId;
    public int customerId;
    public int numberOfTickets;
    public double totalAmount;
    public int trainNumber;
    public String trainName;

    public Order(int orderId, int customerId, int numberOfTickets, double totalAmount, int trainNumber, String trainName) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.numberOfTickets = numberOfTickets;
        this.totalAmount = totalAmount;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
    }

    @Override
    public String toString() {
        return orderId + "," + customerId + "," + numberOfTickets + "," + totalAmount + "," + trainNumber + "," + trainName;
    }
}
