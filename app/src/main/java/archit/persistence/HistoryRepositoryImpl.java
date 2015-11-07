package archit.persistence;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by archit on 5/11/15.
 * Responsible for Handling the DB Transactions
 * Currently have getByKey and UpdateByKey
 */
public class HistoryRepositoryImpl implements HistoryRepository {

    private Database database;
    private Manager manager;

    private static final String TAG = "code";
    private Context ctx;

    public HistoryRepositoryImpl(Context ctx, String dbname) throws IOException, CouchbaseLiteException {
        this.ctx = ctx;
        // 1. use default settings (read/write access)
        manager = new Manager( new AndroidContext(ctx),
                Manager.DEFAULT_OPTIONS );

        // 2. Check database name given by user
        // No upper case allowed in CBL!
        // Only the following characters are valid:
        // abcdefghijklmnopqrstuvwxyz0123456789_$()+-/

        if ( ! Manager.isValidDatabaseName(dbname)) {
            // report...
            return;
        }
        // 3. Get existing db with that name
        // or create a new one if it doesn't exist
        database = manager.getDatabase(dbname);
    }
    @Override
    public Map<String, Object> getAllByKey(String key) {
        Document doc = database.getDocument(key);
        return doc.getProperties();
    }

    @Override
    public void updateKey(String key, Map<String, Object> entries) throws CouchbaseLiteException {
        Document doc = database.getDocument(key);
        Map<String, Object> updatedEntries = new HashMap<String, Object>();
        if(doc.getProperties() != null)
        updatedEntries.putAll(doc.getProperties());
        updatedEntries.putAll(entries);
        doc.putProperties(updatedEntries);
    }
}
