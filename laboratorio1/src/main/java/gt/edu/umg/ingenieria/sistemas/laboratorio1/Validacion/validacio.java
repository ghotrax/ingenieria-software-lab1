package gt.edu.umg.ingenieria.sistemas.laboratorio1.Validacion;

import java.util.Calendar;
import java.util.Locale;
import static java.util.Calendar.*;
import java.util.Date;

public class validacio {

    public static boolean isOverOrEqualsNYears(Date birtDate, int _var) {
        Calendar currentCalendar = getCalendar(new Date());
        Calendar inputCalendar = getCalendar(birtDate);
        int _val = currentCalendar.get(YEAR) - inputCalendar.get(YEAR);
        if
        (
                inputCalendar.get(MONTH) > currentCalendar.get(MONTH) ||
                        (inputCalendar.get(MONTH) == currentCalendar.get(MONTH) && inputCalendar.get(DATE) > currentCalendar.get(DATE))
        )
        {
            _val--;
        }
        return _val >= _var;
    }

    private static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }
}
