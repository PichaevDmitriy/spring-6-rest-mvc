package guru.springframework.spring6restmvc.model;


import com.opencsv.bean.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerCsvRecord {
  @CsvBindByName
  Integer row;

  @CsvBindByName(column = "count.x")
  Integer count;

  @CsvBindByName
  String abv;

  @CsvBindByName
  String ibu;

  @CsvBindByName
  Integer id;

  @CsvBindByName
  String beer;

  @CsvBindByName
  String style;

  @CsvBindByName(column = "brewery_id")
  Integer brewerId;

  @CsvBindByName
  Float ounces;

  @CsvBindByName
  String style2;

  @CsvBindByName(column = "count.y")
  String count_y;

  @CsvBindByName
  String city;

  @CsvBindByName
  String state;

  @CsvBindByName
  String label;
}
