package org.ops4j.methodtypeleak.app;

import javax.security.enterprise.credential.Credential;

public class IdTokenCredential implements Credential {

    private String token;


    public IdTokenCredential(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
