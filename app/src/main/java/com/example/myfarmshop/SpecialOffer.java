package com.example.myfarmshop;

import java.util.Date;

public class SpecialOffer {

    private int farmId;
    private int type;  // 1- Discount, 2- Facility, 3- Event
    private String description;
    private Date dateSince;
    private Date dateUntil;
    private String farmName;  // Name of the farm the special offer belongs to
    private byte[] farmPicture;  // Picture of the farm the special offer belongs to
    private double farmLatitude;  // Latitude of farm the special offer belongs to
    private double farmLongitude; // Longitude of farm the special offer belongs to

    public SpecialOffer() {
    }

    public SpecialOffer(int farmId, int type, String description, Date dateSince,
                        Date dateUntil, String farmName, byte[] farmPicture, double farmLatitude, double farmLongitude) {
        this.farmId = farmId;
        this.type = type;
        this.description = description;
        this.dateSince = dateSince;
        this.dateUntil = dateUntil;
        this.farmName = farmName;
        this.farmPicture = farmPicture;
        this.farmLatitude = farmLatitude;
        this.farmLongitude = farmLongitude;
    }

    // Getters

    public int getFarmId() {
        return farmId;
    }
    public int getType() {
        return type;
    }

    // Returns the corresponding name of the object type
    public String getTypeName() {
        switch (type) {
            case 1:
                return "Discount";
            case 2:
                return "Facility";
            case 3:
                return "Event";
            default:
                return "Unknown";  // This should never be the case
        }
    }

    public String getDescription() {
        return description;
    }
    public Date getDateSince() {
        return dateSince;
    }
    public Date getDateUntil() {
        return dateUntil;
    }
    public String getFarmName() {
        return farmName;
    }
    public byte[] getFarmPicture() { return farmPicture; }
    public double getFarmLatitude() {
        return farmLatitude;
    }
    public double getFarmLongitude() {
        return farmLongitude;
    }

    // Setters

    public void setFarmId(int farmId) {
        this.farmId = farmId;
    }
    public void setType(int type) {
        this.type = type;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDateSince(Date dateSince) {
        this.dateSince = dateSince;
    }
    public void setDateUntil(Date dateUntil) { this.dateUntil = dateUntil; }
    public void setFarmName(String farmName) { this.farmName = farmName; }
    public void setFarmPicture ( byte[] farmPicture ){ this.farmPicture = farmPicture; }
    public void setFarmLatitude(double farmLatitude) {
        this.farmLatitude = farmLatitude;
    }
    public void setFarmLongitude(double farmLongitude) {
        this.farmLongitude = farmLongitude;
    }
}
