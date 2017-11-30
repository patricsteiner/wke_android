package ch.fhnw.wke.tasks;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public abstract class RestCall<Params, Progress, Result> extends ExtendedAsyncTask<Params, Progress, Result> {

    private RestTemplate restTemplate;

    public RestCall() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
