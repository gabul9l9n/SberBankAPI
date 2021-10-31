package org.example.controller;

import org.example.model.dto.AccountDto;
import org.example.model.dto.CardDto;
import org.example.exception.message.CardMessage;
import org.example.service.account.AccountService;
import org.example.service.card.CardService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/account/{idA}/card")
public class CardController {
    private final CardService cardService;
    private final AccountService accountService;

    public CardController(CardService cardService, AccountService accountService) {
        this.cardService = cardService;
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CardDto>> getAllCard(@PathVariable int idA) {
        List<CardDto> cardListDto = cardService.getAllCard(idA);

        HttpHeaders header = new HttpHeaders();
        header.add("description", CardMessage.GET_ALL_CARDS);


        return new ResponseEntity<>(cardListDto, header, HttpStatus.OK);
    }

    @GetMapping("/{idC}")
    public ResponseEntity<CardDto> getCard(@PathVariable int idA, @PathVariable int idC) {
        CardDto cardDto = cardService.getCard(idA, idC);

        HttpHeaders header = new HttpHeaders();
        header.add("description", CardMessage.GET_CARD);

        return new ResponseEntity<>(cardDto, header, HttpStatus.OK);
    }

    @DeleteMapping("/{idC}")
    public ResponseEntity<Void> deleteCard(@PathVariable int idA, @PathVariable int idC) {
        CardDto cardDto = cardService.getCard(idA, idC);

        HttpHeaders header = new HttpHeaders();
        header.add("description", CardMessage.DELETE_CARD);

        cardService.deleteCard(idA, idC);

        return new ResponseEntity<>(header, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Void> createNewCard(@PathVariable int idA, @Valid @RequestBody CardDto cardDto) {
        AccountDto accountDto = accountService.getAccount(idA);

        HttpHeaders header = new HttpHeaders();
        header.add("description", CardMessage.CREATE_NEW_CARD);

        cardDto = cardService.createNewCard(idA, cardDto.getNumber(), cardDto.getCvc(), cardDto.getType(), cardDto.getCurrency());

        return new ResponseEntity<>(header, HttpStatus.OK);
    }

    @PostMapping(path = "/{idC}/balance")
    public ResponseEntity<Void> addDepositToBalance(@PathVariable int idA, @PathVariable int idC,
                                                       @Valid @RequestBody CardDto cardDto) {
        HttpHeaders header = new HttpHeaders();
        header.add("description", CardMessage.ADD_DEPOSIT);

        int balance = cardDto.getBalance();
        cardDto = cardService.addDepositToBalance(idA, idC, balance);
        cardDto.setBalance(balance + cardDto.getBalance());

        return new ResponseEntity<>(header, HttpStatus.OK);
    }

    @GetMapping("/{idC}/balance")
    public ResponseEntity<Integer> getCardBalance(@PathVariable int idA, @PathVariable int idC) {
        CardDto cardDto = cardService.getCard(idA, idC);

        HttpHeaders header = new HttpHeaders();
        header.add("description", CardMessage.GET_CARD_BALANCE);

        return new ResponseEntity<>(cardDto.getBalance(), header, HttpStatus.OK);
    }
}