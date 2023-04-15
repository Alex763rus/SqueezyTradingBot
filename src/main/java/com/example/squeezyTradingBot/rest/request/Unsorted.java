package com.example.squeezyTradingBot.rest.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@Setter
public class Unsorted extends BaseRequest {

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
