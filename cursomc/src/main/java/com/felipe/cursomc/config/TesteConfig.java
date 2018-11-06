package com.felipe.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.felipe.cursomc.services.DBService;

@Configuration
@Profile("BdTeste")
public class TesteConfig {
	
	@Autowired
	DBService dbservvice;
	
	@Bean
	public boolean instaciaBdTeste() throws ParseException {
		dbservvice.instaciarBdTeste();
		return true;
	}
}
