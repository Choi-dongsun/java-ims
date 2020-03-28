package codesquad.web.api.response;

public class ApiResponse<T> {
    private final boolean success;
    private final T response;

    public ApiResponse(boolean success, T response) {
        this.success = success;
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResponse() {
        return response;
    }
}
