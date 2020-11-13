package com.github.microwww.security.cli;

import java.io.IOException;
import java.util.Map;

public interface HttpClient {

    String get(String url, String... param) throws IOException;

    String post(String url, String... param) throws IOException;

    String post(String url, Map<String, String> param) throws IOException;
}
