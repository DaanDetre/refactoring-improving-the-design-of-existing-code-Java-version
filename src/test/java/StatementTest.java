import Models.Invoice;
import Models.PlayData;
import OldNotRefactored.Statement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import java.io.*;
import static junit.framework.Assert.assertEquals;


public class StatementTest {

    PlayData playData;
    Invoice invoice;

    @Test
    public void testGenerateStatement(){
        generateInvoiceData();
        generatePlayData();

        Statement statement = new Statement();
        String output = statement.GenerateStatement(invoice, playData);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("OldNotRefactored.Statement for BigCo\n");
        stringBuilder.append("Hamlet:£650.00 55 seats\n");
        stringBuilder.append("Othello:£500.00 40 seats\n");
        stringBuilder.append("Amount owed is £1,150.00\n");
        stringBuilder.append("You earned 35 credits\n");

        assertEquals(stringBuilder.toString(), output);
    }

    private void generateInvoiceData(){
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("invoices.json")) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readValue(in, JsonNode.class);
            this.invoice = mapper.treeToValue(jsonNode, Invoice.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generatePlayData(){
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("plays.json")) {
            if (in != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                this.playData = objectMapper.readValue(in, PlayData.class);
            } else {
                System.err.println("plays.json not found in the classpath");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
