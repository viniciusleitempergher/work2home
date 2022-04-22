package com.work2home.publica.userutilities.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user-utilities")
public class CategoriaController {
	@GetMapping("/categoria")
	public String test() {
		return "aaa";
	}
}
