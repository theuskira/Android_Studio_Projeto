package br.com.icoddevelopers.nutrifood.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.icoddevelopers.nutrifood.R;
import br.com.icoddevelopers.nutrifood.config.ConfiguracaoFirebase;
import br.com.icoddevelopers.nutrifood.helper.UsuarioFirebase;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContaActivity extends AppCompatActivity {

    private TextView email;

    private FirebaseAuth autenticacao;

    private CircleImageView circleImageViewPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Conta");     //Titulo para ser exibido na sua Action Bar em frente à seta

        this.email = findViewById(R.id.txtEmailPerfil);

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();

        this.circleImageViewPerfil = findViewById(R.id.circleImageViewFotoPerfil);

        FirebaseUser usuario = UsuarioFirebase.getUsuarioAtual();
        Uri url = usuario.getPhotoUrl();

        if(url != null){
            Glide.with(ContaActivity.this)
                    .load(url)
                    .into(circleImageViewPerfil);
        }else {
            circleImageViewPerfil.setImageResource(R.drawable.apple);
        }

        verificarDados();
    }

    private void verificarDados(){
        try{
            //Toast.makeText(MainActivity.this, "" + autenticacao.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();

            FirebaseUser usuarioAtual = null;
            if(autenticacao.getCurrentUser() != null){
                usuarioAtual = autenticacao.getCurrentUser();
            }

            if(usuarioAtual != null){
                email.setText(usuarioAtual.getEmail());
            }else{
                startActivity(new Intent(this, LoginActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finish();
            }
        }catch (Exception e){
            Toast.makeText(ContaActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_conta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar

        int id = item.getItemId();

        if(id == R.id.btnMenuConta_Editar){
            abrirConfiguracoes();
        }
        else if(id == R.id.btnMenuConta_Deslogar){
            deslogarUsuario();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario(){
        try{
            autenticacao.signOut();
            finish();
        }catch (Exception e){
            Toast.makeText(this,"Err: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void abrirConfiguracoes(){
        startActivity(new Intent(this, EditarContaActivity.class));
    }
}
