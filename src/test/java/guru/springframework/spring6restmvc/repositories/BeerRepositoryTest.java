package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.*;
import guru.springframework.spring6restmvc.model.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import java.math.*;

@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    public void testSaveBeer() {
        Beer savedBeer = beerRepository.save(
                Beer.builder()
                        .beerName("My beer")
                        .beerStyle(BeerStyle.IPA)
                        .upc("1244ffdkflsf")
                        .price(new BigDecimal(14))
                        .build());
        beerRepository.flush();
        assertNotNull(savedBeer);
        assertNotNull(savedBeer.getId());
    }
}