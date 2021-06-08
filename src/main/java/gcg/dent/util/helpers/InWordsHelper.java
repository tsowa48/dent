package gcg.dent.util.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

public class InWordsHelper implements Helper<Object> {
    @Override
    public Object apply(Object context, Options options) throws IOException {
        try {
            Double money = Double.parseDouble(context.toString());
            return money.intValue() > 0 ? money2str(money.intValue(), "") : "ноль";
        } catch (Exception ex) {
            return "не указано";
        }
    }

    private static final String[] Low = new String[]{"", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять", "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семьнадцать", "восемьнадцать", "девятнадцать"};
    private static final String[] Ts = new String[]{"", "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};
    private static final String[] Hs = new String[]{"", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"};

    private String money2str(int money, String word) {
        if (money < 20) {
            word += Low[money];
        } else if (money < 100) {
            word += Ts[(int) (Math.floor(money / 10.0))] + " " + money2str(money - (int) (Math.floor(money / 10.0)) * 10, word);
        } else if (money < 1000) {
            word += Hs[(int) (Math.floor(money / 100.0))] + " " + money2str(money - (int) (Math.floor(money / 100.0)) * 100, word);
        } else if (money < 1000000) {
            int ths = (int) (Math.floor(money / 1000.0));
            String e = ((ths > 4 && ths < 20) || (ths - Math.floor(ths / 10.0) * 10 < 1)) ? "" : (ths - Math.floor(ths / 10.0) * 10 < 2 ? "а" : (ths - Math.floor(ths / 10.0) * 10 < 5 ? "и" : ""));
            word += money2str(ths, "").replace("один", "одна").replace("два", "две").replace("дведцать", "двадцать")
                    + " тысяч" + e + " " + money2str(money - (int) (Math.floor(money / 1000.0)) * 1000, word);
        } else if (money < 1000000000) {
            int ths = (int) (Math.floor(money / 1000000.0));
            String e = ((ths - Math.floor(ths / 10.0) * 10 == 1) ? "" : (ths - Math.floor(ths / 10.0) * 10 < 5 ? "а" : "ов"));
            word += money2str(ths, "") + " миллион" + e + " " + money2str(money - (int) (Math.floor(money / 1000000.0)) * 1000000, word);
        } else {
            word = String.valueOf(money);
        }
        return word;
    }
}