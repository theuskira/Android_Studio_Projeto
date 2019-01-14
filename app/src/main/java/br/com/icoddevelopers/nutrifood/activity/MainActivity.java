package br.com.icoddevelopers.nutrifood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.icoddevelopers.nutrifood.R;
import br.com.icoddevelopers.nutrifood.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth autenticacao;
    private Menu menu;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("NutriFood");     //Titulo para ser exibido na sua Action Bar em frente à seta

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.email = findViewById(R.id.txtMainEmail);

        menu  = navigationView.getMenu();

        verificarusuario();
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarusuario();
    }

    @Override
    protected void onResume() {
        super.onResume();
        verificarusuario();
    }

    public void entrarPerfil(View view){
        try{
            autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
            //Toast.makeText(MainActivity.this, "" + autenticacao.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();

            FirebaseUser usuarioAtual = null;
            if(autenticacao.getCurrentUser() != null){
                usuarioAtual = autenticacao.getCurrentUser();
            }

            if(usuarioAtual != null){
                Intent intent = new Intent(MainActivity.this, ContaActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(MainActivity.this, "Usuario não conectado!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void conta(){
        Intent intent = new Intent(MainActivity.this, ContaActivity.class);
        startActivity(intent);
    }

    private void verificarusuario(){

        try{
            autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
            //Toast.makeText(MainActivity.this, "" + autenticacao.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();

            FirebaseUser usuarioAtual = null;
            if(autenticacao.getCurrentUser() != null){
                usuarioAtual = autenticacao.getCurrentUser();
            }

            if(usuarioAtual != null){
                menu.getItem(6).setVisible(true);
                menu.getItem(7).setVisible(false);
                menu.getItem(8).setVisible(true);
                //email.setText("" + usuarioAtual.getEmail());
            }else{
                menu.getItem(6).setVisible(false);
                menu.getItem(7).setVisible(true);
                menu.getItem(8).setVisible(false);
            }
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(MainActivity.this, CalculoIMCSimplesActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_profi) {
            Intent intent = new Intent(MainActivity.this, CalculoProfiActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_orientacao) {
            Intent intent = new Intent(MainActivity.this, OrientacaoActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Orientação Nutricional", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_alarme) {
            Toast.makeText(MainActivity.this, "Alarmes", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_alimentos) {
            Toast.makeText(MainActivity.this, "Alimentos", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, AlimentosActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_manage) {
            Toast.makeText(MainActivity.this, "Editar", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_Add_fruta) {
            Intent intent = new Intent(MainActivity.this, CadastroAlimentosActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_entrar) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_perfil) {
            conta();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
