package com.work2home.publica.project.utils;

import java.time.format.DateTimeFormatter;

public class Formatador {
	public static DateTimeFormatter getFormatter() {
		return DateTimeFormatter.ofPattern("dd/MM/yyyy");
	}
}
