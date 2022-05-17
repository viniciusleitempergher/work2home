package com.work2home.publica.project.validation;

import java.time.LocalDate;
import java.util.GregorianCalendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BrazilDateValidator implements ConstraintValidator<BrazilDate, String> {

	@Override
	public boolean isValid(String data, ConstraintValidatorContext constraintValidatorContext) {

		try {
			String[] charsData = data.split("");

			if ((!charsData[2].equals("/") && !charsData[5].equals("/")) || charsData.length != 10) {
				return false;
			}

			GregorianCalendar gc = new GregorianCalendar();
			charsData = data.split("/");

			int dia = Integer.parseInt(charsData[0]);
			int mes = Integer.parseInt(charsData[1]);
			int ano = Integer.parseInt(charsData[2]);
			boolean eBisexto = gc.isLeapYear(ano);

			if ((ano <= 1900 || ano > LocalDate.now().getYear()) || (mes <= 0 || mes > 12) || (dia <= 0 || dia > 31)) {
				return false;
			} else if (mes == 2 && (!eBisexto && dia > 28 || eBisexto && dia > 29)) {
				return false;
			} else if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia == 31) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void initialize(BrazilDate constraintAnnotation) {
	}
}
