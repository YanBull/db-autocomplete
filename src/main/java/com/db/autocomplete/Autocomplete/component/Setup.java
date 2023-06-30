package com.db.autocomplete.Autocomplete.component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.db.autocomplete.Autocomplete.data.TrainStation;
import com.opencsv.bean.CsvToBeanBuilder;

@Component
public class Setup {
    
    private List<TrainStation> trainStations;
    
    private static final Logger log = LoggerFactory.getLogger(Setup.class);

    private Optional<String> longestName;

    @PostConstruct
    private void setupData() throws IOException {

        File file = new File(getClass().getClassLoader().getResource("D_Bahnhof_2016_01_alle.csv").getFile());

        List<TrainStation> stations = new CsvToBeanBuilder(new FileReader(file, StandardCharsets.UTF_8))
            .withSeparator(';')
            .withSkipLines(1)
            .withType(TrainStation.class)
            .build()
            .parse();

        log.info("Set up " + stations.size() + " stations");
        trainStations = stations;

        longestName = stations.stream()
            .filter(s -> s.getName() != null)
            .map(TrainStation::getName)
            .max(Comparator.comparingInt(String::length));
        
    }

    public List<TrainStation> getTrainStations(){
        return trainStations;
    }

    public int getMaxNameLength(){
        if (longestName.isPresent()){
            return longestName.get().length();
        } else {
            return 0;
        }
    }
}
