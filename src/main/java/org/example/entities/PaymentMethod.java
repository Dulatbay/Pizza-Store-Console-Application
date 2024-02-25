package org.example.entities;

public enum PaymentMethod {
    CASH, MASTER_CARD;

    public static PaymentMethod getByNumber(int number) {
        switch (number) {
            case 0 -> {
                return CASH;
            }
            case 1 -> {
                return MASTER_CARD;
            }
            default -> throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case CASH -> {
                return "CASH";
            }
            case MASTER_CARD -> {
                return "MASTER CARD";
            }
            default -> {
                return "";
            }
        }
    }
}
