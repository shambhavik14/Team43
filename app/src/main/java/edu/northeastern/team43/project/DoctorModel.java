package edu.northeastern.team43.project;

import java.io.Serializable;

public class DoctorModel implements Serializable {
    public String doctorId;
    public String name;
    public String email;
    public String password;

    public String gender;
    public String dob;
    public String state;

    public DoctorModel(String doctorId,String name, String email, String password, String gender, String dob, String state){
        this.doctorId=doctorId;
        this.name=name;
        this.email=email;
        this.password=password;
        this.gender=gender;
        this.dob=dob;
        this.state=state;
    }
    public DoctorModel(){}

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class Builder implements Serializable{
        public String doctorId;
        public String name;
        public String email;
        public String password;

        public String gender;
        public String dob;
        public String state;

        public Builder doctorId(String doctorId){
            this.doctorId=doctorId;
            return this;
        }

        public Builder name(String name){
            this.name=name;
            return this;
        }
        public Builder email(String email){
            this.email=email;
            return this;
        }
        public Builder password(String password){
            this.password=password;
            return this;
        }
        public Builder gender(String gender){
            this.gender=gender;
            return this;
        }
        public Builder dob(String dob){
            this.dob=dob;
            return this;
        }
        public Builder state(String state){
            this.state=state;
            return this;
        }
        public DoctorModel build(){
            return new DoctorModel(this.doctorId,this.name,this.email,this.password,this.gender,this.dob,this.state);
        }
    }
}
