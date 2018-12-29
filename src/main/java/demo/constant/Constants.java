package demo.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Constants {

	public static final String SEVEN_PARAMETERS = " {}={} {}={} {}={} {}={} {}={} {}={} {}={}";
	public static final String FIVE_PARAMETERS = " {}={} {}={} {}={} {}={} {}={}";
	public static final String FOUR_PARAMETERS = " {}={} {}={} {}={} {}={}";
	public static final String THREE_PARAMETERS = " {}={} {}={} {}={}";
	public static final String TWO_PARAMETERS = " {}={} {}={}";
	public static final String ONE_PARAMETERS = " {}={} ";
	public static final String PRINCIPAL_ID = "Principal id";
	public static final String PRINCIPAL_ROLE = "Principal role";
	public static final String STORE = "store";
	public static final String CITY = "city";
	public static final String EMPLOYEE = "employee";
	public static final String COACH = "coach";
	public static final String DAY = "day";
	public static final String MONTH = "month";
	public static final String QUARTER = "quarter";
	public static final String HALFYEAR = "halfYear";
	public static final String YEAR = "year";
	public static final String PAGE_LOGIN = "/login";
	public static final String DENIED = "denied";
	public static final String INDEX = "index";
	public static final String REDIRECT = "redirect:";
	public static final String BEGIN = "begin";
	public static final String ALL_CLIENTS = "allClients";
	public static final String FORM_CLIENT = "clientForm";
	public static final String NOT_FOUND_CARD = "not found card: ";
	public static final String NOT_FOUND_ID = "not found id: ";
	public static final String FORM_SEND_MONEY = "sendMoneyForm";
	public static final String CANNOT_CREATE_TRANSACTION = "Can't create transaction, try again later ...";
	public static final String CONNECTION_REFUSED = "Connection refused, try again later ...";
	public static final String INPUT_AMOUNT = "Input";
	public static final String OUTPUT_AMOUNT = "Output";
	public static final String MESSAGE = "message";
	public static final String PASS_FIRST_CASE = "pass first case";
	public static final String ERROR = "error";
	public static final String RESULT = "result";
	public static final List<String> LIST_CLASSES = new ArrayList<>(
			Arrays.asList(Constants.COACH, Constants.STORE, Constants.CITY, Constants.EMPLOYEE, Constants.DAY,
					Constants.MONTH, Constants.QUARTER, Constants.HALFYEAR, Constants.YEAR));
	public static final String END = "end";
	public static final String ENTITY = "entity";

	private Constants() {
	}
}
