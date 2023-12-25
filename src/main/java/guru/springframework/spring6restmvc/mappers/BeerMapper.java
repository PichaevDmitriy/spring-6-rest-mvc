package guru.springframework.spring6restmvc.mappers;

import guru.springframework.spring6restmvc.entities.*;
import guru.springframework.spring6restmvc.model.*;
import org.mapstruct.*;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer (BeerDTO dto);
    BeerDTO beerToBeerDto (Beer beer);
}
