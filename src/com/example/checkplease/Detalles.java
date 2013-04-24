package com.example.checkplease;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class Detalles extends Activity implements OnItemClickListener, OnClickListener{
	
	private List<String> precios = new ArrayList<String>();
	private Button regresa, okBtn;
	private TextView total, name;
	private EditText nameChange;
	private double sumaTotal;
	private ImageView foto;
	private static final int SELECT_PICTURE = 1;
	private String path = "";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalles);
		
		Bundle extras = getIntent().getExtras(); //si tiene parametos que envio la actividad Main
		if(extras !=null){//se agarra el parametro "position" y se le asigna la variable post
			String precios[] = extras.getStringArray("calculos");
		}
		
		SharedPreferences prefs = getSharedPreferences("PREFS_KEY",Activity.MODE_PRIVATE);
        path = prefs.getString("path","");
        name.setText(prefs.getString("name", "Nombre"));

		String precios[] = {"10","20","30"};
		
		total = (TextView)findViewById(R.id.total);
		name = (TextView)findViewById(R.id.name);
		foto = (ImageView)findViewById(R.id.foto);
		
		if( path.equals("") ){
			foto.setImageBitmap( BitmapFactory.decodeFile(path));
		}
		
		for( int i = 0; i < precios.length; i++ ){
			sumaTotal+= Double.parseDouble(precios[i]);
		}
		
		total.setText(sumaTotal+"");
		
		//se declara la lista asociada con la lista del layout
		ListView list = (ListView) findViewById(R.id.preciosList);
		//se crea el adapter para llenar los elemtnos de la lista con los datos de frutas
		LazyAdapter adapter = new LazyAdapter(this, precios);
		//se agrega los elementos a la lista
		list.setAdapter( adapter );
		//se habilita el evente OnCLick en cada elemto de la lista
		list.setOnItemClickListener(this);
		TextView titulo = (TextView)findViewById(R.id.titulo);
		titulo.setText("Detalles");
		Button btn = (Button) findViewById(R.id.agregaOrden);
		btn.setOnClickListener(new  View.OnClickListener(){
        	public void onClick(View view){
        		Intent intent = new Intent(view.getContext(), Lista.class);
                startActivity(intent);
        	}
        });
		regresa = (Button)findViewById(R.id.regresabtn);

		regresa.setOnClickListener(new  View.OnClickListener(){
        	public void onClick(View view){
        		Detalles.this.finish();
        	}
        });
		
		name.setOnClickListener(new  View.OnClickListener(){
        	public void onClick(View view){
        		showInfo();
        	}
        });
		foto .setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent,  "Selecciona Imagen"), SELECT_PICTURE);
			}
		});
		
	}
	
	public void showInfo() {
		//se crea una nueva alerta de dialogo
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		//se le asigna el titulo a la ventana de dialogo
		dialog.setTitle("Cambiar nombre");

		//se toma el Layout Inflater
		LayoutInflater inflater = getLayoutInflater();
		//se toma el layout correspondiente a la ventana del pop up
		View view = inflater.inflate(R.layout.cambiar_nombre, null);
		//se asigna esa vista a la ventana de dialogo
		dialog.setView(view);
		
		nameChange = (EditText)view.findViewById(R.id.nameChange);

		//para manejar la acci�n del boton OK, de la ventana de dialogo
		dialog.setPositiveButton("Ok",	new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				name.setText(nameChange.getText().toString());
			}
		});

		// Se crea la ventana de dialogo
		AlertDialog helpDialog = dialog.create();
		//se muestra la ventana de dialogo
		dialog.show();
	}
	
	protected void onActivityResult( int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if( resultCode == RESULT_OK && null != data){
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query( selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex( filePathColumn[0] );
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			foto.setImageBitmap( BitmapFactory.decodeFile(picturePath));
		}
	}
	
	@Override
    /**
     * Metodo que guarda los valores de las variables al voltear el android
     * @return void
     */
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		SharedPreferences prefs = getSharedPreferences("PREFS_KEY",Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("path",  path);
		editor.putString("name",  name.getText().toString());
		editor.commit();
	}
	
	@Override
	public void onClick(View v) {}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
