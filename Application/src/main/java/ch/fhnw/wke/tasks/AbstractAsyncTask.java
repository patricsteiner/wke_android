package ch.fhnw.wke.tasks;

import android.os.AsyncTask;

import ch.fhnw.wke.util.Action;

public abstract class AbstractAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private Action<Result> onPostExecuteAction;
    private Action<Progress> onProgressUpdateAction;

    public void setOnPostExecuteAction(Action<Result> onPostExecuteAction) {
        this.onPostExecuteAction = onPostExecuteAction;
    }

    public void setOnProgressUpdateAction(Action<Progress> onProgressUpdateAction) {
        this.onProgressUpdateAction = onProgressUpdateAction;
    }

    @Override
    protected void onPostExecute(Result result) {
        if (onPostExecuteAction != null) {
            onPostExecuteAction.execute(result);
        }
    }

    @Override
    protected void onProgressUpdate(Progress[] values) {
        if (onProgressUpdateAction != null) {
            onProgressUpdateAction.execute(values[0]);
        }
    }

}
