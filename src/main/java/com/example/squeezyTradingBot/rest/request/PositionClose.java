package com.example.squeezyTradingBot.rest.request;


import com.example.squeezyTradingBot.enums.Emoji;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

import static com.example.squeezyTradingBot.enums.Emoji.MONEY_BAG;
import static com.example.squeezyTradingBot.enums.Emoji.WARNING;

@Getter
@ToString
@Setter
public class PositionClose extends PositionOpen {

    protected String dateClose;
    protected double profit;
    protected String comment;
    protected String signalTypeClose;
    protected double deposit;

    @Override
    public String toMessage() {
        Emoji emoji = profit > 0 ? MONEY_BAG : WARNING;
        return new StringBuilder()
                .append(getEmojiCode(emoji)).append(stand).append(space).append(currency).append(space)
                .append(star).append(number).append(star).append(endLine)
                .append(getLineParam("Группа: ", groupType))
                .append(getLineParam("Направление: ", side + " " + getEmojiSide(getSide())))
                .append(getLineParam("Объем: ", String.format("%.0f", volume)))
                .append(getLineParam("Дата открытия: ", dateStart))
                .append(getLineParam("Дата закрытия: ", dateClose))
                .append(getLineParam("Причина закрытия:", signalTypeClose))
                .append(getLineParam("Комментарий:", comment))
                .append(getLineParam("Результат: ", String.format("%.5f", profit)))
                .append(getLineParam("Депозит: ", String.format("%.5f", deposit)))
                .toString();
    }

}
