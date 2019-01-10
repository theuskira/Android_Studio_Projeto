package br.com.icoddevelopers.nutrifood;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

public class CalculoProfiActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo_profi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Cálculo Avançado");     //Titulo para ser exibido na sua Action Bar em frente à seta

        this.mViewHolder.altura = (EditText) findViewById(R.id.editTextProfAltura);
        this.mViewHolder.peso = (EditText) findViewById(R.id.editTextProfPeso);
        this.mViewHolder.idade = (EditText) findViewById(R.id.editTextProfIdade);
        this.mViewHolder.radioButtonHomem = findViewById(R.id.radioButtonProfMasculino);
        this.mViewHolder.radioButtonMulher = findViewById(R.id.radioButtonProfFeminino);
        this.mViewHolder.linearLayoutProf = (LinearLayout) findViewById(R.id.linearLayoutProf);
        this.mViewHolder.linearLayoutProfAltura = (LinearLayout) findViewById(R.id.linearLayoutProfAltura);
        this.mViewHolder.linearLayoutProfPeso = (LinearLayout) findViewById(R.id.linearLayoutProfPeso);
        this.mViewHolder.linearLayoutProfIdade = (LinearLayout) findViewById(R.id.linearLayoutProfIdade);


        //Verifica se a posição da tela é landscape (deitado) se não,
        // só pode estar em portrait (em pé)!
        Configuration configuration = getResources().getConfiguration();

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            this.mViewHolder.linearLayoutProf.setGravity(Gravity.CENTER_HORIZONTAL);
            this.mViewHolder.linearLayoutProfAltura.setGravity(Gravity.CENTER_HORIZONTAL);
            this.mViewHolder.linearLayoutProfPeso.setGravity(Gravity.CENTER_HORIZONTAL);
            this.mViewHolder.linearLayoutProfIdade.setGravity(Gravity.CENTER_HORIZONTAL);
        }else{
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuverificar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnMenuVerificar:
                try{
                    boolean erro = false;

                    if(mViewHolder.altura.getText().toString().equals("")){
                        mViewHolder.altura.setError("Digite a Altura!");
                        erro = true;
                    }
                    else if(Float.parseFloat(mViewHolder.altura.getText().toString()) >= 50){
                        mViewHolder.altura.setText("" + Float.parseFloat(mViewHolder.altura.getText().toString())  * 0.01);
                    }
                    else if(Float.parseFloat(mViewHolder.altura.getText().toString()) > 4 || Float.parseFloat(mViewHolder.altura.getText().toString()) < 0){
                        mViewHolder.altura.setError("Altura Inválida!");
                        erro = true;
                    }
                    if(mViewHolder.peso.getText().toString().equals("")){
                        mViewHolder.peso.setError("Digite o Peso!");
                        erro = true;
                    }
                    else if(Float.parseFloat(mViewHolder.peso.getText().toString()) > 600 || Float.parseFloat(mViewHolder.peso.getText().toString()) < 0){
                        mViewHolder.peso.setError("Peso Inválido!");
                        erro = true;
                    }
                    if(mViewHolder.idade.getText().toString().equals("")){
                        mViewHolder.idade.setError("Digite a Idade!");
                        erro = true;
                    }
                    else if(Float.parseFloat(mViewHolder.idade.getText().toString()) > 150 || Float.parseFloat(mViewHolder.idade.getText().toString()) < 0){
                        mViewHolder.idade.setError("Idade Inválida!");
                        erro = true;
                    }
                    if(!erro){
                        Intent intent = new Intent(CalculoProfiActivity.this, DadosVerificadosProfActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("idade", mViewHolder.idade.getText().toString());
                        bundle.putString("altura", mViewHolder.altura.getText().toString());
                        bundle.putString("peso", mViewHolder.peso.getText().toString());
                        bundle.putString("sexo", verificarValores(mViewHolder.radioButtonHomem, mViewHolder.radioButtonMulher));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    break;
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Erro: " + e, Toast.LENGTH_LONG).show();
                }
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finish();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class ViewHolder{
        EditText altura;
        EditText peso;
        EditText idade;
        RadioButton radioButtonHomem;
        RadioButton radioButtonMulher;
        LinearLayout linearLayoutProf;
        LinearLayout linearLayoutProfAltura;
        LinearLayout linearLayoutProfPeso;
        LinearLayout linearLayoutProfIdade;
    }

    private String verificarValores(RadioButton homem, RadioButton mulher){
        if(homem.isChecked()){
            return "Masculino";
        }else if(mulher.isChecked()){
            return "Feminino";
        }else {
            return "Indefinido";
        }
    }
}
