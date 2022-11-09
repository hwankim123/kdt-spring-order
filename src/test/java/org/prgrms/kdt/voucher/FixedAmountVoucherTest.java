package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucherTest.class);

    @BeforeAll
    static void setup(){
        logger.info("@BeforeAll - 단 한번 실행");
    }

    @BeforeEach
    void init(){
        logger.info("BeforeEach - 매 테스트 마다 코드 실행");
    }

    @Test
    void assertNotequal() {
        assertNotEquals(3, 1+1);
    }

    @Test
    @DisplayName("FIxedAmountVoucher의 discount가 정상적으로 이루어지는지 test")
    void testDiscount() {
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        assertEquals(900, voucher.discount(1000));
    }

    @Test
    @DisplayName("FixedAmountVoucher의 할인 금액은 마이너스가 될 수 없다.")
    //@Disabled // 테스트코드를 지우고싶진 않지만, 잠시만 비활성화하고 싶을 때
    void testIfMinus(){
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100));
    }

    @Test
    @DisplayName("할인된 금액은 마이너스가 될 수 없다.")
    void testMinusDiscountedAmount() {
        FixedAmountVoucher vou = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        assertEquals(0, vou.discount(900));

    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 10000000))
        );
    }
}