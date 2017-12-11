package ch.fhnw.wke.tasks;

import android.os.AsyncTask;

import ch.fhnw.wke.util.Action;

public abstract class ExtendedAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private Action<Result> onPostExecuteAction;

    public void setOnPostExecuteAction(Action<Result> onPostExecuteAction) {
        this.onPostExecuteAction = onPostExecuteAction;
    }

    @Override
    protected void onPostExecute(Result result) {
        if (onPostExecuteAction != null)
            onPostExecuteAction.execute(result);
    }

}
