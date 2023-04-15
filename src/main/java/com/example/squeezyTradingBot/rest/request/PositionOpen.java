package com.example.squeezyTradingBot.rest.request;


import com.example.squeezyTradingBot.enums.Emoji;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@Setter
public class PositionOpen extends BaseRequest {

    protected String stand;
    protected String currency;

    protected String number;
    protected String dateStart;
    protected String side;
    protected double volume;
    protected String groupType;
    protected String signalTypeOpen;

    @Override
    public String toMessage() {
        StringBuilder message = new StringBuilder(getEmojiCode(Emoji.SHOPPING_TROLLEY)).append(stand).append(space).append(currency).append(space);
        message.append(star).append(number).append(star).append(endLine);
        message.append(getLineParam("Группа: ", groupType));
        message.append(getLineParam("Направление: ", side + " " + getEmojiSide(getSide())));
        message.append(getLineParam("Объем: ", String.format("%.0f", volume)));
        message.append(getLineParam("Причина открытия: ", signalTypeOpen));
        message.append(getLineParam("Дата открытия: ", dateStart));
        return message.toString();
    }

}
