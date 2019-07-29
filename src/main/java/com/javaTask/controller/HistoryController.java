package com.javaTask.controller;

import com.javaTask.exceptions.NoTimeException;
import com.javaTask.model.ProductTO;
import com.javaTask.model.enums.Status;
import com.javaTask.repository.ProductTORepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Aleksandr Beryozkin
 */

@RestController
@RequestMapping(path = "/api")
public class HistoryController {

    private static final Logger LOG = Logger.getLogger(HistoryController.class.getName());

    private final ProductTORepository productTORepository;

    private String from = null;
    private String till = null;
    private long timeFrom = 0l;
    private long timeTill = 0l;

    public HistoryController(ProductTORepository productTORepository) {
        this.productTORepository = productTORepository;
    }

    @GetMapping(path = "/history")
    public ResponseEntity<?> getHistory(@RequestParam("userid") Long userId, @RequestParam("cartid") Long cartId, @RequestParam(value = "from", required = false) String from, @RequestParam(value = "till", required = false) String till) {
        Map <String, Object> response = new HashMap<>();

        try {
            convertToMilliseconds();
        } catch (NoTimeException e) {
            LOG.info("Exception occured in convertToMilliseconds() within HistoryController");

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/index.html");
            return new ResponseEntity<String>(headers, HttpStatus.FOUND);
        }

        List<ProductTO> results = productTORepository.getProductsHistoryByTimeAndUserId(userId, timeFrom, timeTill, Status.CLOSED);

        response.put("userId", userId);
        response.put("cartId", cartId);
        response.put("results", results);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void convertToMilliseconds() throws NoTimeException {
        if (from == null || till == null) {
            throw new NoTimeException("Dates were not sent to HistoryController");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateFrom = from + " 00:00:00";
        LOG.info(dateFrom);
        String dateTill = till + " 23:59:59";
        LOG.info(dateTill);

        try {
            timeFrom = sdf.parse(dateFrom).getTime();
            LOG.info("Date from: " + timeFrom);
            timeTill = sdf.parse(dateTill).getTime();
            LOG.info("Date till: " + timeTill);
        } catch (ParseException e1) {
            LOG.info("An exception occured when converting date from string to long timestamp");
            e1.printStackTrace();
        }
    }
}
