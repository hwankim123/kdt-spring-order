package org.prgrms.kdt.voucher;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MemoryVoucherRepository implements VoucherRepository, InitializingBean, DisposableBean {

    Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId)); // 만약 get해오지 못한다면 Null을 담아서 return
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("MemoryVoucherRepository.postConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("MemoryVoucherRepository.afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("MemoryVoucherRepository.destroy");
    }
}
