package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.util.*;

import java.io.*;
import java.util.*;

@SpringBootTest
class BeerCsvServiceImplTest {
  BeerCsvService beerCsvService  = new BeerCsvServiceImpl();

  @Test
  void convertCsv() throws FileNotFoundException {
    File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
    List<BeerCsvRecord> recs = beerCsvService.convertCsv(file);
    System.out.println(recs.size());
    assertThat(recs.size()).isGreaterThan(0);
  }
}