package gc.nicoarbelaez.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import gc.nicoarbelaez.models.calendar.CalendarModel;

public class CalendarUtils {

    public static CalendarModel findCalendar(String calendarName, List<CalendarModel> calendarList) {
        return calendarList
                .stream()
                .filter(calendar -> calendar.getCalendarName().equalsIgnoreCase(calendarName))
                .findFirst()
                .orElse(null);
    }

    public static CalendarModel findCalendar(String calendarName, Set<CalendarModel> calendarList) {
        return findCalendar(calendarName, new ArrayList<>(calendarList));
    }
}
