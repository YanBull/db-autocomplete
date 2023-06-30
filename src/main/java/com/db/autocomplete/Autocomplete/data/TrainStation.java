package com.db.autocomplete.Autocomplete.data;

import com.opencsv.bean.CsvBindByPosition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainStation {
    
    @CsvBindByPosition(position = 0)
    Long evaNr;

    @CsvBindByPosition(position = 1)
    String ds100;

    @CsvBindByPosition(position = 2)
    String name;

    @CsvBindByPosition(position = 3)
    String verkehr;

    @CsvBindByPosition(position = 4)
    Double laenge;

    @CsvBindByPosition(position = 5)
    Double breite;

    public String toJsonResponseString(){
        return this.evaNr.toString() + " - " + this.ds100 + " - " + this.name;
    }
}
