package org.prgrms.kdt.order;

public enum TestEnum {
    WOW("+");

    private final String name;

    TestEnum(String name){
        this.name = name;
    }

    @Override
    public String toString(){return name;}
}
