package org.example.crawer;

import com.google.gson.JsonObject;

public class HouseInfo {
    //标题名称
    private  String title;
    //房屋总价
    private  double total_price;
    //房屋单价（元/平米）
    private  long unit_price;
    //建筑面积
    private  double area;
    //房屋户型
    private  String house_type;
    //所属小区
    private  String community;
    //行政区（所在位置）
    private  String district;
    //所属街道（所在位置）
    private  String street_district;
    //地址（所在位置）
    private  String location;
    //建造年代
    private  int time;
    //房屋朝向
    private  String direction;
    //参考首付
    private  double down_payment;
    //参考月供
    private  long monthly_payment;
    //房屋类型
    private  String type;
    //所在楼层
    private  String floor;
    //装修程度
    private  String decorate;
    //房本年限
    private  int  property;
    //配套电梯
    private  String  elevator;
    //房本年限
    private  String use_time;
    //唯一住房
    private  String only;
    //产权性质
    private  String ownership;
    //核心卖点
    private  String selling_point;
    //链接
    private  String link;

    @Override
    public String toString() {
        JsonObject json = new JsonObject();
        json.addProperty("title",title);
        json.addProperty("total_price",total_price);
        json.addProperty("unit_price",unit_price);
        json.addProperty("area",area);
        json.addProperty("house_type",house_type);
        json.addProperty("community",community);
        json.addProperty("district",district);
        json.addProperty("street_district",street_district);
        json.addProperty("location",location);
        json.addProperty("time",time);
        json.addProperty("direction",direction);
        json.addProperty("down_payment",down_payment);
        json.addProperty("monthly_payment",monthly_payment);
        json.addProperty("type",type);
        json.addProperty("floor",floor);
        json.addProperty("decorate",decorate);
        json.addProperty("property",property);
        json.addProperty("elevator",elevator);
        json.addProperty("use_time",use_time);
        json.addProperty("only",only);
        json.addProperty("ownership",ownership);
        json.addProperty("selling_point",selling_point);
        json.addProperty("link",link);
        return json.toString();
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public double getTotal_price() {
        return total_price;
    }
    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
    public long getUnit_price() {
        return unit_price;
    }
    public void setUnit_price(long unit_price) {
        this.unit_price = unit_price;
    }
    public double getArea() {
        return area;
    }
    public void setArea(double area) {
        this.area = area;
    }
    public String getHouse_type() {
        return house_type;
    }
    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }
    public String getCommunity() {
        return community;
    }
    public void setCommunity(String community) {
        this.community = community;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getStreet_district() {
        return street_district;
    }
    public void setStreet_district(String street_district) {
        this.street_district = street_district;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public double getDown_payment() {
        return down_payment;
    }
    public void setDown_payment(double down_payment) {
        this.down_payment = down_payment;
    }
    public long getMonthly_payment() {
        return monthly_payment;
    }
    public void setMonthly_payment(long monthly_payment) {
        this.monthly_payment = monthly_payment;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getFloor() {
        return floor;
    }
    public void setFloor(String floor) {
        this.floor = floor;
    }
    public String getDecorate() {
        return decorate;
    }
    public void setDecorate(String decorate) {
        this.decorate = decorate;
    }
    public int getProperty() {
        return property;
    }
    public void setProperty(int property) {
        this.property = property;
    }
    public String getElevator() {
        return elevator;
    }
    public void setElevator(String elevator) {
        this.elevator = elevator;
    }
    public String getUse_time() {
        return use_time;
    }
    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }
    public String getOnly() {
        return only;
    }
    public void setOnly(String only) {
        this.only = only;
    }
    public String getOwnership() {
        return ownership;
    }
    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }
    public String getSelling_point() {
        return selling_point;
    }
    public void setSelling_point(String selling_point) {
        this.selling_point = selling_point;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
}