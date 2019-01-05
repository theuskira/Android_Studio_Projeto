package br.com.icoddevelopers.nutrifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;

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
                if(mViewHolder.altura.getText().toString().equals("")){
                    mViewHolder.altura.setError("Digite a Altura!");
                }
                if(mViewHolder.peso.getText().toString().equals("")){
                    mViewHolder.peso.setError("Digite o Peso!");
                }
                if(mViewHolder.idade.getText().toString().equals("")){
                    mViewHolder.idade.setError("Digite a Idade!");
                }
                else{
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
    }

    private String verificarValores(RadioButton homem, RadioButton mulher){
        if(homem.isChecked()){
            return "Homem";
        }else if(mulher.isChecked()){
            return "Mulher";
        }else {
            return "Indefinido";
        }
    }
}
