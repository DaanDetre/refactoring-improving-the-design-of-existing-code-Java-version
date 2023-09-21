package Refactored;

import Models.Invoice;
import Models.Performance;
import Models.Play;
import Models.PlayData;

import java.text.NumberFormat;
import java.util.Locale;

public class Statement {
    public Statement(){

    }

    public String GenerateStatement(PlayData playData, Invoice invoice){
        CreateStatementData createStatementData = new CreateStatementData(playData, invoice);
        return renderPlainText(createStatementData);
    }

    private String renderPlainText(CreateStatementData CSD) {
        StringBuilder result = new StringBuilder();
        result.append("Refactored.Statement for " + CSD.invoice.getCustomer() +"\n");

        for(Performance perf : CSD.invoice.getPerformances()){
            result.append(CSD.playFor(perf).getName() + ":" + CSD.toPond(CSD.amountFor(perf) / 100) + " "  + perf.getAudience() + " seats\n");
        }

        result.append("Amount owed is " +  CSD.toPond(CSD.totalAmount()/100)+"\n");
        result.append("You earned " + CSD.totalVolumeCredits() + " credits\n");
        return result.toString();
    }
}
