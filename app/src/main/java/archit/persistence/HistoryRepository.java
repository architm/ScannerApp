package archit.persistence;

import com.couchbase.lite.CouchbaseLiteException;

import java.util.Map;

import archit.model.History;

/**
 * Created by archit on 5/11/15.
 */
public interface HistoryRepository {

    public Map<String, Object> getAllByKey(String key);

    public void updateKey(String key, Map<String, Object> newEntries) throws CouchbaseLiteException;
}
