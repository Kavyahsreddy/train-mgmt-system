package com.bnpp.wm.actimize.model;

import com.bnpp.wm.actimize.enums.TrainType;
import java.util.List;

public class Train {
    public int trainNumber;
    public String trainName;
    public String source;
    public String destination;
    public List<String> subStops;
    public TrainType type;
    public double price;
    public int totalSeats;

    public Train(int number, String name, String source, String dest, List<String> subStops, TrainType type, double price, int seats) {
        this.trainNumber = number;
        this.trainName = name;
        this.source = source;
        this.destination = dest;
        this.subStops = subStops;
        this.type = type;
        this.price = price;
        this.totalSeats = seats;
    }

    @Override
    public String toString() {
        return trainNumber + "," + trainName + "," + source + "," + destination + "," + type + "," + price + "," + totalSeats;
    }
}
