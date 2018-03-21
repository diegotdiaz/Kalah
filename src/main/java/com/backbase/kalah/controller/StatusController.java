package com.backbase.kalah.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.backbase.kalah.util.Messages;
/**
 * Controller to verify application status
 * @author dtoro
 *
 */
@RestController
@RequestMapping("/ecv")
public class StatusController {

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> status() {
		return ResponseEntity.ok(Messages.MESSAGE_APP_IS_UP);
	}
}
