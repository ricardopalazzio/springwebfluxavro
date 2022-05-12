package com.example.springwebfluxavro;

import org.apache.avro.generic.GenericRecord;

public class MonitoringRecord {

    private  String equipment;
    private  String  lat;
    private  String lng;
    
    public MonitoringRecord() {
    }

    public MonitoringRecord(String equipment, String lat, String lng) {
        this.equipment = equipment;
        this.lat = lat;
        this.lng = lng;
    }

    public static MonitoringRecord fromAvro(GenericRecord record){
       return  new MonitoringRecord(
            record.get("CD_EQUIPAMENTO").toString(),
            record.get("VL_LATITUDE").toString(),
            record.get("VL_LONGITUDE").toString());
    }

    public String getEquipment() {
        return equipment;
    }


    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }


    public String getLat() {
        return lat;
    }


    public void setLat(String lat) {
        this.lat = lat;
    }


    public String getLng() {
        return lng;
    }


    public void setLng(String lng) {
        this.lng = lng;
    }


    
    
}
