package guru.springframework.spring6restmvc.model;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerCsvRecord {
  Integer row;
  Integer count;
  String abv;
  String ibu;
  Integer id;
  String beer;
  String style;
  Integer brewerId;
  Float ounces;
  String style2;
  String count_y;
  String city;
  String state;
  String label;

}
