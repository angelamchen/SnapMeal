package com.achen.recipeGenerator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import springfox.documentation.annotations.ApiIgnore;

/**
 * Used for mapping index to swagger ui
 * @author angelachen
 *
 */
@Controller
@ApiIgnore
public class SwaggerUIController {
	
	@GetMapping("/")
	public String getDefaultMapping() {
		return "redirect:/swagger-ui.html";
	}
}
