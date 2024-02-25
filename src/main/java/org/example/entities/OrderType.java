package org.example.entities;

public enum OrderType {
    DELIVERY, PICK_UP;

    public static OrderType getByNumber(int number) {
        switch (number) {
            case 0 -> {
                return DELIVERY;
            }
            case 1 -> {
                return PICK_UP;
            }
            default -> throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case DELIVERY -> {
                return "DELIVERY";
            }
            case PICK_UP -> {
                return "PICK UP";
            }
            default -> {
                return "";
            }
        }
    }
}
