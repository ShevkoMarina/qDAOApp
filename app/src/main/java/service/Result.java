package service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qdao.R;

public class Result<T> {
    @Nullable private final T data;
    @Nullable private final String errorMessage;

    private Result(@Nullable T data, @Nullable String error){
        this.data = data;
        this.errorMessage = error;
    }

    public static <T> Result<T> success() {
        return new Result<>(null, null);
    }

    public static <T> Result<T> success(@NonNull T data) {
        return new Result<>(data, null);
    }

    public static <T> Result<T> error(@NonNull String error) {
        return new Result<>(null, error);
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSuccess(){
        return errorMessage == null;
    }
}
