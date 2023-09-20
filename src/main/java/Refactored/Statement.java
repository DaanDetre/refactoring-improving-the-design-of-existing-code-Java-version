package Refactored;

import Models.Invoice;
import Models.Performance;
import Models.Play;
import Models.PlayData;

import java.text.NumberFormat;
import java.util.Locale;

public class Statement {
    PlayData playData;

    public Statement(PlayData playData){
        this.playData = playData;
    }

    public String GenerateStatement(Invoice invoice){
        int totalAmount = 0;
        int volumeCredits = 0;
        StringBuilder result = new StringBuilder();
        result.append("OldNotRefactored.Statement for " + invoice.getCustomer() +"\n");

        Locale locale = new Locale("en", "GB");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        for(Performance perf : invoice.getPerformances()){
            int thisAmount = amountFor(perf);

            // add volume credits
            volumeCredits += Math.max(perf.getAudience() - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy".equals(playFor(perf).getType())) volumeCredits += Math.floor(perf.getAudience() / 5f);
            // print line for this order
            result.append(playFor(perf).getName() + ":" + fmt.format(thisAmount / 100) + " "  + perf.getAudience() + " seats\n");
            totalAmount += thisAmount;


        }
        result.append("Amount owed is " +  fmt.format(totalAmount/100)+"\n");
        result.append("You earned " + volumeCredits + " credits\n");

        return result.toString();
    }

    private Play playFor(Performance perf) {
        return playData.getPlays().get(perf.getPlayID());
    }

    private int amountFor(Performance perf) {
        int result = 0;
        switch (playFor(perf).getType()) {
            case "tragedy" -> {
                result = 40000;
                if (perf.getAudience() > 30) {
                    result += 1000 * (perf.getAudience() - 30);
                }
            }
            case "comedy" -> {
                result = 30000;
                if (perf.getAudience() > 20) {
                    result += 10000 + 500 * (perf.getAudience() - 20);
                }
                result += 300 * perf.getAudience();
            }
            default -> throw new Error("unknown type: " + playFor(perf).getType());
        }
        return result;
    }
}
