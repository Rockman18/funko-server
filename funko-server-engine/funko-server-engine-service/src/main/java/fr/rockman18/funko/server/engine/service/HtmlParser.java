package fr.rockman18.funko.server.engine.service;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.InitializingBean;

public class HtmlParser implements InitializingBean {

    private boolean proxyActivated;
    private boolean proxyAuthenticated;
    private String proxyHost;
    private String proxyPort;
    private String proxyUser;
    private String proxyPassword;

    public void setProxyActivated(Boolean proxyActivated) {
	this.proxyActivated = proxyActivated;
    }

    public void setProxyAuthenticated(Boolean proxyAuthenticated) {
	this.proxyAuthenticated = proxyAuthenticated;
    }
    
    public void setProxyHost(String proxyHost) {
	this.proxyHost = proxyHost;
    }

    public void setProxyPort(String proxyPort) {
	this.proxyPort = proxyPort;
    }

    public void setProxyUser(String proxyUser) {
	this.proxyUser = proxyUser;
    }

    public void setProxyPassword(String proxyPassword) {
	this.proxyPassword = proxyPassword;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
	if (proxyActivated) {
	    if (proxyAuthenticated) {
		Authenticator.setDefault(new Authenticator() {
		public PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(proxyUser, proxyPassword.toCharArray());
		}
	    });
		System.setProperty("http.proxyUser", proxyUser);
		System.setProperty("http.proxyPassword", proxyPassword);
	    }

	    System.setProperty("http.proxyHost", proxyHost);
	    System.setProperty("http.proxyPort", proxyPort);
	}
    }

    public Document parseUrl(String url) throws IOException {
	return Jsoup.connect(url).timeout(60000).maxBodySize(0)
		.userAgent(
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36")
		.get();
    }
}
