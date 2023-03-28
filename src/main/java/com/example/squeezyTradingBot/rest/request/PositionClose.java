package com.example.squeezyTradingBot.rest.request;


import com.example.squeezyTradingBot.enums.Emoji;
import lombok.Data;

import static com.example.squeezyTradingBot.enums.Emoji.MONEY_BAG;
import static com.example.squeezyTradingBot.enums.Emoji.WARNING;

@Data
public class PositionClose  extends PositionOpen {

    protected String dateClose;
    protected double profit;
    protected String comment;
    protected String signalTypeClose;
    protected double deposit;

    @Override
    public String toMessage() {
        Emoji emoji = profit>0?MONEY_BAG:WARNING;
        StringBuilder message = new StringBuilder(getEmojiCode(emoji)).append(stand).append(space).append(currency).append(space);
        message.append(star).append(number).append(star).append(endLine);
        message.append(getLineParam("Группа: ", groupType));
        message.append(getLineParam("Направление: ", side + " " + getEmojiSide(getSide())));
        message.append(getLineParam("Объем: ", String.format("%.0f", volume)));
        message.append(getLineParam("Дата открытия: ", dateStart));
        message.append(getLineParam("Дата закрытия: ", dateClose));
        message.append(getLineParam("Причина закрытия:", signalTypeClose));
        message.append(getLineParam("Комментарий:", comment));
        message.append(getLineParam("Результат: ", String.format("%.5f", profit)));
        message.append(getLineParam("Депозит: ", String.format("%.5f", deposit)));
        return message.toString();
    }

}
