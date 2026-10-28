package com.workintech.s18d1.util;

import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import org.springframework.http.HttpStatus;

public final class BurgerValidation {

    private BurgerValidation() {
    }

    public static void validateNew(Burger burger) {
        if (burger == null) {
            badRequest("Burger is required");
        }
        if (burger.getName() == null ||
            burger.getName().isBlank()) {
            badRequest("Burger name is required");
        }
        if (burger.getPrice() == null ||
            burger.getPrice() <= 0) {
            badRequest("Burger price must be positive");
        }
        if (burger.getIsVegan() == null) {
            badRequest("Burger vegan status is required");
        }
        if (burger.getBreadType() == null) {
            badRequest("Bread type is required");
        }
        if (burger.getContents() == null ||
            burger.getContents().isBlank()) {
            badRequest("Burger contents are required");
        }
    }

    public static void validateUpdate(Burger burger) {
        if (burger == null ||
            burger.getId() == null ||
            burger.getId() <= 0) {
            badRequest("Valid burger id is required");
        }
        if (burger.getName() != null &&
            burger.getName().isBlank()) {
            badRequest("Burger name cannot be blank");
        }
        if (burger.getPrice() != null &&
            burger.getPrice() <= 0) {
            badRequest("Burger price must be positive");
        }
    }

    private static void badRequest(String message) {
        throw new BurgerException(message, HttpStatus.BAD_REQUEST);
    }
}
