package com.backbase.bookingpojo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookingDates {
    String checkin;
    String checkout;
}
