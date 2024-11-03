package Entity;

import java.sql.Timestamp;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Timestamp createdAt;
    private String createdBy;
    private Integer roleId;
    private Boolean isPwdResetReq = Boolean.FALSE;

    // Constructors
    public User() {}

    public User(String id, String firstName, String lastName, String email, String password,
                Timestamp createdAt, String createdBy, Integer roleId, Boolean isPwdResetReq) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.roleId = roleId;
        this.isPwdResetReq = isPwdResetReq;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Boolean getIsPwdResetReq() {
        return isPwdResetReq;
    }

    public void setIsPwdResetReq(Boolean isPwdResetReq) {
        this.isPwdResetReq = isPwdResetReq;
    }

    // Method to display user details (for debugging purposes)
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", roleId=" + roleId +
                ", isPwdResetReq=" + isPwdResetReq +
                '}';
    }
}
