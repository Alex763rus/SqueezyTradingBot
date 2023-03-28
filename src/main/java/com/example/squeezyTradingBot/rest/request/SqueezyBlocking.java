package com.example.squeezyTradingBot.rest.request;

import com.example.squeezyTradingBot.enums.Emoji;
import lombok.Data;

import static com.example.squeezyTradingBot.enums.Emoji.*;

@Data
public class SqueezyBlocking extends BaseRequest {

    protected String stand;
    protected String currency;
    protected String number;
    protected String state;
    protected String comment;

    @Override
    public String toMessage() {
        Emoji emoji = state.equals("lock")?LOCK:UNLOCK;
        StringBuilder message = new StringBuilder(getEmojiCode(emoji)).append(stand).append(space).append(currency).append(space);
        message.append(star).append(number).append(star).append(endLine);
        message.append(getLineParam("Причина:", comment));
        return message.toString();
    }
}
