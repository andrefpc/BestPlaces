package afpcsoft.com.br.bestplaces.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by AndréFelipe on 16/04/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    /**
     * Este é o endereço onde o android salva os bancos de dados criado pela
     * aplicação, /data/data/<namespace da aplicacao>/databases/
     */
    private String DBPATH = "";

    // Este é o nome do banco de dados que iremos utilizar
    private static final String DB_NAME = "bestPlacesDB.sqlite";

    private static final int DATABASE_VERSION = 23;

    private Context context;

    private SQLiteDatabase db;

    private static DatabaseHelper mInstance = null;

    /**
     * O construtor necessita do contexto da aplicação
     */
    public DatabaseHelper(Context context) {
		/*
		 * O primeiro argumento é o contexto da aplicacao O segundo argumento é
		 * o nome do banco de dados O terceiro é um pondeiro para manipulação de
		 * dados, não precisaremos dele. O quarto é a versão do banco de dados
		 */

        super(context, DB_NAME, null, DATABASE_VERSION);
        DBPATH = context.getFilesDir() + "/";
        this.context = context;

        try {
            getDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static DatabaseHelper getInstance(Context context) {
        /**
         * use the application context as suggested by CommonsWare. this will
         * ensure that you dont accidentally leak an Activitys context (see this
         * article for more information:
         * http://android-developers.blogspot.nl/2009
         * /01/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context);
        }
        return mInstance;
    }

    /**
     * Os métodos onCreate e onUpgrade precisam ser sobreescrito
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion)
        try {
            // Zero o numero de revisao ao apagar o banco

            // SharedPreferences preferencesVersaoRodape =
            // context.getSharedPreferences(Constantes.RODAPE_NUMERO_REVISAO,
            // Context.MODE_PRIVATE);
            // Editor editorVersaoRodape = preferencesVersaoRodape.edit();
            // editorVersaoRodape.putInt(Constantes.RODAPE_NUMERO_REVISAO, 0);
            // editorVersaoRodape.commit();

            copyDatabase();

        } catch (IOException e) {
            throw new Error("Error upgrading database");
        }
    }

    /**
     * Método auxiliar que verifica a existencia do banco da aplicação.
     */
    private boolean checkDataBase() {

        SQLiteDatabase db = null;

        try {
            String path = DBPATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READONLY);
            db.close();
        } catch (SQLiteException e) {
            // O banco não existe
            // Log.e("DataBaseHelper", "check if db exists", e);
        }

        // Retorna verdadeiro se o banco existir, pois o ponteiro irá existir,
        // se não houver referencia é porque o banco não existe
        return db != null;
    }

    private void createDataBase() throws Exception {

        // Primeiro temos que verificar se o banco da aplicação
        // já foi criado
        boolean exists = checkDataBase();
        if (exists) {
            // Log.v("banco de dados", "banco de dados existente");
            this.getWritableDatabase();
            this.close();

        }
        exists = checkDataBase();
        if (!exists) {
            // Chamaremos esse método para que o android
            // crie um banco vazio e o diretório onde iremos copiar
            // no banco que está no assets.

            // this.getReadableDatabase();

            // Se o banco de dados não existir iremos copiar o nosso
            // arquivo em /assets para o local onde o android os salva
            try {
                copyDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Esse método é responsável por copiar o banco do diretório assets para o
     * diretório padrão do android.
     */
    private void copyDatabase() throws IOException {

        String dbPath = DBPATH + DB_NAME;
        File FiledirChecker = new File(DBPATH);

        // Abre o arquivo o destino para copiar o banco de dados
        File f = new File(dbPath);

		/*
		 * try{ FiledirChecker.mkdirs(); }catch(Exception e){
		 * e.printStackTrace(); }
		 */

        f.createNewFile();

        OutputStream dbStream = new FileOutputStream(f);

        // Abre Stream do nosso arquivo que esta no assets
        InputStream dbInputStream = context.getAssets()
                .open("bestPlacesDB.sqlite");
        byte[] buffer = new byte[1024];
        int length;
        while ((length = dbInputStream.read(buffer)) > 0) {
            dbStream.write(buffer, 0, length);
        }

        dbInputStream.close();

        dbStream.flush();
        dbStream.close();

    }

    public SQLiteDatabase getDatabase() {

        try {
            // Verificando se o banco já foi criado e se não foi o
            // mesmo é criado.
            createDataBase();

            // Abrindo database
            String path = DBPATH + DB_NAME;
            if (db == null)
                db = SQLiteDatabase.openDatabase(path, null,
                        SQLiteDatabase.OPEN_READWRITE);
            return db;
        } catch (Exception e) {
            // Se não conseguir copiar o banco um novo será retornado
            return getWritableDatabase();
        }

    }

    public SQLiteDatabase getDb() {
        return db;
    }
}