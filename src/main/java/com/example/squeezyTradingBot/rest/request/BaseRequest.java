package com.example.squeezyTradingBot.rest.request;

import com.example.squeezyTradingBot.enums.Emoji;
import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.val;

@Getter
@ToString
public abstract class BaseRequest {

    public static final char star = '*';
    public static final char space = ' ';
    public static final String endLine = "\n";

    public abstract String toMessage();

    public String getEmojiSide(String side) {
        Emoji emojiSide;
        switch (side.toUpperCase()) {
            case "BUY":
                emojiSide = Emoji.ARROW_UP;
                break;
            case "SELL":
                emojiSide = Emoji.ARROW_DOWN;
                break;
            default:
                emojiSide = Emoji.QUESTION;
        }
        return getEmojiCode(emojiSide);
    }

    public String getEmojiCode(Emoji emoji){
        return EmojiParser.parseToUnicode(emoji.getCode());
    }

    protected String getLineParam(String name, Object value) {
        return new StringBuilder().append(star).append(name).append(star).append(value).append(endLine).toString();
    }
}
