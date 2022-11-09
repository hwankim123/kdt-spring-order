package org.prgrms.kdt.voucher;


import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class HemcrestAssertionTests {
    @Test
    @DisplayName("여러 hamcrest matcher 테스트")
    void hamcrestTest() {
        assertEquals(2, 1+1);
        assertThat(1+1, equalTo(2));
        assertThat(1+1, is(2));
        assertThat(1+1, anyOf(is(1), is(2))); // 어떤 상태가 1이 될수도 있고 2도 될수도 있을 때

        assertNotEquals(1+1, 1);
        assertThat(1+1, not(equalTo(1)));
    }

    @Test
    @DisplayName("컬렉션에 대한 matcher 테스트")
    void hamcrestMatcherListTest() {
        List<Integer> list = Arrays.asList(2, 2, 3);
        assertThat(list, hasSize(3));
        assertThat(list, everyItem(greaterThan(1)));
        assertThat(list, containsInAnyOrder(3, 2, 2));
        assertThat(list, hasItem(2));
        assertThat(list, hasItem(greaterThanOrEqualTo(2))); // 2보다 크거나 같은 원소가 있는지
    }
}
