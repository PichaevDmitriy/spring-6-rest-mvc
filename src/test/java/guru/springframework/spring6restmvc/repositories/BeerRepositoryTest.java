package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.*;
import guru.springframework.spring6restmvc.model.*;
import jakarta.validation.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.dao.*;

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


    @Test
    public void testSaveBeer_BeerNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
                    beerRepository.save(
                            Beer.builder()
                                    .beerName("My beer dfsffffffffff3746dasuhdksajdasdjahsdjkashdakjsdhkjashdajkshdkahsdkkjakhsdhjkasd53847563487563847563847567834563874538475634875638475634875634875634876567845fffffffffffffffffffffffffff")
                                    .beerStyle(BeerStyle.IPA)
                                    .upc("1244ffdkflsf")
                                    .price(new BigDecimal(14))
                                    .build());
                    beerRepository.flush();
                }
        );
    }
}