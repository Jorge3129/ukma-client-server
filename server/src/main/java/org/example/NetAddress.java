package org.example;

public class NetAddress implements INetAddress {
    String url;

    NetAddress(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
