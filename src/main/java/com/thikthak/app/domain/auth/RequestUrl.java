package com.thikthak.app.domain.auth;

import org.springframework.http.HttpMethod;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "AUTH_REQUEST_URL")
public class RequestUrl implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    private Long id;

    String configAttribute;
    HttpMethod httpMethod;
    String url;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigAttribute() {
        return configAttribute;
    }

    public void setConfigAttribute(String configAttribute) {
        this.configAttribute = configAttribute;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
