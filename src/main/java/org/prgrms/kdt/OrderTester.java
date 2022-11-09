package org.prgrms.kdt;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.*;

@Component
public class OrderTester {

    private static final Logger logger = LoggerFactory.getLogger("org.prgrms.kdt.OrderTester");

    @Autowired
    private static DefaultListableBeanFactory bf;

    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String version = environment.getProperty("kdt.version");
        Integer minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount", Integer.class);
        List supportVendors = environment.getProperty("kdt.support-vendors", List.class);
        logger.info("logger name => {} {} {}", logger.getName(), 2, 3);
        logger.info("version = " + version);
        logger.info("minimumOrderAmount = " + minimumOrderAmount);
        logger.info("supportVendors = " + supportVendors);

        var customerId = UUID.randomUUID();

        VoucherRepository voucherRepository = applicationContext.getBean(VoucherRepository.class);
        VoucherRepository voucherRepository2 = applicationContext.getBean(VoucherRepository.class);
        System.out.println("voucherRepository = " + voucherRepository);
        System.out.println("voucherRepository2 = " + voucherRepository2);
        System.out.println("voucherRepository2 == voucherRepository = " + (voucherRepository2 == voucherRepository));
        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        OrderService orderService = applicationContext.getBean(OrderService.class);
        Order order = orderService.createOrder(customerId, new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount is not {0}", order.totalAmount()));

        applicationContext.close();
        for(String n : bf.getBeanDefinitionNames()){
            System.out.println(n + " \t" + bf.getBean(n).getClass().getName());
        }
    }
}
