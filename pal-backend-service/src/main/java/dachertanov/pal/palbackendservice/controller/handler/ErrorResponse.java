package dachertanov.pal.palbackendservice.controller.handler;

import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse(String code) {
        this.code = code;
        this.message = "UNKNOWN";
    }

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
