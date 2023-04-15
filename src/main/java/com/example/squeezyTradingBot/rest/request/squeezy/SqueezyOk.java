package com.example.squeezyTradingBot.rest.request.squeezy;

import com.example.squeezyTradingBot.rest.request.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

import static com.example.squeezyTradingBot.enums.Emoji.*;

@Getter
@ToString
@Setter
public class SqueezyOk extends BaseRequest {

    protected String stand;
    protected String currency;

    @Override
    public String toMessage() {
        return new StringBuilder()
                .append(getEmojiCode(SIGNAL_STRENGTH)).append(stand).append(space).append(currency).append(space).append(endLine)
                .append("Сквизи на связи, работаем!")
                .toString();
    }
}
