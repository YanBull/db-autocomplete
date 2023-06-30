package com.db.autocomplete.Autocomplete.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.db.autocomplete.Autocomplete.component.Setup;
import com.db.autocomplete.Autocomplete.data.TrainStation;
import com.db.autocomplete.Autocomplete.to.AutocompleteResponse;
import com.db.autocomplete.Autocomplete.to.ErrorResponse;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AutocompleteService {
    
    private static final Logger log = LoggerFactory.getLogger(AutocompleteService.class);

    private static final int MIN_SEARCH_KEY_LEN = 3; 

    @Autowired
    Setup setup;

    public ResponseEntity<?> getAutocompleteList(String charSequence){

        long startTime = System.currentTimeMillis();

        ResponseEntity error = validateParam(charSequence);
        
        if(!error.getStatusCode().equals(HttpStatus.OK)){
            return error;
        }
        
        List<TrainStation> stations = setup.getTrainStations();

        List<String> matchingObjects = stations.stream()
            .filter(s -> s.getName().toLowerCase().contains(charSequence.toLowerCase()))
            .map(TrainStation::toJsonResponseString)
            .collect(Collectors.toList());

        Long timeElapsed = System.currentTimeMillis() - startTime;

        AutocompleteResponse resp = new AutocompleteResponse(
            matchingObjects, 
            timeElapsed.toString() + " ms" , 
            matchingObjects.size());

        return new ResponseEntity(resp, HttpStatus.OK);
        
    }

    private ResponseEntity<?> validateParam(String charSequence) {

        if (charSequence == null || charSequence.length()<3){
            return new ResponseEntity(
                new ErrorResponse("003",
                 "Search key should be at least " + MIN_SEARCH_KEY_LEN +  " characters long"),
                HttpStatus.BAD_REQUEST);
        }

        if (setup.getMaxNameLength() != 0 && charSequence.length() > setup.getMaxNameLength()){
            return new ResponseEntity(
                new ErrorResponse("002",
                 "Search key should not be longer than " + setup.getMaxNameLength() + " characters"),
                HttpStatus.BAD_REQUEST);
        }

        String regex = "(.)*(\\d)(.)*";      
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(charSequence).matches()){
            return new ResponseEntity(
                new ErrorResponse("001",
                 "Only alphabetical sequences are allowed"),
                HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

}
