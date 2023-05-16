package remote;

import remote.user_models.AddUserDto;
import remote.user_models.AuthorizeUserRequestDto;
import remote.user_models.AuthorizeUserResponseDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserClient {
    @Headers("Content-type: application/json")
    @POST("user/auth")
    Call<AuthorizeUserResponseDto> authorize(@Body AuthorizeUserRequestDto request);

    @Headers("Content-type: application/json")
    @POST("user/add")
    Call<Void> register(@Body AddUserDto request);
}
