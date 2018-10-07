package com.solitary.ksapp.component;

import android.content.Intent;
import android.provider.CalendarContract;

import java.util.Calendar;

import com.solitary.ksapp.model.Position;

/**
 * Add event to calendar and make reminder(once / daily)
 */
public class CalendarHelper {

    public static Intent onAddEventClicked(Position position) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");

        Calendar cal = Calendar.getInstance();
        long startTime = cal.getTimeInMillis();
        long endTime = cal.getTimeInMillis() + 60 * 60 * 1000;

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

        intent.putExtra(CalendarContract.Events.TITLE, "Try "+position.getTitle());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, position.getDescription());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Bedroom");
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

        return intent;

    }
}