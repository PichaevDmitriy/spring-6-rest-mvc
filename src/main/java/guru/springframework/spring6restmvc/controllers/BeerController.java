package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.model.*;
import guru.springframework.spring6restmvc.services.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {
    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
    private final BeerService beerService;

    @GetMapping(value = BEER_PATH )
    public List<BeerDTO> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping(value = BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("Get beer by id - in controller");
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(value = BEER_PATH)
    public ResponseEntity saveNewBeer(@RequestBody @Validated BeerDTO beer) {
        BeerDTO savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/v1/beer/" + savedBeer.getId());

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = BEER_PATH_ID)
    public ResponseEntity updateById(@NotNull @PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDTO beer) {
       if (beerService.updateBeerById(beerId, beer).isEmpty()){
           throw new NotFoundException();
       };
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = BEER_PATH_ID)
    public ResponseEntity patchBeerById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {
        if (beerService.patchById(beerId, beer).isEmpty()){
            throw new NotFoundException();
        };
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    @DeleteMapping(value = BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {
        if (!beerService.deleteById(beerId)) {
            throw new NotFoundException();
        };
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
