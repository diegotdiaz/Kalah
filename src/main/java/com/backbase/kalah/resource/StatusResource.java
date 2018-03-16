package com.backbase.kalah.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="/ecv")
public class StatusResource {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> status() {
		return ResponseEntity.ok().build();
	}
}
