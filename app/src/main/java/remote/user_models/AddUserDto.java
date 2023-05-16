package remote.user_models;

public class AddUserDto {
    private String login;
    private String password;
    private String account;

    public AddUserDto(String login, String password, String account) {
        this.login = login;
        this.password = password;
        this.account = account;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getAccount() {
        return account;
    }
}
