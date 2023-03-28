package com.example.squeezyTradingBot.rest.request;

import com.example.squeezyTradingBot.enums.Emoji;
import com.vdurmont.emoji.EmojiParser;

public abstract class BaseRequest {

    public static final char star = '*';
    public static final char space = ' ';
    public static final char apostrL = '_';
    public static final char apostrR = '_';
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
}