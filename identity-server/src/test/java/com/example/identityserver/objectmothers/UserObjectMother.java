package com.example.identityserver.objectmothers;

import com.example.identityserver.domain.User;
import com.example.identityserver.domain.UserRole;
import com.github.javafaker.Faker;

public final class UserObjectMother {

    static Faker faker = new Faker();

    public static String getRandomUsername() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        return faker.random().nextInt(100, 10000).toString();
    }

    public static User getRandomUser() {
        return getRandomUser(faker.random().nextInt(0, 1) == 0 ? UserRole.USER : UserRole.ADMIN);
    }

    public static User getRandomUser(UserRole role) {
        return new User(getRandomUsername(), getRandomPassword(), role);
    }
}
