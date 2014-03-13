package bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import entities.Currency;

public class Starter {

	public static Boolean checkValidation(String[] parsedInput) {
		if (parsedInput[0].equals("exit"))
			return true;
		// if (parsedInput[0].matches("/?"))
		// return true;
		if ((parsedInput[0].equals("+")) || (parsedInput[0].equals("-"))) {
			if (parsedInput.length < 3)
				return false;
			if (!parsedInput[1].matches("[A-Z]{3}"))
				return false;
			try {
				switch (parsedInput[0]) {
				case "-":
					int cash = Integer.parseInt(parsedInput[2]);
					if (cash <= 0)
						return false;
					break;
				case "+":
					if (parsedInput.length < 3)
						return false;
					int anotherCash = Integer.parseInt(parsedInput[2]);
					int number = Integer.parseInt(parsedInput[3]);
					if ((number <= 0) || (anotherCash <= 0))
						return false;
					break;
				}
			} catch (NumberFormatException ex) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Map<String, Currency> curr = new HashMap<String, Currency>();

		String[] parsedData;
		Scanner in = new Scanner(System.in);

		while (true) {
			parsedData = StringUtils.split(in.nextLine());

			if (parsedData.length == 0) {
				System.out.println("ERROR");
				continue;
			}
			if (!checkValidation(parsedData)) {
				System.out.println("ERROR");
				continue;
			}
			if (parsedData[0].equals("exit")) {
				in.close();
				break;
			}

			switch (parsedData[0]) {
			case "+": {
				if (!curr.containsKey(parsedData[1])) {
					Currency currency = new Currency();
					currency.setName(parsedData[1]);
					curr.put(parsedData[1], currency);
				}

				Currency currency = curr.get(parsedData[1]);

				if (currency.addNotes(Integer.parseInt(parsedData[2]),
						Integer.parseInt(parsedData[3]))) {
					curr.put(parsedData[1], currency);
					System.out.println("OK");
				} else {
					System.out.println("ERROR");
				}
				break;
			}
			case "-": {
				if (!curr.containsKey(parsedData[1])) {
					System.out.println("ERROR");
					continue;
				}
				Currency currency = curr.get(parsedData[1]);
				ArrayList<String> outResult = new ArrayList<String>(
						currency.getNotes(Integer.parseInt(parsedData[2])));
				if ((outResult.isEmpty()) || (outResult == null)) {
					System.out.println("ERROR");
				} else {
					for (int i = 0; i < outResult.size(); i++) {
						System.out.println(outResult.get(i));
					}
					System.out.println("OK");
				}
				break;
			}
			case "?": {
				for (Map.Entry<String, Currency> entry : curr.entrySet()) {
					Currency currency = entry.getValue();
					ArrayList<String> allMoney = new ArrayList<String>(
							currency.showMoney());
					System.out.println(currency.getName());
					for (int i = 0; i < allMoney.size(); i++) {
						System.out.println(allMoney.get(i));
					}
				}
				System.out.println("OK");
				break;
			}
			default: {
				System.out.println("ERROR");
				break;
			}
			}
		}
		in.close();
	}
}