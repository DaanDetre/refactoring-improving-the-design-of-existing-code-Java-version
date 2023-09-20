package OldNotRefactored;

import Models.Invoice;
import Models.Performance;
import Models.Play;
import Models.PlayData;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Statement {
    public String GenerateStatement(Invoice invoice, PlayData playData){
        int totalAmount = 0;
        int volumeCredits = 0;
        StringBuilder result = new StringBuilder();
        result.append("OldNotRefactored.Statement for " + invoice.getCustomer() +"\n");

        Locale locale = new Locale("en", "GB");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        List<Play> plays = new ArrayList<>(playData.getPlays().values()) ;

        for(Performance perf : invoice.getPerformances()){
            List<Play> filteredPlays = plays.stream()
                    .filter(p -> p.getName().equalsIgnoreCase(perf.getPlayID()))
                    .collect(Collectors.toList());

            if (!filteredPlays.isEmpty()) {
                Play play = filteredPlays.get(0); // Assuming playID is unique
                // Now you can work with the 'play' object
                System.out.println("Models.Play ID: " + play.getName());
                System.out.println("Models.Play Type: " + play.getType());

                int thisAmount = 0;
                switch (play.getType()) {
                    case "tragedy" -> {
                        thisAmount = 40000;
                        if (perf.getAudience() > 30) {
                            thisAmount += 1000 * (perf.getAudience() - 30);
                        }
                    }
                    case "comedy" -> {
                        thisAmount = 30000;
                        if (perf.getAudience() > 20) {
                            thisAmount += 10000 + 500 * (perf.getAudience() - 20);
                        }
                        thisAmount += 300 * perf.getAudience();
                    }
                    default -> throw new Error("unknown type: " + play.getType());
                }

                // add volume credits
                volumeCredits += Math.max(perf.getAudience() - 30, 0);
                // add extra credit for every ten comedy attendees
                if ("comedy".equals(play.getType())) volumeCredits += Math.floor(perf.getAudience() / 5);
                // print line for this order
                result.append(play.getName() + ":" + fmt.format(thisAmount / 100) + " "  + perf.getAudience() + " seats\n");
                totalAmount += thisAmount;
            }


        }
        result.append("Amount owed is " +  fmt.format(totalAmount/100)+"\n");
        result.append("You earned " + volumeCredits + " credits\n");

        return result.toString();
    }
}
