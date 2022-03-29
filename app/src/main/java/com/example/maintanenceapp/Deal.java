package com.example.maintanenceapp;

public class Deal {

    private String AppointmentId;
    private String AppointmentTime;
    private String AppointmentDate;
    private String CarRegisterNum;
    private String CarType;
    private String status;
    private String userid;
    private String email;
    private String ServiceType;
    private String ServiceMileage;
    private String ServiceCost;



    public Deal() {
    }

    public Deal(String AppointmentId, String AppointmentTime, String AppointmentDate, String CarRegisterNum, String CarType, String status, String userid, String email, String ServiceType, String ServiceMileage, String ServiceCost) {
        this.AppointmentId = AppointmentId;
        this.AppointmentTime = AppointmentTime;
        this.AppointmentDate = AppointmentDate;
        this.CarRegisterNum = CarRegisterNum;
        this.CarType = CarType;
        this.status = status;
        this.userid = userid;
        this.email = email;
        this.ServiceType = ServiceType;
        this.ServiceMileage= ServiceMileage;
        this.ServiceCost = ServiceCost;

    }

    public String getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        AppointmentId = appointmentId;
    }

    public String getAppointmentTime() {
        return AppointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        AppointmentTime = appointmentTime;
    }

    public String getAppointmentDate() {
        return AppointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        AppointmentDate = appointmentDate;
    }

    public String getCarRegisterNum() {
        return CarRegisterNum;
    }

    public void setCarRegisterNum(String carRegisterNum) {
        CarRegisterNum = carRegisterNum;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getServiceMileage() {
        return ServiceMileage;
    }

    public void setServiceMileage(String serviceMileage) {
        ServiceMileage = serviceMileage;
    }

    public String getServiceCost() {
        return ServiceCost;
    }

    public void setServiceCost(String serviceCost) {
        ServiceCost = serviceCost;
    }

}
