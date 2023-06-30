package com.db.autocomplete.Autocomplete.to;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutocompleteResponse {
    
    List<String> station_list;
    String time_taken;
    long number_of_stations_found;
}
