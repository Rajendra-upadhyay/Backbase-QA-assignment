package com.backbase.bookingpojo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookingDetails {
    String firstname;
    String lastname;
    double totalprice;
    boolean depositpaid;
    BookingDates bookingdates;
    String additionalneeds;
}
