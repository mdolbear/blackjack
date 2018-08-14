package com.oracle.blackjack;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@ConfigurationProperties("app")
public class BlackJackApplicationProperties {

    private String applicationName;
    private String applicationDescription;

    /**
     * Answer my default instance
     */
    public BlackJackApplicationProperties() {

        super();
    }

    /**
     * Answer my applicationName
     *
     * @return java.lang.String
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * Set my applicationName
     *
     * @param applicationName java.lang.String
     */
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * Answer my applicationDescription
     *
     * @return java.lang.String
     */
    public String getApplicationDescription() {
        return applicationDescription;
    }

    /**
     * Set my applicationDescription
     *
     * @param applicationDescription java.lang.String
     */
    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }

}
