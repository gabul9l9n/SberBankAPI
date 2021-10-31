package org.example.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardController cardController;

    private String cardUri = "/account/{idA}/card";

    @Test
    public void getCardById() throws Exception {
        this.mockMvc.perform(get(cardUri + "/{idC}", 1, 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"Card\" : {\n" +
                        "    \"id\" : 1,\n" +
                        "    \"number\" : 3475475743563566,\n" +
                        "    \"cvc\" : 727,\n" +
                        "    \"type\" : \"Visa\",\n" +
                        "    \"currency\" : \"Dollar\",\n" +
                        "    \"balance\" : 0\n" +
                        "  }\n" +
                        "}"));
    }

    @Test
    public void getNotExistingCardById() throws Exception {
        this.mockMvc.perform(get(cardUri + "/{idC}", 1, 2))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getAllCards() throws Exception {
        this.mockMvc.perform(get(cardUri + "/all", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"List\" : [ {\n" +
                        "    \"id\" : 1,\n" +
                        "    \"number\" : 3475475743563566,\n" +
                        "    \"cvc\" : 727,\n" +
                        "    \"type\" : \"Visa\",\n" +
                        "    \"currency\" : \"Dollar\",\n" +
                        "    \"balance\" : 0\n" +
                        "  }, {\n" +
                        "    \"id\" : 7,\n" +
                        "    \"number\" : 4273623873627412,\n" +
                        "    \"cvc\" : 264,\n" +
                        "    \"type\" : \"Visa\",\n" +
                        "    \"currency\" : \"Euro\",\n" +
                        "    \"balance\" : 0\n" +
                        "  }, {\n" +
                        "    \"id\" : 9,\n" +
                        "    \"number\" : 2346512767824671,\n" +
                        "    \"cvc\" : 327,\n" +
                        "    \"type\" : \"Visa\",\n" +
                        "    \"currency\" : \"Dollar\",\n" +
                        "    \"balance\" : 0\n" +
                        "  } ]\n" +
                        "}"));
    }

    @Test
    public void getAllNotExistingCardsById() throws Exception {
        this.mockMvc.perform(get(cardUri, 10))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getBalance() throws Exception {
        this.mockMvc.perform(get(cardUri + "/{idC}/balance", 1,1))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"Integer\" : 0\n" +
                        "}"));
    }

    @Test
    public void getBalanceOfNotExistingCard() throws Exception {
        this.mockMvc.perform(get(cardUri + "/{idC}/balance", 1,2))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addDepositToBalance() throws Exception {
        this.mockMvc.perform(post(cardUri + "/{idC}/balance", 2, 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"balance\" : 200}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"Card\": {\n" +
                        "        \"id\": 2,\n" +
                        "        \"number\": 2341567387889129,\n" +
                        "        \"cvc\": 324,\n" +
                        "        \"type\": \"MasterCard\",\n" +
                        "        \"currency\": \"Euro\",\n" +
                        "        \"balance\": 200\n" +
                        "    }\n" +
                        "}"));
    }

    @Test
    public void addDepositToBalanceForNotExistingCard() throws Exception {
        this.mockMvc.perform(post(cardUri + "/{idC}/balance", 2, 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"balance\" : 200}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteCard() throws Exception {
        this.mockMvc.perform(delete(cardUri + "/{idC}", 1, 9))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNotExistingCard() throws Exception {
        this.mockMvc.perform(delete(cardUri + "/{idC}", 1, 8))
                .andExpect(status().is4xxClientError());
    }
}