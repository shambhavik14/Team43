package edu.northeastern.team43.project;

import java.io.Serializable;

public class PatientModel implements Serializable {
    public String patientId;
    public String name;
    public String email;
    public String password;
    public String gender;
    public String state;
    public String dob;

    public String profilePicture;

    public PatientModel(String patientId,String name, String email, String password,String gender,  String dob, String state, String profilePicture){
        this.patientId=patientId;
        this.name=name;
        this.email=email;
        this.password=password;
        this.gender=gender;
        this.state=state;
        this.dob=dob;
        this.profilePicture =profilePicture;
    }

    public PatientModel(){}

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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
        public String patientId;
        public String name;
        public String email;
        public String password;

        public String gender;
        public String dob;
        public String state;
        public String profilePicture;
        public PatientModel.Builder patientId(String patientId){
            this.patientId=patientId;
            return this;
        }

        public PatientModel.Builder name(String name){
            this.name=name;
            return this;
        }
        public PatientModel.Builder email(String email){
            this.email=email;
            return this;
        }
        public PatientModel.Builder password(String password){
            this.password=password;
            return this;
        }
        public PatientModel.Builder gender(String gender){
            this.gender=gender;
            return this;
        }
        public PatientModel.Builder dob(String dob){
            this.dob=dob;
            return this;
        }
        public PatientModel.Builder state(String state){
            this.state=state;
            return this;
        }
        public PatientModel.Builder profilePicture(String profilePicture){
            this.profilePicture=profilePicture;
            return this;
        }
        public PatientModel build(){
            return new PatientModel(this.patientId,this.name,this.email,this.password,this.gender,this.dob,this.state,this.profilePicture);
        }
    }
}
