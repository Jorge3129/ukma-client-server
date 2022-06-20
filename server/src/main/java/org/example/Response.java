package org.example;

public class Response {
    private final ResponseType type;

    public Response(ResponseType type) {
        this.type = type;
    }

    public ResponseType getType() {
        return type;
    }
}


enum ResponseType {
    OK,
    BAD_REQUEST
}