package service.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import model.APIError;
import okhttp3.ResponseBody;
import remote.NetworkService;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                NetworkService.getRetrofitClient()
                        .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}