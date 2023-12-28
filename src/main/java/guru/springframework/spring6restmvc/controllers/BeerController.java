package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.model.*;
import guru.springframework.spring6restmvc.services.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {
    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
    private final BeerService beerService;

    @PatchMapping(value = BEER_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {
        beerService.patchById(beerId, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {
       if (beerService.updateBeerById(beerId, beer).isEmpty()){
           throw new NotFoundException();
       };
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = BEER_PATH)
    public ResponseEntity handlePost(@RequestBody BeerDTO beer) {
        BeerDTO savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/v1/beer/" + savedBeer.getId());

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping(value = BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {
        beerService.deleteById(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = BEER_PATH )
    public List<BeerDTO> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping(value = BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("Get beer by id - in controller");
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }


}
