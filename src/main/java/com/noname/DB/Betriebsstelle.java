package com.noname.DB;

public class Betriebsstelle {

	private final String plc;
	private final String code;
	private final String langname;
	private final String kurzname;
	private final String kurztyp;
	private final String langtyp;
	private final String betriebszustand;
	private final String datumAb;
	private final String datumBis;
	private final String niederlassung;
	private final String regionalbereich;
	private final String letzteAenderung;
	
	public Betriebsstelle(String[] attributes) {
		plc = attributes[0];
		code = attributes[1];
		langname = attributes[2];
		kurzname = attributes[3];
		kurztyp = attributes[4];
		langtyp = attributes[5];
		betriebszustand = attributes[6];
		datumAb = attributes[7];
		datumBis = attributes[8];
		niederlassung = attributes[9];
		regionalbereich = attributes[10];
		letzteAenderung = attributes[11];
	}

	public String getPlc() {
		return plc;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getLangname() {
		return langname;
	}
	
	public String getKurzname() {
		return kurzname;
	}
	
	public String getKurztyp() {
		return kurztyp;
	}
	public String getLangtyp() {
		return langtyp;
	}
	
	public String getBetriebszustand() {
		return betriebszustand;
	}
	
	public String getDatumAb() {
		return datumAb;
	}

	public String getDatumBis() {
		return datumBis;
	}

	public String getNiederlassung() {
		return niederlassung;
	}

	public String getRegionalbereich() {
		return regionalbereich;
	}

	public String getLetzteAenderung() {
		return letzteAenderung;
	}

}
