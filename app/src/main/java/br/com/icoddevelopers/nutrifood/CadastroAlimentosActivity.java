package br.com.icoddevelopers.nutrifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroAlimentosActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Frutas");

    private EditText nome;
    private EditText calorias;
    private EditText gorduras_t;
    private EditText colesterol;
    private EditText sodio;
    private EditText potassio;
    private EditText carboidratos;
    private EditText proteinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_alimentos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Adicionar Fruta");     //Titulo para ser exibido na sua Action Bar em frente à seta

        this.nome = findViewById(R.id.txtCadAlimentos_Nome);
        this.calorias = findViewById(R.id.txtCadAlimentos_Calorias);
        this.gorduras_t = findViewById(R.id.txtCadAlimentos_GordT);
        this.colesterol = findViewById(R.id.txtCadAlimentos_Colesterol);
        this.sodio = findViewById(R.id.txtCadAlimentos_Sodio);
        this.potassio = findViewById(R.id.txtCadAlimentos_Potassio);
        this.carboidratos = findViewById(R.id.txtCadAlimentos_Carboidratos);
        this.proteinas = findViewById(R.id.txtCadAlimentos_Proteinas);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.salvar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnMenuSalvar:
                try{
                    boolean erro = false;

                    if(this.nome.getText().toString().equals("")){
                        this.nome.setError("Digite o Nome!");
                        erro = true;
                    }/*
                    else if(Float.parseFloat(mViewHolder.altura.getText().toString()) >= 50){
                        mViewHolder.altura.setText("" + Float.parseFloat(mViewHolder.altura.getText().toString())  * 0.01);
                    }
                    else if(Float.parseFloat(mViewHolder.altura.getText().toString()) > 4 || Float.parseFloat(mViewHolder.altura.getText().toString()) < 0){
                        mViewHolder.altura.setError("Altura Inválida!");
                        erro = true;
                    }*/
                    if(!erro){
                        try{

                            enviarDados();

                            Toast.makeText(getApplicationContext(), this.nome.getText().toString() + " adicionado!", Toast.LENGTH_LONG).show();

                            limparCampos();

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Erro: " + e, Toast.LENGTH_LONG).show();
                        }
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

    private void enviarDados(){

        try{

            this.databaseReference.child(this.nome.getText().toString()).setValue(this.nome.getText().toString());
            this.databaseReference.child(this.nome.getText().toString()).child("Nome").setValue(this.nome.getText().toString());

            if(!this.calorias.getText().toString().equals("")){
                this.databaseReference.child(this.nome.getText().toString()).child("Calorias").setValue(Float.parseFloat(this.calorias.getText().toString()));
            }
            if(!this.gorduras_t.getText().toString().equals("")){
                this.databaseReference.child(this.nome.getText().toString()).child("Gorduras Totais").setValue(Float.parseFloat(this.gorduras_t.getText().toString()));
            }
            if(!this.colesterol.getText().toString().equals("")){
                this.databaseReference.child(this.nome.getText().toString()).child("Colesterol").setValue(Float.parseFloat(this.colesterol.getText().toString()));
            }
            if(!this.sodio.getText().toString().equals("")){
                this.databaseReference.child(this.nome.getText().toString()).child("Sódio").setValue(Float.parseFloat(this.sodio.getText().toString()));
            }
            if(!this.potassio.getText().toString().equals("")){
                this.databaseReference.child(this.nome.getText().toString()).child("Potássio").setValue(Float.parseFloat(this.potassio.getText().toString()));
            }
            if(!this.carboidratos.getText().toString().equals("")){
                this.databaseReference.child(this.nome.getText().toString()).child("Carboidratos").setValue(Float.parseFloat(this.carboidratos.getText().toString()));
            }
            if(!this.proteinas.getText().toString().equals("")){
                this.databaseReference.child(this.nome.getText().toString()).child("Proteínas").setValue(Float.parseFloat(this.proteinas.getText().toString()));
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Erro: " + e, Toast.LENGTH_LONG).show();
        }

    }

    private void limparCampos(){
        this.nome.setText("");
        this.calorias.setText("");
        this.gorduras_t.setText("");
        this.colesterol.setText("");
        this.sodio.setText("");
        this.potassio.setText("");
        this.carboidratos.setText("");
        this.proteinas.setText("");
    }
}
