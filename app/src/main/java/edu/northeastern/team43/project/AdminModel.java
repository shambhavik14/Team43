package edu.northeastern.team43.project;

import java.io.Serializable;

public class AdminModel implements Serializable {

    public String adminId;
    public String name;
    public String email;
    public String password;
    public String gender;
    public String dob;

    public String profilePicture;

    public AdminModel(String adminId, String name, String email, String password, String gender, String dob, String profilePicture) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dob = dob;
        this.profilePicture = profilePicture;
    }

    public AdminModel() {
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
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

    public static class Builder implements Serializable {
        public String adminId;
        public String name;
        public String email;
        public String password;

        public String gender;
        public String dob;
        public String profilePicture;

        public AdminModel.Builder patientId(String adminId) {
            this.adminId = adminId;
            return this;
        }

        public AdminModel.Builder name(String name) {
            this.name = name;
            return this;
        }

        public AdminModel.Builder email(String email) {
            this.email = email;
            return this;
        }

        public AdminModel.Builder password(String password) {
            this.password = password;
            return this;
        }

        public AdminModel.Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public AdminModel.Builder dob(String dob) {
            this.dob = dob;
            return this;
        }

        public AdminModel.Builder profilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
            return this;
        }

        public AdminModel build() {
            return new AdminModel(this.adminId, this.name, this.email, this.password, this.gender, this.dob, this.profilePicture);
        }
    }
}
