package org.prgrms.kdt.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.MemoryVoucherRepository;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.prgrms.kdt.voucher.VoucherService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    //stub
    class OrderRepositoryStub implements OrderRepository{

        @Override
        public Order insert(Order order) {
            return null;
        }
    }

    @Test
    @DisplayName("오더가 생성되야 한다.(stub)")
    void createOrderByStub() {
        System.out.println("Te = " + TestEnum.WOW);
        TestEnum wow = TestEnum.valueOf("+");
        System.out.println("wow = " + wow);

        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(fixedAmountVoucher);

        //넘겨준 stub은 아무런 일도 하지 않음. 걍 객체 생성만 해주기 위해 가짜 객체 만듦
        OrderService orderService = new OrderService(new VoucherService(voucherRepository), new OrderRepositoryStub());

        Order order = orderService.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmountVoucher.getVoucherId());

        //상태에 집중해서 테스트 코드 작성
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }

    @Test
    @DisplayName("오더가 생성되야 한다.(mock) - mockito 이용")
    void createOrderByMock() {

        VoucherService voucherServiceMock = mock(VoucherService.class);
        OrderRepository orderRepositoryMock = mock(OrderRepository.class);
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        when(voucherServiceMock.getVoucher(fixedAmountVoucher.getVoucherId())).thenReturn(fixedAmountVoucher);
        OrderService orderService = new OrderService(voucherServiceMock, orderRepositoryMock);


        Order order = orderService.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmountVoucher.getVoucherId());

        // 이러이러한 메서드들이 호출되어야 한다.
        verify(voucherServiceMock).userVoucher(fixedAmountVoucher);
        verify(voucherServiceMock).getVoucher(fixedAmountVoucher.getVoucherId());
        verify(orderRepositoryMock).insert(order);

        // 이 순서대로 호출되어야 한다
        InOrder inOrder = inOrder(voucherServiceMock); // inOrder 객체를 사용하면 명시한 순서대로 메서드를 검증해야 통과한다.
        inOrder.verify(voucherServiceMock).getVoucher(fixedAmountVoucher.getVoucherId());
        inOrder.verify(voucherServiceMock).userVoucher(fixedAmountVoucher);
    }
}