package src.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkHttp {
    final OkHttpClient client = new OkHttpClient();

    public String run(String url) throws IOException {
        Request req = new Request.Builder().url(url).build();

        try (Response res = client.newCall(req).execute()) {
                return res.body().string();
        }
    }
}
