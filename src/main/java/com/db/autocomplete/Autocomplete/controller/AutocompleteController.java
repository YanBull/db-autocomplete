package com.db.autocomplete.Autocomplete.controller;

import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.db.autocomplete.Autocomplete.service.AutocompleteService;

@RestController
public class AutocompleteController {

    @Autowired
    private AutocompleteService autocompleteService;

    @GetMapping("/api/v1/isalive")
	public ResponseEntity<?> isAlive() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
	}

    @GetMapping("/api/v1/autocomplete/{charSequence}")
	public ResponseEntity<?> autoComplete(
        @PathVariable(name = "charSequence", required = true) String charSequence
    ) {
        return autocompleteService.getAutocompleteList(charSequence);
	}
}
