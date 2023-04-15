package com.example.squeezyTradingBot.rest.request.squeezy;

import com.example.squeezyTradingBot.enums.Emoji;
import com.example.squeezyTradingBot.rest.request.BaseRequest;
import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@Setter
public class SqueezyStart extends BaseRequest {
    private String version;

    @Override
    public String toMessage() {
        return new StringBuilder()
                .append(EmojiParser.parseToUnicode(Emoji.ROBOT_FACE.getCode()))
                .append(" Squeezy trading bot was started!").append(endLine)
                .append("Version: ").append(version)
                .toString();
    }
}
