package com.aquariux.crypto.controller;

import com.aquariux.crypto.common.Const;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController(Const.API)
public class SupportController {

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ResponseEntity<?> checkStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK);
        response.put("message", "OK");
        response.put("currentTime",  new Date());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
