package support;

import java.util.List;

public class Config {
    private String browser;
    private boolean betaVersion;
    private String testEnv;
    private boolean headless;
    private int pageLoadTimeout;
    private int implicitTimeout;
    private int explicitTimeout;
    private List<String> supportedOSList;

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public boolean isBetaVersion() {
        return betaVersion;
    }

    public void setBetaVersion(boolean betaVersion) {
        this.betaVersion = betaVersion;
    }

    public String getTestEnv() {
        return testEnv;
    }

    public void setTestEnv(String testEnv) {
        this.testEnv = testEnv;
    }

    public boolean isHeadless() {
        return headless;
    }

    public void setHeadless(boolean headless) {
        this.headless = headless;
    }

    public int getPageLoadTimeout() {
        return pageLoadTimeout;
    }

    public void setPageLoadTimeout(int pageLoadTimeout) {
        this.pageLoadTimeout = pageLoadTimeout;
    }

    public int getImplicitTimeout() {
        return implicitTimeout;
    }

    public void setImplicitTimeout(int implicitTimeout) {
        this.implicitTimeout = implicitTimeout;
    }

    public int getExplicitTimeout() {
        return explicitTimeout;
    }

    public void setExplicitTimeout(int explicitTimeout) {
        this.explicitTimeout = explicitTimeout;
    }

    public List<String> getSupportedOSList() {
        return supportedOSList;
    }

    public void setSupportedOSList(List<String> supportedOSList) {
        this.supportedOSList = supportedOSList;
    }
}