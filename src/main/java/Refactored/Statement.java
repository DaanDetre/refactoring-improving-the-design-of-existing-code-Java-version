package Refactored;

import Models.Invoice;
import Models.Performance;
import Models.Play;
import Models.PlayData;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Statement {
    List<Play> plays;
    public String GenerateStatement(Invoice invoice, PlayData playData){
        int totalAmount = 0;
        int volumeCredits = 0;
        StringBuilder result = new StringBuilder();
        result.append("OldNotRefactored.Statement for " + invoice.getCustomer() +"\n");

        Locale locale = new Locale("en", "GB");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        plays = new ArrayList<>(playData.getPlays().values());

        for(Performance perf : invoice.getPerformances()){
            List<Play> filteredPlays = playFor(perf);

            if (!filteredPlays.isEmpty()) {
                final Play play = filteredPlays.get(0);
                int thisAmount = amountFor(perf, play);

                // add volume credits
                volumeCredits += Math.max(perf.getAudience() - 30, 0);
                // add extra credit for every ten comedy attendees
                if ("comedy".equals(play.getType())) volumeCredits += Math.floor(perf.getAudience() / 5f);
                // print line for this order
                result.append(play.getName() + ":" + fmt.format(thisAmount / 100) + " "  + perf.getAudience() + " seats\n");
                totalAmount += thisAmount;
            }


        }
        result.append("Amount owed is " +  fmt.format(totalAmount/100)+"\n");
        result.append("You earned " + volumeCredits + " credits\n");

        return result.toString();
    }

    private List<Play> playFor(Performance perf) {
        List<Play> filteredPlays = plays.stream()
                .filter(p -> p.getName().equalsIgnoreCase(perf.getPlayID())).toList();
        return filteredPlays;
    }

    private int amountFor(Performance performance, Play play) {
        int result = 0;
        switch (play.getType()) {
            case "tragedy" -> {
                result = 40000;
                if (performance.getAudience() > 30) {
                    result += 1000 * (performance.getAudience() - 30);
                }
            }
            case "comedy" -> {
                result = 30000;
                if (performance.getAudience() > 20) {
                    result += 10000 + 500 * (performance.getAudience() - 20);
                }
                result += 300 * performance.getAudience();
            }
            default -> throw new Error("unknown type: " + play.getType());
        }
        return result;
    }
}
