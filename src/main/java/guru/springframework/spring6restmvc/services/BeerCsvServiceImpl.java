package guru.springframework.spring6restmvc.services;

import com.opencsv.bean.*;
import guru.springframework.spring6restmvc.model.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

@Slf4j
@Service
public class BeerCsvServiceImpl implements  BeerCsvService {
  @Override
  public List<BeerCsvRecord> convertCsv(File csvFile) throws FileNotFoundException {
    try {
      return new CsvToBeanBuilder<BeerCsvRecord>(new FileReader(csvFile))
        .withType(BeerCsvRecord.class)
        .build().parse();
    } catch (FileNotFoundException ex){
      throw new RuntimeException(ex);
    }
  }
}
