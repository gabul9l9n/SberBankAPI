package org.example.controller;

import org.example.model.dto.AccountDto;
import org.example.model.enums.Bank;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(value = MethodSorters.JVM)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountController accountController;

    private String accUri = "/account";

    @Test
    public void getAllAccounts() throws Exception {
        this.mockMvc.perform(get(accUri + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"List\" : [ {\n" +
                        "    \"id\" : 1,\n" +
                        "    \"number\" : 2314,\n" +
                        "    \"bank\" : \"Sberbank\",\n" +
                        "    \"card\" : [ {\n" +
                        "      \"id\" : 1,\n" +
                        "      \"number\" : 3475475743563566,\n" +
                        "      \"cvc\" : 727,\n" +
                        "      \"type\" : \"Visa\",\n" +
                        "      \"currency\" : \"Dollar\",\n" +
                        "      \"balance\" : 0\n" +
                        "    }, {\n" +
                        "      \"id\" : 7,\n" +
                        "      \"number\" : 4273623873627412,\n" +
                        "      \"cvc\" : 264,\n" +
                        "      \"type\" : \"Visa\",\n" +
                        "      \"currency\" : \"Euro\",\n" +
                        "      \"balance\" : 0\n" +
                        "    }, {\n" +
                        "      \"id\" : 9,\n" +
                        "      \"number\" : 2346512767824671,\n" +
                        "      \"cvc\" : 327,\n" +
                        "      \"type\" : \"Visa\",\n" +
                        "      \"currency\" : \"Dollar\",\n" +
                        "      \"balance\" : 0\n" +
                        "    } ]\n" +
                        "  }, {\n" +
                        "    \"id\" : 2,\n" +
                        "    \"number\" : 4872,\n" +
                        "    \"bank\" : \"Tinkoff\",\n" +
                        "    \"card\" : [ {\n" +
                        "      \"id\" : 2,\n" +
                        "      \"number\" : 2341567387889129,\n" +
                        "      \"cvc\" : 324,\n" +
                        "      \"type\" : \"MasterCard\",\n" +
                        "      \"currency\" : \"Euro\",\n" +
                        "      \"balance\" : 0\n" +
                        "    }, {\n" +
                        "      \"id\" : 5,\n" +
                        "      \"number\" : 1237372643891782,\n" +
                        "      \"cvc\" : 574,\n" +
                        "      \"type\" : \"MasterCard\",\n" +
                        "      \"currency\" : \"Dollar\",\n" +
                        "      \"balance\" : 0\n" +
                        "    }, {\n" +
                        "      \"id\" : 6,\n" +
                        "      \"number\" : 9328746273126332,\n" +
                        "      \"cvc\" : 976,\n" +
                        "      \"type\" : \"Visa\",\n" +
                        "      \"currency\" : \"Ruble\",\n" +
                        "      \"balance\" : 0\n" +
                        "    } ]\n" +
                        "  }, {\n" +
                        "    \"id\" : 3,\n" +
                        "    \"number\" : 9813,\n" +
                        "    \"bank\" : \"VTB\",\n" +
                        "    \"card\" : [ {\n" +
                        "      \"id\" : 3,\n" +
                        "      \"number\" : 5893842117421237,\n" +
                        "      \"cvc\" : 786,\n" +
                        "      \"type\" : \"Mir\",\n" +
                        "      \"currency\" : \"Ruble\",\n" +
                        "      \"balance\" : 0\n" +
                        "    }, {\n" +
                        "      \"id\" : 4,\n" +
                        "      \"number\" : 9231278432412424,\n" +
                        "      \"cvc\" : 234,\n" +
                        "      \"type\" : \"MasterCard\",\n" +
                        "      \"currency\" : \"Ruble\",\n" +
                        "      \"balance\" : 0\n" +
                        "    }, {\n" +
                        "      \"id\" : 8,\n" +
                        "      \"number\" : 1873273647236487,\n" +
                        "      \"cvc\" : 754,\n" +
                        "      \"type\" : \"Mir\",\n" +
                        "      \"currency\" : \"Euro\",\n" +
                        "      \"balance\" : 0\n" +
                        "    } ]\n" +
                        "  } ]\n" +
                        "}"));
    }

    @Test
    public void getAccountById() throws Exception {
        this.mockMvc.perform(get(accUri + "/{idA}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"Account\" : {\n" +
                        "    \"id\" : 1,\n" +
                        "    \"number\" : 2314,\n" +
                        "    \"bank\" : \"Sberbank\",\n" +
                        "    \"card\" : [ {\n" +
                        "      \"id\" : 1,\n" +
                        "      \"number\" : 3475475743563566,\n" +
                        "      \"cvc\" : 727,\n" +
                        "      \"type\" : \"Visa\",\n" +
                        "      \"currency\" : \"Dollar\",\n" +
                        "      \"balance\" : 0\n" +
                        "    }, {\n" +
                        "      \"id\" : 7,\n" +
                        "      \"number\" : 4273623873627412,\n" +
                        "      \"cvc\" : 264,\n" +
                        "      \"type\" : \"Visa\",\n" +
                        "      \"currency\" : \"Euro\",\n" +
                        "      \"balance\" : 0\n" +
                        "    }, {\n" +
                        "      \"id\" : 9,\n" +
                        "      \"number\" : 2346512767824671,\n" +
                        "      \"cvc\" : 327,\n" +
                        "      \"type\" : \"Visa\",\n" +
                        "      \"currency\" : \"Dollar\",\n" +
                        "      \"balance\" : 0\n" +
                        "    } ]\n" +
                        "  }\n" +
                        "}"));
    }

    @Test
    public void getAccountByNotExistingId() throws Exception {
        this.mockMvc.perform(get(accUri + "/{idA}", 4))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteAccountById() throws Exception {
        this.mockMvc.perform(delete(accUri + "/{idA}", 3))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNotExistingAccountById() throws Exception {
        this.mockMvc.perform(delete(accUri + "/{idA}", 4))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void postNewAccount() throws Exception {
        AccountDto accountDto = new AccountDto();
        accountDto.setBank(Bank.Sberbank);
        accountDto.setNumber(2314);
        this.mockMvc.perform(post(accUri + "/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\": 2314, \"bank\": \"Sberbank\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"Account\": {\n" +
                        "        \"id\": 4,\n" +
                        "        \"number\": 2314,\n" +
                        "        \"bank\": \"Sberbank\",\n" +
                        "        \"card\": []\n" +
                        "    }\n" +
                        "}"));
    }

    @Test
    public void postNewAccountWithErrorInConstraintOfNumber() throws Exception {
        AccountDto accountDto = new AccountDto();
        accountDto.setBank(Bank.Sberbank);
        accountDto.setNumber(2314);
        this.mockMvc.perform(post(accUri + "/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\": 231, \"bank\": \"Sberbank\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void postNewAccountWithErrorInConstraintOfBank() throws Exception {
        AccountDto accountDto = new AccountDto();
        accountDto.setBank(Bank.Sberbank);
        accountDto.setNumber(2314);
        this.mockMvc.perform(post(accUri + "/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\": 2314, \"bank\": \"Sberbankb\"}"))
                .andExpect(status().is4xxClientError());
    }
}
