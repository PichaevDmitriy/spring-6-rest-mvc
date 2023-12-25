package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.mappers.*;
import guru.springframework.spring6restmvc.model.*;
import guru.springframework.spring6restmvc.repositories.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository repository;
    private final BeerMapper beerMapper;
    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<BeerDTO> listBeers() {
        return null;
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return null;
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beer) {

    }

    @Override
    public void deleteById(UUID beerId) {

    }

    @Override
    public void patchById(UUID beerId, BeerDTO beer) {

    }
}
