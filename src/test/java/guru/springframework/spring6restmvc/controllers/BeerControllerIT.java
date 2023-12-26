package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.model.*;
import guru.springframework.spring6restmvc.repositories.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.annotation.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void listBeers(){
        List<BeerDTO> beerList = beerController.listBeers();
        assertEquals(3, beerList.size());
    }

    @Test
    @Transactional
    @Rollback
    void ListBeersEmptyList(){
        beerRepository.deleteAll();
        List<BeerDTO> beerList = beerController.listBeers();
        assertNotNull(beerList);
        assertTrue(beerList.isEmpty());
    }

}