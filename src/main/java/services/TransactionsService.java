package services;
import io.restassured.response.Response;
import utils.Signatures;

import java.util.UUID;

import static io.restassured.RestAssured.*;

public class TransactionsService {
    private String baseUrl;
    private String privatetKey;

    public TransactionsService(String baseUrl, String privatetKey) {
        this.baseUrl = baseUrl;
        this.privatetKey = privatetKey;
    }

    public Response generatePseTransaction(String acceptanceToken){
        String reference = "test_" + UUID.randomUUID().toString();
        int amount = 150000;
        String currency = "COP";
        String integritySecret = "stagtest_integrity_nAIBuqayW70XpUqJS4qf4STYiISd89Fp";
        String signature = Signatures.generate_signature(reference, amount, currency, integritySecret);
        String body = "{"
                + "\"acceptance_token\":\"" + acceptanceToken + "\","
                + "\"amount_in_cents\":" + amount + ","
                + "\"currency\":\"" + currency + "\","
                + "\"reference\":\"" + reference + "\","
                + "\"customer_email\":\"pruebasensandbox@yopmail.com\","
                + "\"payment_method\":{"
                + "     \"type\":\"PSE\","
                + "     \"user_type\":\"0\","
                + "     \"user_legal_id_type\":\"CC\","
                + "     \"user_legal_id\":\"1069879849\","
                + "     \"financial_institution_code\":\"1\","
                + "     \"payment_description\":\"Transaction test\""
                + "     },"
                + "\"customer_data\":{"
                + "     \"phone_number\":\"+573000000000\","
                + "     \"full_name\":\"Alejandra Pruebas Sandbox UAT\""
                + "     },"
                + "\"signature\":\"" + signature + "\""
                + "}";

        return given().header("Authorization", "Bearer" + privatetKey).header("Content-Type", "application/json").body(body).when().post(baseUrl + "/transactions");
    }
}
