package org.prgrms.kdt;

import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Optional;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdt.order", "org.prgrms.kdt.voucher"})
//@ComponentScan(basePackageClasses = {Order.class, Voucher.class})
//@PropertySource("application.properties")
public class AppConfiguration {

//    @Bean
//    public VoucherRepository voucherRepository(){
//        return voucherId -> Optional.empty();
//    }
//
//    @Bean
//    public OrderRepository orderRepository(){
//        return new OrderRepository() {
//            @Override
//            public void insert(Order order) {
//
//            }
//        };
//    }

//    @Bean
//    public VoucherService voucherService(VoucherRepository voucherRepository){
//        return new VoucherService(voucherRepository);
//    }
//
//    @Bean
//    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository){
//        return new OrderService(voucherService, orderRepository);
//    }

    @Bean(initMethod = "init")
    public BeanOne beanOne(){
        return new BeanOne();
    }
    class BeanOne implements InitializingBean {
        public void init(){
            System.out.println("BeanOne.init");
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("BeanOne.afterPropertiesSet");
        }
    }
}
