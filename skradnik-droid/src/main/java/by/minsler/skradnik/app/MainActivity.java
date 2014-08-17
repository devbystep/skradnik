package by.minsler.skradnik.app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import by.minsler.skradnik.dao.DAOException;
import by.minsler.skradnik.dao.TranslationDAO;
import by.minsler.skradnik.ds.SkarnikOpenShiftDAO;
import by.minsler.skradnik.ds.SqliteDAO;
import by.minsler.skradnik.entity.Translation;

import java.util.Arrays;

public class MainActivity extends ActionBarActivity {


    public static final String PLEASE_ENTER_WORD = "введите слово для поиска";
    private EditText wordEdit;
    private TextView wordView;
    private Button wordButton;

    private TranslationDAO translationDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo set translation dao by setting menu
        this.translationDao = new SqliteDAO(this);
        setContentView(by.minsler.skradnik.app.R.layout.activity_main);
        wordEdit = (EditText) findViewById(by.minsler.skradnik.app.R.id.word_translate);
        wordView = (TextView) findViewById(by.minsler.skradnik.app.R.id.view_translate);
        wordButton = (Button) findViewById(by.minsler.skradnik.app.R.id.button_translate);
        wordView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(by.minsler.skradnik.app.R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void translate_click(View v) {
        String wordFromEdit = wordEdit.getText().toString();
        if (wordFromEdit == null || wordFromEdit.trim().length() == 0) {
            wordView.setText(PLEASE_ENTER_WORD);
            return;
        }
        new TranslateTask().execute(wordFromEdit);
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(wordEdit.getWindowToken(), 0);
    }


    private class TranslateTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String wordFromEdit = Arrays.asList(strings).get(0);
            try {
                Translation translation = translationDao.getTranslation(wordFromEdit);
                if (translation != null) {
                    publishProgress(translation.getTranslation());
                }
            } catch (DAOException e) {
                wordView.setText("Error: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            String translateForWord = Arrays.asList(values).get(0);
            wordView.setText(Html.fromHtml(translateForWord), TextView.BufferType.SPANNABLE);
            wordEdit.setText("");
            super.onProgressUpdate(values);
        }

    }
}
