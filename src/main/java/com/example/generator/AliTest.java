package com.example.generator;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.diagnosis.DiagnosisUtils;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;

/**
 * <p>
 * AliTest描述:测试
 * <p>
 * 包名称：com.example.generator
 * 类名称：AliTest
 * 全路径：com.example.generator.AliTest
 * 类描述：测试
 *
 * @author 31756-YHL
 * @date 2025年12月03日 15:27
 */
public class AliTest {
    public static void main(String[] args) throws AlipayApiException {
        String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCaa8oDttMo6Oynw1aoCToznky8lqWhnmgK1JFwpOBpcONNg3hIduN2hdB0CXJ3lRGP99A/EY3wtRjZK6cRBf/f/MuU7/xlyL1N0JpgqAFKFCMJCG2i6yGU2o40Pd6z0qdxlUw5kMIZEc9J1hNXj+thlbgJN7khV1tuMnd/B+KVCzb8K/whBD8s6VbSXtUErUKAOc29lZZPG9YygJrUXptF5yNL9UVUB4yvv5s2Dv1+zgNXKwVl/MXEZ9MiUmGaHEhfKkd3M1wsOHTfBOPyZIt4PzM1NX4K+HJPKGgVdi/daJIqwuQxwJ4cesabe8atkupxTcankbi6vlPBGPpU3T1pAgMBAAECggEAZyWYyuJUKf9sFKNmPtfrCI+0skPuXwZweMuEWrnHLKc7LYujzMOdLI41r0ygKUBpesENWRNgvGg7MNT3w2c+g19YnSOwdEyiJlgwxl9yfQj2XhJDKY/l3ogedMbe+z66miQfxlPrUtOSor9cLCD2GTjotT+DacHwUKtlJnUd5G+Nu+LobGS6PHIK+n2WKVWXEZqj8ARnA+9GTfPJ6MC6lldArd7x90oGwtuRzSp/Gl7Mv7UPn/6V6detQXPg1s1nJJ+I+HfXKX6/RKjj8o4W17YNUUNJoSbmXX1tCjDeJiQAiWazsSpoFFDReMMr2jDuJ276ruHaKLqSECUfsC0sAQKBgQDgWTu/UILkOO80oCNs+Omm5JzCQbM+Oh8rGHPoYdovr3GN0CMn6pEOxcxJB9Rbe8rb3I6OmZuztC4284CWbHAjQNtzyhwJxyC6Nvt41Fq7A5XGkGhPQzTqCc4hkSiCg06oAH+e/6XOvApQ/6ibzxbYQW8bWaL/BlybQU3J4H08uwKBgQCwNP4/WEm80UkPmLtPHdjwVnUPKg6/VNtMbYvPGWEIRYNMGNQsoID7l8joZgRKx1QkTm/WHSNgDJcK0c6ZVpJVG0ZTiSgSkv/pa4ttM9PdVYWm/KhY3BCWd6tuJRBvdoh43s4kHtdLRGkgl/YidqTRS8UE9FC7I8rrth0/rNB+KwKBgCXRBNA32whrTG77e104C/KU4JOiDXu0wmI6Sfdm3ydglGyKJBLraC4sS0YYKsSM9mHfKKyC2gsNmSisHQW+G5zlptg6vkUrqqmY8QSqDu0tKocA+oQ52OaGZpej50cwqQaKpvh/MUzKx5Zlsd8ppjkntnnOvznrsZp+BIW98H5XAoGAXl6TSCOloi1H/sVik7W9iMIVAWEZ8tikdmM4/FpwQDjSX7/walIvog8hd22joNTuOF+Vv8ttNbSodA/3oOHfjxsDaBvNzVzTGBUT4BFgx2pVISCxnJ2HeL5wnpXVHo0R/AH/zxKBZXsm/mDEXqLCFKGKy+N5FTDPLbwkIAUSyG8CgYA8IrOul3v7rq2dcILJ78wk0K+bZ612Ov4Oq3b4bTHrrKJvhcTMT78dWsgelwi1WWhBEqBLrmGGOU9bjroza4QrfP+McXjKtvaG+lbD5ZjYM85JN+o06UEjZq7Pp5jUrT+b5XJXd8G48M4lF3Q5bAsbE+VhguUwY5hMsRiOaE+uYQ==";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmmvKA7bTKOjsp8NWqAk6M55MvJaloZ5oCtSRcKTgaXDjTYN4SHbjdoXQdAlyd5URj/fQPxGN8LUY2SunEQX/3/zLlO/8Zci9TdCaYKgBShQjCQhtoushlNqOND3es9KncZVMOZDCGRHPSdYTV4/rYZW4CTe5IVdbbjJ3fwfilQs2/Cv8IQQ/LOlW0l7VBK1CgDnNvZWWTxvWMoCa1F6bRecjS/VFVAeMr7+bNg79fs4DVysFZfzFxGfTIlJhmhxIXypHdzNcLDh03wTj8mSLeD8zNTV+CvhyTyhoFXYv3WiSKsLkMcCeHHrGm3vGrZLqcU3Gp5G4ur5TwRj6VN09aQIDAQAB";
        String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuI/JzOUg3OcFZxIN9EF0a7A+v6JERXIA7/rex8XcDi0qNTkcevw+aYsPRfmakrYTlz2ZHXy2bDFVcbAp0LE1AhehqEIQyo/xz2FeLcvEIHKCjPgCubfjIxucH3QR3wFPjwFlPL0K8Mu2P2wcTQQkHwayW7C6hWJAaGGxwhofP1bEJMn7WO40KabFc4Ge+USiGcxYcWw63rKqCeFcszjd67jwypbe8PVh7D6AWacvakGKJak89QfUodN/qui96zx7FITeyrNZre276rStr4kqupIXKMwlPYB4iNASD6shsOa75GH0+AEQa4wWIxcXGxWN4mw9ve7xwGH1eimh8UAyNQIDAQAB";
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
        alipayConfig.setAppId("2021006114668975");
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode("937b766966544b63b230bf0601e0XX33");
        AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
        System.out.println(request.getTextParams());
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
             String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
             System.out.println(diagnosisUrl);
        }
    }
}