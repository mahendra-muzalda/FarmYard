package com.example.khetibadi.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String machineName;
    private String machineType;
    private String location;
    private Double pricePerHour;

    private Double latitude;
    private Double longitude;

    private String status; // Available, Rented, Under Maintenance

    @ManyToOne
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getMachineName(){
        return machineName;
    }

    public void setMachineName(String machineName){
        this.machineName = machineName;
    }

    public String getMachineType(){
        return machineType;
    }

    public void setMachineType(String machineType){
        this.machineType = machineType;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public Double getPricePerHour(){
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour){
        this.pricePerHour = pricePerHour;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
