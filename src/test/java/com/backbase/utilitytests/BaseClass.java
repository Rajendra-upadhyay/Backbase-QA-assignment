package com.backbase.utilitytests;

import com.backbase.bookingpojo.BookingDates;
import com.backbase.bookingpojo.BookingDetails;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import net.datafaker.Faker;
import org.testng.annotations.DataProvider;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class BaseClass {

    String baseURI = "https://restful-booker.herokuapp.com/booking";

    public RequestSpecification getRequestSpecification(BookingDetails bookingDetailsPayload) {
        return RestAssured.given()
                .baseUri(baseURI)
                .headers(getHeaders())
                .log().all()
                .body(bookingDetailsPayload);
    }

    public Map<String, Object> getHeaders() {
        Map<String, Object> headers = new LinkedHashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        return headers;
    }

    @DataProvider(name = "getBookingRequestPayload")
    public Iterator<BookingDetails> getBookingPayload() {
        Faker faker = new Faker();
        BookingDates bookingDates =
                BookingDates.builder()
                        .checkin(String.valueOf(faker.date().future(10, TimeUnit.DAYS)))
                        .checkout(String.valueOf(faker.date().future(15, TimeUnit.DAYS)))
                        .build();
        BookingDetails bookingDetails =
                BookingDetails.builder()
                        .firstname(faker.name().firstName())
                        .lastname(faker.name().lastName())
                        .totalprice(faker.number().randomNumber(5, true))
                        .additionalneeds("breakfast")
                        .bookingdates(bookingDates).build();
        List<BookingDetails> bookingDetailsList = new ArrayList<>();
        bookingDetailsList.add(bookingDetails);
        return bookingDetailsList.iterator();
    }

    public void coolingPeriod(long milliSeconds){
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(milliSeconds));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
