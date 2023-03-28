package com.example.squeezyTradingBot.rest.request;

import lombok.Data;

@Data
public class Unsorted extends BaseRequest{

    protected String stand;
    protected String currency;
    protected String message;

    @Override
    public String toMessage() {
        StringBuilder sb = new StringBuilder(stand).append(space).append(currency).append(space).append(" (формат TODO...)");
        sb.append(endLine);
        sb.append(message);
        return sb.toString();
    }
}
