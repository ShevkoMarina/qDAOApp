package remote.user_models;

public class AuthorizeUserRequestDto {
    private String login;
    private String password;

    public AuthorizeUserRequestDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
