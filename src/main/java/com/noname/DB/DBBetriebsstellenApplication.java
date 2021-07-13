package com.noname.DB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DBBetriebsstellenApplication {
	
	/**config**/
	//the source file from where the data is located
	private final String betriebsstellenFile = "Betriebsstellen.csv";
	//the expected header. This is to avoid misinterpretation of the file
	private final String[] expectedHeader = {"﻿PLC", "RL100-Code", "RL100-Langname",
		"RL100-Kurzname", "Typ Kurz", "Typ Lang", "Betriebszustand",
		"Datum ab", "Datum bis", "Niederlassung", "Regionalbereich",
		"Letzte Änderung"};
	//this gives a rough estimate how many entries there are in the table
	private int approximateEntries = 25000;
	//make code case (in)sensitive
	private boolean ignoreCodeCase = true;
	
	
	/**variables**/
	//hasmap storing all the data with the keys
	//setting the length of the hashmap to approximateEntries/loadFactor(0.75) reduces the amount of rehashing needed
	private HashMap<String, Betriebsstelle> betriebsstelleByCode = new HashMap<>(4*approximateEntries/3);
	//saves the header for later lookup
	private final Betriebsstelle betriebsstellenHeader;
	
	
	public DBBetriebsstellenApplication() throws IOException{
		
		try(BufferedReader br = new BufferedReader(new FileReader(betriebsstellenFile))) {
		    String line = br.readLine();
		    
		    //check that the header actually matches the interpretation of the data
		    String[] header = line.split(";");
		    if(header.length != expectedHeader.length) {
		    	throw new IndexOutOfBoundsException("The file contains a different number of columns as expected from the"
		    										+ "provided expected header");
		    }
		    for(int index = 0; index < header.length; index++) {
		    	if(!header[index].equals(expectedHeader[index])) {
		    		System.err.println("Column " + index + ": " + header[index] +" is deviating from the expected header "
		    							+ expectedHeader[index]);
		    	}
		    }
		    betriebsstellenHeader = new Betriebsstelle(header);
		    
		    //read in the data
		    while ((line = br.readLine()) != null) {
		    	String[] arguments = line.split(";");
		    	Betriebsstelle betriebsstelle = new Betriebsstelle(arguments);
		    	if(ignoreCodeCase) {
		    		betriebsstelleByCode.put(arguments[1].toLowerCase(), betriebsstelle);
		    	} else {
		    		betriebsstelleByCode.put(arguments[1], betriebsstelle);
		    	}
		    }
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(DBBetriebsstellenApplication.class, args);
	}

	@GetMapping("/betriebsstellen/{code}")
	public Betriebsstelle getBetriebsstelleByCode(@PathVariable String code) {
		//"header" query return the header
		if(code.toLowerCase().equals("header")) {
			return betriebsstellenHeader;
		}
		
		if(ignoreCodeCase) {
			code = code.toLowerCase();
		}
		
		Betriebsstelle betriebsstelle = betriebsstelleByCode.get(code);
		if(betriebsstelle != null) {
			return betriebsstelle;
		}
		
		return codeNotFound(code);
	}
	
	//handles error if code cannot be found; could also return a default answer or do something else
	private Betriebsstelle codeNotFound(String code){
		throw new NoSuchElementException ("Code:" + code+ " not found");
	}

}
