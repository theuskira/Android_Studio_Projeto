package br.com.icoddevelopers.nutrifood;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class CalculoIMCSimplesActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    private float imc;

    private DecimalFormat df = new DecimalFormat("##.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo_imcsimples);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Cálculo IMC");     //Titulo para ser exibido na sua Action Bar em frente à seta

        this.mViewHolder.altura = (EditText) findViewById(R.id.editTextAltura);
        this.mViewHolder.peso = (EditText) findViewById(R.id.editTextPeso);
        this.mViewHolder.linearLayoutSimples = (LinearLayout) findViewById(R.id.linearLayoutSimples);
        this.mViewHolder.linearLayoutSimplesAltura = (LinearLayout) findViewById(R.id.linearLayoutSimplesAltura);
        this.mViewHolder.linearLayoutSimplesPeso = (LinearLayout) findViewById(R.id.linearLayoutSimplesPeso);
        this.mViewHolder.calcular = (Button) findViewById(R.id.btnCalcular);
        this.mViewHolder.infoIMC = (TextView) findViewById(R.id.textoInformacaoIMC);
        this.mViewHolder.infoPeso = (TextView) findViewById(R.id.textoInformacaoPesoIdeal);
        this.mViewHolder.imagem = (ImageView) findViewById(R.id.imagemIMC);
        this.mViewHolder.imagem.setVisibility(View.INVISIBLE);
        this.mViewHolder.radioButtonHomem = findViewById(R.id.radioButtonMasculino);
        this.mViewHolder.radioButtonMulher = findViewById(R.id.radioButtonFeminino);

        this.mViewHolder.calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewHolder.altura.getText().toString().equals("")){
                    mViewHolder.altura.setError("Digite a Altura!");
                    mViewHolder.imagem.setVisibility(View.VISIBLE);
                    mViewHolder.imagem.setImageResource(R.drawable.invalido);
                    mViewHolder.infoIMC.setText("Valor Inválido!");
                    mViewHolder.infoPeso.setText("");
                }
                if(mViewHolder.peso.getText().toString().equals("")){
                    mViewHolder.peso.setError("Digite o Peso!");
                    mViewHolder.imagem.setVisibility(View.VISIBLE);
                    mViewHolder.imagem.setImageResource(R.drawable.invalido);
                    mViewHolder.infoIMC.setText("Valor Inválido!");
                    mViewHolder.infoPeso.setText("");
                }
                else{
                    imc = (Float.parseFloat(mViewHolder.peso.getText().toString()) / (Float.parseFloat(mViewHolder.altura.getText().toString()) * Float.parseFloat(mViewHolder.altura.getText().toString())));
                    mViewHolder.infoIMC.setText("IMC = " + df.format(imc) + " " + calculoIMC(imc));
                    AlterarImagem(imc);
                    mViewHolder.infoPeso.setText(calculoPesoIdeal(Float.parseFloat(mViewHolder.altura.getText().toString()), mViewHolder.radioButtonHomem, mViewHolder.radioButtonMulher));
                }
            }
        });

        //Verifica se a posição da tela é landscape (deitado) se não,
        // //só pode estar em portrait (em pé)!
        Configuration configuration = getResources().getConfiguration();

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            this.mViewHolder.linearLayoutSimples.setGravity(Gravity.CENTER_HORIZONTAL);
            this.mViewHolder.linearLayoutSimplesAltura.setGravity(Gravity.CENTER_HORIZONTAL);
            this.mViewHolder.linearLayoutSimplesPeso.setGravity(Gravity.CENTER_HORIZONTAL);
        }else{
            this.mViewHolder.linearLayoutSimples.setGravity(Gravity.NO_GRAVITY);
            this.mViewHolder.linearLayoutSimplesAltura.setGravity(Gravity.NO_GRAVITY);
            this.mViewHolder.linearLayoutSimplesPeso.setGravity(Gravity.NO_GRAVITY);
        }
    }

    private static class ViewHolder{
        EditText altura;
        EditText peso;
        LinearLayout linearLayoutSimples;
        LinearLayout linearLayoutSimplesAltura;
        LinearLayout linearLayoutSimplesPeso;
        Button calcular;
        TextView infoIMC;
        TextView infoPeso;
        ImageView imagem;
        RadioButton radioButtonHomem;
        RadioButton radioButtonMulher;
    }

    private String calculoIMC(float imc){
        if(imc < 16){
            return "Desnutrição Severa";
        }
        else if(imc >= 16 && imc <= 16.99){
            return "Desnutrição Moderada";
        }
        else if(imc >= 17 && imc <= 18.49){
            return "Desnutrição Leve";
        }
        else if(imc >= 18.5 && imc <= 24.99){
            return "Peso Normal!";
        }
        else if(imc >= 25 && imc <= 26.99){
            return "Sobrepeso Grau I";
        }
        else if(imc >= 27 && imc <= 29.99){
            return "Sobrepeso Grau II (Pré-obeso)";
        }
        else if(imc >= 30 && imc <= 34.99){
            return "Obesidade I";
        }
        else if(imc >= 35 && imc <= 39.99){
            return "Obesidade II";
        }
        else if(imc >= 40 && imc <= 49.99){
            return "Obesidade III (Mórbida)";
        }
        else if(imc >= 50){
            return "Obesidade IV (Extrema)";
        }
        else{
            return "Valor Inválido!";
        }
    }

    private String calculoPesoIdeal(Float altura, RadioButton homem, RadioButton mulher){

        if(homem.isChecked()){
            return "Seu peso ideal é " + df.format(22.0 * (altura * altura)) + " Kg";
        }else if(mulher.isChecked()){
            return "Seu peso ideal é " + df.format(20.8 * (altura * altura)) + " Kg";
        }else {
            return "";
        }
    }

    private void AlterarImagem(float imc){
        if(imc != 0){
            mViewHolder.imagem.setVisibility(View.VISIBLE);
        }
        if(imc < 17){
            // Muito abaixo do peso
            mViewHolder.imagem.setImageResource(R.drawable.sad);
        }
        else if(imc >= 17 && imc <= 18.49){
            // Abaixo do Peso
            mViewHolder.imagem.setImageResource(R.drawable.neutro);
        }
        else if(imc >= 18.5 && imc <= 24.99){
            // Peso Normal
            mViewHolder.imagem.setImageResource(R.drawable.lol);
        }
        else if(imc >= 25 && imc <= 29.99){
            // Acima do Peso
            mViewHolder.imagem.setImageResource(R.drawable.neutro);
        }
        else if(imc >= 30 && imc <= 34.99){
            // Obesidade I
            mViewHolder.imagem.setImageResource(R.drawable.sad);
        }
        else if(imc >= 35 && imc <= 39.99){
            // Obesidade II (Severa)
            mViewHolder.imagem.setImageResource(R.drawable.sad);
        }
        else if(imc >= 40){
            // Obesidade III (Mórbida)
            mViewHolder.imagem.setImageResource(R.drawable.sad);
        }
        else{
            // Valor Inválido!
            mViewHolder.imagem.setImageResource(R.drawable.invalido);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finish();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:
                break;
        }
        return true;
    }
}
