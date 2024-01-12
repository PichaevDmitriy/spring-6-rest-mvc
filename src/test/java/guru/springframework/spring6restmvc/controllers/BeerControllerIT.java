package guru.springframework.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.*;
import guru.springframework.spring6restmvc.entities.*;
import guru.springframework.spring6restmvc.mappers.*;
import guru.springframework.spring6restmvc.model.*;
import guru.springframework.spring6restmvc.repositories.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.annotation.*;
import org.springframework.test.web.servlet.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.context.*;

import java.util.*;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void listBeers(){
        List<BeerDTO> beerList = beerController.listBeers();
        assertEquals(3, beerList.size());
    }

    @Test
    void getById(){
        List<Beer> beerList = beerRepository.findAll();
        Beer beer= beerList.get(0);

        BeerDTO dto = beerController.getBeerById(beer.getId());
        assertThat(dto).isNotNull();
    }
    @Test
    void getById_NotFound(){
        assertThrows(NotFoundException.class, () -> beerController.getBeerById(UUID.randomUUID()));
    }

    @Test
    @Transactional
    @Rollback
    void saveNewBeer(){
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New beer")
                .build();
        ResponseEntity responseEntity = beerController.saveNewBeer(beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    void updateExistingBeer(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String newBeerName = "UPDATED";
        beerDTO.setBeerName(newBeerName);

        ResponseEntity responseEntity = beerController.updateById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(204);

        Beer updatedBeer = beerRepository.findById( beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(newBeerName);
    }

    @Test
    void updateNotFound(){
        assertThrows(NotFoundException.class, () -> {
            beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Test
    @Transactional
    @Rollback
    void patchExistingBeer(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String newBeerName = "UPDATED";
        beerDTO.setBeerName(newBeerName);

        ResponseEntity responseEntity = beerController.patchBeerById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(204);

        Beer updatedBeer = beerRepository.findById( beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(newBeerName);
    }

    @Test
    void patchNotFound(){
        assertThrows(NotFoundException.class, () -> {
            beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Test
    void patchBeerBadBeerName() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Namedfsfsdfsdfsdfsdfsdfffdfdfsddfsdfsdfsdfsdfsdfsdf");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(beerMap))
            .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isBadRequest());
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

    @Test
    @Transactional
    @Rollback
    void deleteById(){
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity responseEntity = beerController.deleteById(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThrows(NotFoundException.class, () -> beerController.getBeerById(beer.getId()));
    }

    @Test
    @Transactional
    @Rollback
    void deleteByIdNotFound(){
        assertThrows(NotFoundException.class, () -> beerController.deleteById(UUID.randomUUID()));
    }

}