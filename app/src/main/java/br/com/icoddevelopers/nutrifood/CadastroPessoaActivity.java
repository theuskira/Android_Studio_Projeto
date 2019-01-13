package br.com.icoddevelopers.nutrifood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroPessoaActivity extends AppCompatActivity {

    private FirebaseAuth usuario = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");

    private EditText cadastroNome;
    private EditText cadastroSobrenome;
    private EditText cadastroEmail;
    private EditText cadastroNumero;
    private EditText cadastroSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Entrar");     //Titulo para ser exibido na sua Action Bar em frente à seta

        this.cadastroNome = findViewById(R.id.cadastroNome);
        this.cadastroSobrenome = findViewById(R.id.cadastroSobrenome);
        this.cadastroEmail = findViewById(R.id.cadastroEmail);
        this.cadastroNumero = findViewById(R.id.cadastroTelefone);
        this.cadastroSenha = findViewById(R.id.cadastroSenha);

        /*
        try{
            databaseReference.child("Usuarios").setValue("Usuarioas");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Erro: " + e.toString(), Toast.LENGTH_LONG).show();
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.salvar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar

        int id = item.getItemId();

        if(id == android.R.id.home){
            startActivity(new Intent(this, LoginActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
            finish();  //Método para matar a activity e não deixa-lá indexada na pilhagem
            return true;
        }else if(id == R.id.btnMenuSalvar){
            if(!erroCampos()){
                /* Cadastro de Usuario */
                try{
                    usuario.createUserWithEmailAndPassword(this.cadastroEmail.getText().toString(), this.cadastroSenha.getText().toString())
                            .addOnCompleteListener(CadastroPessoaActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        cadastrarDados();
                                        Toast.makeText(getApplicationContext(), cadastroNome.getText().toString() + " cadastrado!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(CadastroPessoaActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(CadastroPessoaActivity.this, "Erro ao cadastrar " + cadastroNome.getText().toString() + "!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }catch (Exception e){
                    Toast.makeText(CadastroPessoaActivity.this, "Erro: " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean erroCampos(){
        boolean erro = false;

        if(this.cadastroNome.getText().toString().equals("")){
            this.cadastroNome.setError("Digite o Nome!");
            erro = true;
        }
        if(this.cadastroEmail.getText().toString().equals("")){
            this.cadastroEmail.setError("Digite o Email!");
            erro = true;
        }
        if(this.cadastroSenha.getText().toString().equals("")){
            this.cadastroSenha.setError("Digite a Senha!");
            erro = true;
        }
        return erro;
    }

    private void cadastrarDados(){
        try{

            this.databaseReference.child(this.cadastroEmail.getText().toString()).setValue(this.cadastroEmail.getText().toString());
            this.databaseReference.child(this.cadastroEmail.getText().toString()).child("Nome").setValue(this.cadastroNome.getText().toString())
                    .addOnCompleteListener(CadastroPessoaActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                if(!cadastroSobrenome.getText().toString().equals("")){
                                    databaseReference.child(cadastroEmail.getText().toString()).child("Sobrenome").setValue(cadastroSobrenome.getText().toString());
                                }
                                if(!cadastroNumero.getText().toString().equals("")){
                                    databaseReference.child(cadastroEmail.getText().toString()).child("Telefone").setValue(Long.parseLong(cadastroNumero.getText().toString()));
                                }
                            }else {
                                Toast.makeText(CadastroPessoaActivity.this, "Erro ao cadastrar " + cadastroNome.getText().toString() + "!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });



        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Erro: " + e, Toast.LENGTH_LONG).show();
        }
    }
}
