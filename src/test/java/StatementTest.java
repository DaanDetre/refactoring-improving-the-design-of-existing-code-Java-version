import Models.Invoice;
import Models.PlayData;
import Refactored.Statement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import java.io.*;
import static junit.framework.Assert.assertEquals;


public class StatementTest {

    @Test
    public void testGeneratePlainTextStatement(){

        Statement statement = new Statement();
        String output = statement.generatePlainTextStatement(generatePlayData(), generateInvoiceData());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Refactored.Statement for BigCo\n");
        stringBuilder.append("Hamlet:£650.00 55 seats\n");
        stringBuilder.append("As You Like It:£580.00 35 seats\n");
        stringBuilder.append("Othello:£500.00 40 seats\n");
        stringBuilder.append("Amount owed is £1,730.00\n");
        stringBuilder.append("You earned 47 credits\n");

        assertEquals(stringBuilder.toString(), output);
    }

    @Test
    public void testGenerateHTMLStatement(){

        Statement statement = new Statement();
        String output = statement.generateHTMLStatement(generatePlayData(), generateInvoiceData());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h1>Refactored.Statement for BigCo</h1>\n");
        stringBuilder.append("<table>\n");
        stringBuilder.append("<tr><th>play</th><th>seats</th><th>cost</th></tr>\n");
        stringBuilder.append("<tr><td>Hamlet</td><td>55</td><td>£650.00</td></tr>\n");
        stringBuilder.append("<tr><td>As You Like It</td><td>35</td><td>£580.00</td></tr>\n");
        stringBuilder.append("<tr><td>Othello</td><td>40</td><td>£500.00</td></tr>\n");
        stringBuilder.append("</table>\n");
        stringBuilder.append("<p>Amount owed is <em>£1,730.00</em></p>\n");
        stringBuilder.append("<p>You earned <em>47</em> credits</p>\n");

        assertEquals(stringBuilder.toString(), output);
    }

    private Invoice generateInvoiceData(){
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("invoices.json")) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readValue(in, JsonNode.class);
            return mapper.treeToValue(jsonNode, Invoice.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PlayData generatePlayData(){
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("plays.json")) {
            if (in != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(in, PlayData.class);
            } else {
                System.err.println("plays.json not found in the classpath");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
