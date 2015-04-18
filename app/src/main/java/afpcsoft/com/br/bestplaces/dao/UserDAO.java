package afpcsoft.com.br.bestplaces.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import afpcsoft.com.br.bestplaces.Utils.DatabaseHelper;

/**
 * Created by AndréFelipe on 16/04/2015.
 */
public class UserDAO {

    private SQLiteDatabase db;
    private Context context;

    public UserDAO(Context context) {
        this.db = DatabaseHelper.getInstance(context).getDb();
        this.context = context;
    }

    public void createUser(){
        ContentValues values = new ContentValues(5);

        values.put("nome",	"ADMIN");

        db.insertOrThrow("user", null, values);
    }
}
