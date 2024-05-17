package com.example.demo;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

@Remote
public interface IRequester {
    public <T> List<T> fetchDataFromUrl(String url, Class<T> valueType);
}
