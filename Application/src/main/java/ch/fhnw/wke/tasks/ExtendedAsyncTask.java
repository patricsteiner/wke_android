package ch.fhnw.wke.tasks;

import android.os.AsyncTask;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import ch.fhnw.wke.util.Action;

public abstract class ExtendedAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private Action<Result> onPostExecuteAction;

    public void setOnPostExecuteAction(Action<Result> onPostExecuteAction) {
        this.onPostExecuteAction = onPostExecuteAction;
    }

    @Override
    protected void onPostExecute(Result result) {
        onPostExecuteAction.execute(result);
    }

}
