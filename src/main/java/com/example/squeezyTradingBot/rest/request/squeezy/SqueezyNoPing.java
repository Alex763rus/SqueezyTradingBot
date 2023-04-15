package com.example.squeezyTradingBot.rest.request.squeezy;

import com.example.squeezyTradingBot.rest.request.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static com.example.squeezyTradingBot.enums.Emoji.NO_MOBILE_PHONES;

@Getter
@ToString
@Setter
public class SqueezyNoPing extends BaseRequest {

    @Override
    public String toMessage() {
        return new StringBuilder()
                .append(getEmojiCode(NO_MOBILE_PHONES)).append(endLine)
                .append(getLineParam("Внимание:", "Сквизи не на связи!"))
                .toString();
    }

}
