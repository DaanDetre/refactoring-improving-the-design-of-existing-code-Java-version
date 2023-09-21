package Refactored;

import Models.Invoice;
import Models.Performance;
import Models.PlayData;

public class Statement {
    public Statement(){

    }

    public String generatePlainTextStatement(PlayData playData, Invoice invoice){
        return renderPlainText(new CalculateStatementData(playData, invoice));
    }

    public String generateHTMLStatement(PlayData playData, Invoice invoice){
        return renderHTML(new CalculateStatementData(playData, invoice));
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

    private String renderHTML(CalculateStatementData CSD) {
        StringBuilder result = new StringBuilder();
        result.append("<h1>Refactored.Statement for " + CSD.invoice.getCustomer() +"</h1>\n");
        result.append("<table>\n");
        result.append("<tr><th>play</th><th>seats</th><th>cost</th></tr>\n");
        for(Performance perf : CSD.invoice.getPerformances()){
            result.append("<tr><td>"+CSD.playFor(perf).getName() + "</td><td>" + perf.getAudience() + "</td><td>"  + CSD.toPond(CSD.amountFor(perf) / 100)+ "</td></tr>\n");
        }
        result.append("</table>\n");
        result.append("<p>Amount owed is <em>" +  CSD.toPond(CSD.totalAmount()/100)+"</em></p>\n");
        result.append("<p>You earned <em>" + CSD.totalVolumeCredits() + "</em> credits</p>\n");
        return result.toString();
    }
}
