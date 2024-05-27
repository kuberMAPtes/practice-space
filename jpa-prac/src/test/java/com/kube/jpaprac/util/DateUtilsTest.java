package com.kube.jpaprac.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {

    @Test
    void test() {
        LocalDate localDate = DateUtils.parseDate("2222-02-17");
        LocalDate expected = LocalDate.of(2222, Month.FEBRUARY, 17);

        assertThat(localDate).isEqualTo(expected);
    }
}