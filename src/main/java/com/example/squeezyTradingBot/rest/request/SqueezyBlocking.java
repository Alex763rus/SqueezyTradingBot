package com.example.squeezyTradingBot.rest.request;

import com.example.squeezyTradingBot.enums.Emoji;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.val;

import static com.example.squeezyTradingBot.enums.Emoji.*;

@Getter
@ToString
@Setter
public class SqueezyBlocking extends BaseRequest {

    protected String stand;
    protected String currency;

    protected String number;
    protected String state;
    protected String comment;

    @Override
    public String toMessage() {
        val emoji = state.equals("lock") ? LOCK : UNLOCK;
        return new StringBuilder()
                .append(getEmojiCode(emoji)).append(stand).append(space).append(currency).append(space)
                .append(star).append(number).append(star).append(endLine)
                .append(getLineParam("Причина:", comment))
                .toString();
    }
}
