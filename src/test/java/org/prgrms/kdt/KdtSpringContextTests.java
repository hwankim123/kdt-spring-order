package org.prgrms.kdt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.order.*;
import org.prgrms.kdt.voucher.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class) // application context가 만들어짐
@ContextConfiguration(classes = {AppConfiguration.class}) // application context가 만들어지기 위한 xml 설정혹은 클래스 설정.만약 설정안하면 클래스 안에 선언된 static @Configuration 클래스를 찾게 됨
public class KdtSpringContextTests {

    @Configuration
    static class Config{
        @Bean
        VoucherRepository voucherRepository(){
            return new VoucherRepository() {
                @Override
                public Optional<Voucher> findById(UUID voucherId) {
                    return Optional.empty();
                }

                @Override
                public Voucher insert(Voucher voucher) {
                    return null;
                }
            };
        }
    }

    @Autowired // application context를 autowired를 통해 자동으로 불러올 수 있따.
    ApplicationContext applicationContext;

    @Autowired
    OrderService orderService;

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("application Context가 생성되야 한다")
    void applicationContext(){
        assertThat(applicationContext, notNullValue());
    }

    @Test
    @DisplayName("VoucherRepository가 빈으로 등록되어 있어야 한다.")
    void testVoucherRepositoryCreation(){
        VoucherRepository bean = applicationContext.getBean(VoucherRepository.class);
        assertThat(bean, notNullValue());

    }

    @Test
    @DisplayName("orderService를 사용해서 주문을 생성할 수 있다.")
    void testOrderService(){
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(fixedAmountVoucher);

        Order order = orderService.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmountVoucher.getVoucherId());

        //상태에 집중해서 테스트 코드 작성
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }
}
