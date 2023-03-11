package Entities;

public class UserEntity {
    protected final Long userId;

    protected String userName;

    protected String userEmail;

    protected String userPassword;

    protected String userPhone;

    public static final String dbTable = "users";

    public UserEntity(Long userId, String userName, String userEmail, String userPassword, String userPhone) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }
}
