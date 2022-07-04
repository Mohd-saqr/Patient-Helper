package com.patient.patienthelper.helperClass;

import com.amplifyframework.core.Amplify;

public class UserLogIn {
    private String fullName;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;

    private String id;
    private String email;
    private Boolean email_verified;
    private Boolean firstLogIn = true;
    private String status;
    private String diseaseName;

    private String imageId;

    public UserLogIn(String fullName, String userName, String id, String email, Boolean email_verified, String imageId, String firstName, String lastName, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.id = id;
        this.email = email;
        this.email_verified = email_verified;
        this.imageId = imageId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;


    }

    public UserLogIn(String fullName, String userName, String firstName, String lastName, String id, String email, Boolean email_verified, Boolean firstLogIn, String status, String imageId, String diseaseName) {
        this.fullName = fullName;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.email = email;
        this.email_verified = email_verified;
        this.firstLogIn = firstLogIn;
        this.status = status;
        this.imageId = imageId;
        this.diseaseName = diseaseName;
    }

//    public UserLogIn() {
//
//        Amplify.Auth.fetchUserAttributes(sec->{
//            this.id=sec.get(0).toString();
//            this.email_verified=sec.get(1).toString().equals("true");
//            this.status=sec.get(2).toString();
//            this.firstName=sec.get(3).toString();
//            this.lastName =sec.get(4).toString();
//            this.email=sec.get(5).toString();
//            this.imageId=email.replace("@","")
//                    .replace("_","").replace("-","")
//                    .replace(".","");
//            this.fullName=firstName+" " +lastName;
//        },err->{
//        //////
//        });
//    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(Boolean email_verified) {
        this.email_verified = email_verified;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getEmail_verified() {
        return email_verified;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getFirstLogIn() {
        return firstLogIn;
    }

    public void setFirstLogIn(Boolean firstLogIn) {
        this.firstLogIn = firstLogIn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    @Override
    public String toString() {
        return "UserLogIn{" +
                "fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", email_verified=" + email_verified +
                ", firstLogIn=" + firstLogIn +
                ", status='" + status + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }
}
