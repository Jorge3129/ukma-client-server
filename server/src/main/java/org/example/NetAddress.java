package org.example;

import org.example.interfaces.INetAddress;

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
