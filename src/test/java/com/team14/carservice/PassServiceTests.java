package com.team14.carservice;

import com.team14.carservice.service.PassServiceImpl;
import com.team14.carservice.service.common.PassService;
import org.junit.Test;

import static org.springframework.test.util.AssertionErrors.assertTrue;

public class PassServiceTests {

    PassService passGen = new PassServiceImpl();

    @Test
    public void whenPasswordGeneratedUsingPass_thenSuccessful() {
        String password = passGen.generateRandomPassword();
        int specialCharCount = 0;
        for (char c : password.toCharArray()) {
            if (c >= 33 || c <= 47) {
                specialCharCount++;
            }
        }
        assertTrue("Password validation failed in Pass", specialCharCount >= 2);
    }

}
