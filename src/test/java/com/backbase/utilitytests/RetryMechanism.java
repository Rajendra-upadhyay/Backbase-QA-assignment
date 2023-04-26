package com.backbase.utilitytests;

import com.backbase.bookingpojo.BookingDetails;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.awaitility.Awaitility;
import org.testng.annotations.Test;

import java.time.Duration;

//note this package and classes are not part of assignment
public class RetryMechanism extends BaseClass {


    @Test(dataProvider = "getBookingRequestPayload", enabled = false)
    public void createNewBooking(BookingDetails bookingDetailsPayload) {
        int count = 1;
        do {
            System.out.println("===================" + count);
            Response response =
                    getRequestSpecification(bookingDetailsPayload)
                            .post()
                            .then()
                            .log().all()
                            .extract().response();
            coolingPeriod(1);
        } while (count++ < 3);
    }

    public int createBooking() {

        int randomNumber = Integer.valueOf(RandomStringUtils.randomNumeric(5));
        System.out.println("====time===" + randomNumber);
        return randomNumber % 2;
    }

    @Test(timeOut = 6100, enabled = false)
    public void checkStatusIsSuccess() {

        Awaitility
                .waitAtMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofSeconds(2))
                .until(() -> createBooking() == 0);
    }

    @Test
    public void compareStrings() {
        String str1 = "rajendra";
        String str2 = "prahlad";
        System.out.println(str2.compareTo(str1));
        String str="raj!~!@#$%$^&*()_+Upadhyay";
        str=str.replaceAll("[^a-z0-9A-Z]","");
        System.out.println(str.toUpperCase());
    }
}
