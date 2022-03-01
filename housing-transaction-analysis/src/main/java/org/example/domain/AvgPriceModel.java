package org.example.domain;

public class AvgPriceModel {
    private String district;
    private double avg_price;
    public String getDistrict() {
        return district;
    }
    public double getAvg_price() {
        return avg_price;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public void setAvg_price(double avg_price) {
        this.avg_price = avg_price;
    }
    public AvgPriceModel(String district,double avg_price){
        this.district = district;
        this.avg_price = avg_price;
    }
    @Override
    public String toString() {
        return "AvgPriceModel [district=" + district + ", avg_price=" + avg_price + "]";
    }
}