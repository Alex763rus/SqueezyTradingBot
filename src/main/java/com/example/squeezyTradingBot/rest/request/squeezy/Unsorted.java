package com.example.squeezyTradingBot.rest.request.squeezy;

import com.example.squeezyTradingBot.rest.request.BaseRequest;
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
        return new StringBuilder()
                .append(stand).append(space).append(currency).append(space).append(" (формат TODO...)").append(endLine)
                .append(message)
                .toString();
    }
}
