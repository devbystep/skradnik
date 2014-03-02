package com.example.app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MainActivity extends ActionBarActivity {

    private EditText wordEdit;
    private TextView wordView;
    private Button wordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordEdit = (EditText) findViewById(R.id.word_translate);
        wordView = (TextView) findViewById(R.id.view_translate);
        wordButton = (Button) findViewById(R.id.button_translate);
        wordView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            wordView.setText("введите слово для поиска");
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
            String translateForWord = translate(wordFromEdit);
            publishProgress(translateForWord);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            String translateForWord = Arrays.asList(values).get(0);
            wordView.setText(Html.fromHtml(translateForWord), TextView.BufferType.SPANNABLE);
            wordEdit.setText("");
            super.onProgressUpdate(values);
        }

        public String translate(String word) {
            String url = "http://www.skarnik.by/search?term=" + word + "&lang=rus";
            String result = getTranslation(url);
            return result;
        }


        public String getTranslation(String url) {

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", "android");

            try {
                HttpResponse response = client.execute(request);

                // Check if server response is valid
                StatusLine status = response.getStatusLine();
                if (status.getStatusCode() != 200) {
                    throw new IOException("Invalid response from server: " + status.toString());
                }

                // Pull content stream from response
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();


                ByteArrayOutputStream content = new ByteArrayOutputStream();

                // Read response into a buffered stream
                int readBytes = 0;
                byte[] sBuffer = new byte[512];
                while ((readBytes = inputStream.read(sBuffer)) != -1) {
                    content.write(sBuffer, 0, readBytes);
                }

                String dataAsString = new String(content.toByteArray());

                Document doc = Jsoup.parse(dataAsString);
                Element masthead = doc.select("div.span10").get(1);
                return masthead.toString();
            } catch (IOException e) {
                Log.d("error", e.getLocalizedMessage());
                return "internal error. sorry.";
            }
        }
    }
}
