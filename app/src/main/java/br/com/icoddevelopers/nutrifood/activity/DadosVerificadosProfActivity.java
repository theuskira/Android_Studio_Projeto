package br.com.icoddevelopers.nutrifood.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;

import br.com.icoddevelopers.nutrifood.R;

public class DadosVerificadosProfActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();

    private DecimalFormat df = new DecimalFormat("##.##");

    private float altura;
    private float peso;
    private int idade;
    private String sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_verificados_prof);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Resultado");     //Titulo para ser exibido na sua Action Bar em frente à seta

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        this.altura = Float.parseFloat(bundle.getString("altura"));
        this.peso = Float.parseFloat(bundle.getString("peso"));
        this.idade = Integer.parseInt(bundle.getString("idade"));
        this.sexo = bundle.getString("sexo");

        this.mViewHolder.imc = (TextView) findViewById(R.id.dadosVerificadosIMC);
        this.mViewHolder.classificacao = (TextView) findViewById(R.id.dadosVerificadosClassificacao);
        this.mViewHolder.faixa_etaria = (TextView) findViewById(R.id.dadosVerificadosFaixa);
        this.mViewHolder.idade = (TextView) findViewById(R.id.dadosVerificadosIdade);
        this.mViewHolder.necessidade_hidrica = (TextView) findViewById(R.id.dadosVerificadosHidrico);
        this.mViewHolder.peso_ideal = (TextView) findViewById(R.id.dadosVerificadosIdeal);
        this.mViewHolder.peso_ajustado = (TextView) findViewById(R.id.dadosVerificadosAjustado);
        this.mViewHolder.sexo = (TextView) findViewById(R.id.dadosVerificadosSexo);
        this.mViewHolder.alturaAtual = (TextView) findViewById(R.id.dadosVerificadosAltura);
        this.mViewHolder.pesoAtual = (TextView) findViewById(R.id.dadosVerificadosPesoAtual);

        this.mViewHolder.imc.setText(df.format(peso/(altura*altura)));
        this.mViewHolder.classificacao.setText(calculoIMC(peso/(altura*altura)));
        this.mViewHolder.faixa_etaria.setText(faixaEtaria(idade));
        this.mViewHolder.alturaAtual.setText("" + altura);
        this.mViewHolder.pesoAtual.setText("" + peso);
        this.mViewHolder.idade.setText("" + idade);
        this.mViewHolder.sexo.setText(sexo);
        this.mViewHolder.necessidade_hidrica.setText("" + df.format(calculoHidrico(peso, idade) * 0.001));
        this.mViewHolder.peso_ideal.setText("" + df.format(pesoIdeal(sexo, altura)));
        this.mViewHolder.peso_ajustado.setText("" + df.format((pesoIdeal(sexo, altura) - peso) * 0.25 + peso));
    }

    private static class ViewHolder{
        TextView imc;
        TextView classificacao;
        TextView faixa_etaria;
        TextView necessidade_hidrica;
        TextView idade;
        TextView peso_ideal;
        TextView peso_ajustado;
        TextView sexo;
        TextView alturaAtual;
        TextView pesoAtual;
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

    private String faixaEtaria(int idade){
        if(idade < 18){
            return "Jovem";
        }
        else if(idade >= 18 && idade < 55){
            return "Adulto";
        }
        else if(idade >= 55){
            return "Idoso";
        }
        else{
            return "Valor Inválido!";
        }
    }

    private float calculoHidrico(float peso, int idade){
        if(idade < 18){
            return peso * 40;
        }
        else if(idade >= 18 && idade < 55){
            return peso * 35;
        }
        else if(idade >= 55 && idade <= 75){
            return peso * 30;
        }
        else {
            return peso * 25;
        }
    }

    private double pesoIdeal(String sexo, float altura){
        if(sexo.equals("Masculino")){
            return 22 * (altura * altura);
        }
        else if(sexo.equals("Feminino")){
            return 20.8 * (altura * altura);
        }
        else{
            return 21.7 * (altura * altura);
        }
    }
}
