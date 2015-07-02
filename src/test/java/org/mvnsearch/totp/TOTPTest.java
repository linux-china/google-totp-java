package org.mvnsearch.totp;

import org.junit.Assert;
import org.junit.Test;

/**
 * totp test
 *
 * @author linux_china
 */
public class TOTPTest {
    String randomCode = "1234567";

    @Test
    public void testGenerateUrl() throws Exception {
        System.out.println(TOTP.getAuthUrl("libing.chen@gmail.com", "mvnsearch", randomCode));
    }

    @Test
    public void testCheckCode() throws Exception {
        Assert.assertTrue(TOTP.checkCode(randomCode, 672468));
    }
}
