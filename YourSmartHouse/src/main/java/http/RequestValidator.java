package http;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class RequestValidator {

    private final ArrayList<String> errors;
    private final HttpServletRequest request;
    private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("^(-)?(0|[1-9](\\d+)?+)(\\.\\d(\\d)?)?+$");

    public RequestValidator(HttpServletRequest request) {
        this.request = request;
        this.errors = new ArrayList<>();
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public ArrayList<String> getErrors() {
        return this.errors;
    }

    private boolean gatherError(boolean c, String m) {
        if(!c)
            errors.add(m);
        return c;
    }

    private boolean required(String s) {
        return (s != null && !s.isBlank());
    }

    public boolean assertMatch(String val, Pattern p, String mes) {
        String param = request.getParameter(val);
        boolean c = required(param) && p.matcher(param).matches();
        return gatherError(c, mes);
    }

    public boolean assertInt(String val, String mes) {
        return assertMatch(val, INT_PATTERN, mes);
    }

    public boolean assertDouble(String val, String mes) {
        return assertMatch(val, DOUBLE_PATTERN, mes);
    }

    public boolean assertEmail(String val, String mes) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        return assertMatch(val, p, mes);
    }

    public boolean assertInts(String val, String mes) {
        String[] params = request.getParameterValues(val);
        boolean allInt = Arrays.stream(params).allMatch(param -> INT_PATTERN.matcher(param).matches());
        return gatherError(allInt, mes);
    }

    public boolean assertSize(String first, String second, String mes) {
        String[] fList = request.getParameterValues(first);
        String[] sList = request.getParameterValues(second);
        return gatherError(fList.length==sList.length, mes);
    }

}
