package com.midasit.bungae;

import com.midasit.bungae.user.dto.User;

import static org.junit.Assert.assertEquals;

public class TestUtil {
    public static void isEqualAllValueOfUser(User user1, User user2) {
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getPassword(), user2.getPassword());
        assertEquals(user1.getName(), user2.getName());
        assertEquals(user1.getEmail(), user2.getEmail());
        assertEquals(user1.getGender(), user2.getGender());
    }
}
