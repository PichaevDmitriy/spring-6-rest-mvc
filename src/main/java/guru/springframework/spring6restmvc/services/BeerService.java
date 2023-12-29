package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.*;

import java.util.*;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);
    List<BeerDTO> listBeers();

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    Boolean deleteById(UUID beerId);

    Optional<BeerDTO> patchById(UUID beerId, BeerDTO beer);
}
