package ch.fhnw.wke.tasks;

import ch.fhnw.wke.Config;

public class TransferLearningRestCall extends AbstractRestCall<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        getRestTemplate().postForObject(Config.REST_ENDPOINT_TRANSFER_LEARNING, null, Void.class);
        return null;
    }

}