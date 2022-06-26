package com.patient.patienthelper.helperClass;

public class UserLogIn {
    private String fullName;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;

    private String id;
    private String email;
    private Boolean email_verified ;

    private  String imageId;

    public UserLogIn(String fullName, String userName, String id, String email, Boolean email_verified, String imageId, String firstName , String lastName , String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.id = id;
        this.email = email;
        this.email_verified=email_verified;
        this.imageId=imageId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;




    }


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

    @Override
    public String toString() {
        return "UserLogIn{" +
                "fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", email_verified=" + email_verified +
                '}';
    }
}
