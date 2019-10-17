package com.requisicoes.cursoandroid.requisicoeshttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button botaoRecuperar;
    private TextView textoResultado;
    private TextView txtCep;
    private TextView txtLogradouro;
    private TextView txtCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoRecuperar = findViewById(R.id.buttonRecuperar);
        textoResultado = findViewById(R.id.textResultado);
        txtCep = findViewById(R.id.txtCep);
        txtLogradouro = findViewById(R.id.txtLogradouro);
        txtCidade = findViewById(R.id.txtCidade);

        botaoRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask task = new MyTask();
                String urlApi = "https://blockchain.info/ticker";
                String cep = "01310100";
                String urlCep = "https://viacep.com.br/ws/" + cep + "/json/";
                task.execute(urlCep);
            }
        });

    }

    class MyTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {

                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                // Recupera os dados em Bytes
                inputStream = conexao.getInputStream();

                //inputStreamReader lê os dados em Bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader( inputStream );

                //Objeto utilizado para leitura dos caracteres do InpuStreamReader
                BufferedReader reader = new BufferedReader( inputStreamReader );
                buffer = new StringBuffer();
                String linha = "";

                while((linha = reader.readLine()) != null){
                    buffer.append( linha );
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

            String logradouro = null;
            String cep = null;
            String complemento = null;
            String bairro = null;
            String localidade = null;
            String uf = null;

           /* String objetoValor = null;
            String valorMoeda = null;
            String simbolo = null;

            String objetoValorUS = null;
            String valorMoedaUS = null;
            String simboloUS = null;*/

            try {

                JSONObject jsonObject = new JSONObject(resultado);
                logradouro = jsonObject.getString("logradouro");
                cep = jsonObject.getString("cep");
                complemento = jsonObject.getString("complemento");
                bairro = jsonObject.getString("bairro");
                localidade = jsonObject.getString("localidade");
                uf = jsonObject.getString("uf");

                //valor para Real BRL
               // JSONObject jsonObject = new JSONObject(resultado);
              //  objetoValor = jsonObject.getString("BRL");

                //Valor para Dolar USD
               // JSONObject jsonObjectUS = new JSONObject(resultado);
               // objetoValorUS = jsonObjectUS.getString("USD");


                //Valor para Real
                //JSONObject jsonObjectReal = new JSONObject(objetoValor);
                //valorMoeda = jsonObjectReal.getString("last");
               // simbolo = jsonObjectReal.getString("symbol");

                //Valor para Dolar
                //JSONObject jsonObjectDolar = new JSONObject(objetoValorUS);
                //valorMoedaUS = jsonObjectDolar.getString("last");
               // simboloUS = jsonObjectDolar.getString("symbol");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //textoResultado.setText(resultado);
            textoResultado.setText("Resultado Completo: " + logradouro+" / "+cep+" / "+complemento+" / "+bairro+" / "+localidade+" / "+uf);
            txtCep.setText("CEP: " + cep);
            txtLogradouro.setText("Rua: "  +logradouro);
            txtCidade.setText("Cidade: " + localidade);
            //textoResultado.setText("Valor para Real: "+ simbolo+"  "+ valorMoeda + " E O valor Dolar" + simboloUS+" "+valorMoedaUS);
        }
    }

}
