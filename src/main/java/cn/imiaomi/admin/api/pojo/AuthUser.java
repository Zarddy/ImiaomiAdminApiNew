package cn.imiaomi.admin.api.pojo;

import jdk.nashorn.internal.ir.annotations.Ignore;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "auth_user")
public class AuthUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Ignore
    private String password;

    private String salt;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "is_superuser")
    private Boolean isSuperuser;

    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    @Column(name = "is_staff")
    private Boolean isStaff;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "date_joined")
    private Date dateJoined;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return last_login
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * @param lastLogin
     */
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * @return is_superuser
     */
    public Boolean getIsSuperuser() {
        return isSuperuser;
    }

    /**
     * @param isSuperuser
     */
    public void setIsSuperuser(Boolean isSuperuser) {
        this.isSuperuser = isSuperuser;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return first_name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return last_name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return is_staff
     */
    public Boolean getIsStaff() {
        return isStaff;
    }

    /**
     * @param isStaff
     */
    public void setIsStaff(Boolean isStaff) {
        this.isStaff = isStaff;
    }

    /**
     * @return is_active
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return date_joined
     */
    public Date getDateJoined() {
        return dateJoined;
    }

    /**
     * @param dateJoined
     */
    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }
}