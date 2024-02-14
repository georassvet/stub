package ru.riji.stub.service;

import okhttp3.*;
import org.springframework.stereotype.Service;
import ru.riji.stub.model.ServiceModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class InfluxExporter {

    private final String host = "http://89.108.64.113:8086";
    private final String db = "stub_metrics";
    private final String measurement = "system_name";
    private static OkHttpClient client = new OkHttpClient();
    
    private String createBody(Map<String, ServiceModel> services){
        StringBuilder body = new StringBuilder();

        Map<String, ServiceModel> map = new TreeMap<>(services);
//        LocalDateTime localDateTime = LocalDateTime.parse(data.getRows().get(i).get(j), dateTimeFormatter);
//        //ZoneId zoneId = ZoneId.of("America/Chicago");
//        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
//        Instant instant = zonedDateTime.toInstant();
//
//        //  Instant instant = Instant.parse("2023-04-12T22:27:00.00Z");
//        long epoch = instant.getEpochSecond()  * 1000000000 + instant.getNano();
//        time = Long.toString(epoch);

        for (Map.Entry<String, ServiceModel> entry : map.entrySet()) {

            String format = String.format("%s,name=%s delay=%s,count=%s\n", measurement, entry.getKey(), entry.getValue().getCurrentDelay(), entry.getValue().getCounter());
            body.append(format);
        }
        return body.toString();
    }
   public void sendMetrics(Map<String, ServiceModel> services) throws IOException {
        String url = String.format("%s/write?db=%s", host, db);

        String body = createBody(services);

        RequestBody requestBody =  RequestBody.create(body.toString(), MediaType.parse("text/plain"));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", String.format("Token %s:%s", "admin", "Bosco314"))
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        response.close();
        System.out.println(String.format("%s %s\n%s", response.code(), db, body));
    }
}
