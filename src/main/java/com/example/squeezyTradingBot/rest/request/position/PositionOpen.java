package com.example.squeezyTradingBot.rest.request.position;


import com.example.squeezyTradingBot.enums.Emoji;
import com.example.squeezyTradingBot.rest.request.BaseRequest;
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
        return new StringBuilder()
                .append(getEmojiCode(Emoji.SHOPPING_TROLLEY)).append(stand).append(space).append(currency).append(space)
                .append(star).append(number).append(star).append(endLine)
                .append(getLineParam("Группа: ", groupType))
                .append(getLineParam("Направление: ", side + " " + getEmojiSide(getSide())))
                .append(getLineParam("Объем: ", String.format("%.0f", volume)))
                .append(getLineParam("Причина открытия: ", signalTypeOpen))
                .append(getLineParam("Дата открытия: ", dateStart))
                .toString();
    }

}
