package Refactored;

import Models.Invoice;
import Models.Performance;
import Models.PlayData;

public class Statement {
    public Statement(){

    }

    public String GenerateStatement(PlayData playData, Invoice invoice){
        return renderPlainText(new CalculateStatementData(playData, invoice));
    }

    private String renderPlainText(CalculateStatementData CSD) {
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
