package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    public Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
}
