package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.*;

import java.io.*;
import java.util.*;

public interface BeerCsvService {
  List<BeerCsvRecord> convertCsv(File csvFile) throws FileNotFoundException;
}
