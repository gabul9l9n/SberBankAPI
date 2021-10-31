package org.example.controller;

import org.example.model.dto.AccountDto;
import org.example.exception.message.AccountMessage;
import org.example.service.account.AccountService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accountDtoList = accountService.getAllAccount();

        HttpHeaders header = new HttpHeaders();
        header.add("description", AccountMessage.GET_ALL_ACCOUNTS);

        return new ResponseEntity<>(accountDtoList, header, HttpStatus.OK);
    }

    @GetMapping("/{idA}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable int idA) {
        AccountDto accountDto = accountService.getAccount(idA);

        HttpHeaders header = new HttpHeaders();
        header.add("description", AccountMessage.GET_ACCOUNT + idA);

        return new ResponseEntity<>(accountDto, header, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Void> createNewAccount(@RequestBody @Valid AccountDto accountDto) {
        accountDto = accountService.createNewAccount(accountDto.getNumber(), accountDto.getBank());

        HttpHeaders header = new HttpHeaders();
        header.set("description", AccountMessage.CREATE_NEW_ACCOUNT);

        return new ResponseEntity<>(header, HttpStatus.OK);
    }

    @DeleteMapping("/{idA}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int idA) {
        AccountDto accountDto = accountService.getAccount(idA);

        HttpHeaders header = new HttpHeaders();
        header.add("description", AccountMessage.DELETE_ACCOUNT);

        accountService.deleteAccount(idA);

        return new ResponseEntity<>(header, HttpStatus.OK);
    }
}