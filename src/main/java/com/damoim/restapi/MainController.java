package com.damoim.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**  * MainController
 *
 * @author incheol.jung
 * @since 2021. 02. 13.
 */
@RestController
public class MainController {
	@GetMapping
	public ResponseEntity get(){
		return new ResponseEntity("hello last test", HttpStatus.ACCEPTED);
	}
}
