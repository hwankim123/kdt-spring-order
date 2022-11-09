package org.prgrms.kdt.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private static final long MAX_VOUCHER_AMOUNT = 100000;

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if(amount < 0) throw new IllegalArgumentException("할인 금액은 음수일 수 없습니다.");
        if(amount == 0) throw new IllegalArgumentException("할인 금액은 0원일 수 없습니다.");
        if(amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException("할인 금액은 100000원보다 낮아야 합니다.");
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount){
        long discountAmount = beforeDiscount - amount;
        return (discountAmount < 0) ? 0 : discountAmount;
    }
}
