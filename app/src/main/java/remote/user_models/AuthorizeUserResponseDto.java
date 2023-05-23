package remote.user_models;

public class AuthorizeUserResponseDto {
    private int id;
    private short role;
    private String account;
    private String token;

    public AuthorizeUserResponseDto(int id, short role, String account, String token) {
        this.id = id;
        this.role = role;
        this.account = account;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public short getRole() {
        return role;
    }

    public String getAccount() {
        return account;
    }

    public String getToken() {
        return token;
    }
}
