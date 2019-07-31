package com.javaTask.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaTask.model.ProductTO;
import com.javaTask.repository.ProductTORepository;
import com.javaTask.service.ProductTOService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Aleksandr Beryozkin
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @MockBean
    private ProductTOService productTOService;

    @Test
    public void testLoadCartInfo() throws Exception {
        final String baseUrl = "http://localhost:"+randomServerPort+"/cart?userid=1&cartid=1";

        ProductTO product1 = new ProductTO("product1", 1, 10.1, 11l);
        product1.setId(1l);
        ProductTO product2 = new ProductTO("product2", 2, 20.1, 22l);
        product2.setId(2l);

        List<ProductTO> outputs = Arrays.asList(product1, product2);

        when(productTOService.getAllProductsByCartId(1l)).thenReturn(outputs);

        String expected = om.writeValueAsString(outputs);

        ResponseEntity<String> result = restTemplate.getForEntity(baseUrl, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        JSONAssert.assertEquals(expected, result.getBody(), false);

        verify(productTOService, times(1)).getAllProductsByCartId(1l);
    }
}
