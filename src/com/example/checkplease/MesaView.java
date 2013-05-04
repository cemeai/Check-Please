package com.example.checkplease;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.checkplease.libreria.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MesaView extends Activity implements OnItemClickListener, OnClickListener{
	
	UserFunctions userFunctions = new UserFunctions();//carga la case userFunctions

	private List<Mesa> usrMesa = new ArrayList<Mesa>(); //lista de precios en la lista
	private MesaViewAdapter adapter;//adapter de la lista de productos
	private ListView l; //vista de la lista
	private Button cerrar; // boton  agregar y terminar de la vista  detalles
	private TextView total; 
	private int idMesa = 0;
	private String totalS = "";
	private String nombrePref = "";
	private String path = "";
	private int position = 0;
	private String idUsr = "";
	private int paid = 0;
	
	@Override
	/**
	 * Metodo que maneja los  datos y eventos  de la actividad Detalles
	 * @return void
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mesa);
		
		cerrar = (Button)findViewById(R.id.cerrarMesa);
		total = (TextView)findViewById(R.id.tvgTotal);
		
		//Recoleta  los parametros recibidos de la vista Lista
		Bundle extras = getIntent().getExtras(); //si tiene parametos que envio la actividad Main
		if(extras !=null){//se agarra el parametro "position" y se le asigna la variable post
			idMesa = extras.getInt("IdMesa");
			if(extras.getString("Viene").equals("detalles")){
				totalS = "" + extras.getFloat("Total");
				nombrePref = extras.getString("Nombre");
				path = extras.getString("Picture");
				position = extras.getInt("Position");
				idUsr = extras.getString("IdUsr");
				if( extras.getBoolean("Paid") ) paid = 1; else paid = 0;
			}
		}
		
		usrMesa.add(new Mesa(0, 1, "Raul", (float)120.00, 1, "null"));		
		usrMesa.add(new Mesa(0, 1, "Cesar", (float)80.00, 1, "null"));
		usrMesa.add(new Mesa(0, 1, "Mario", (float)110.50, 1, "null"));

		//HashMap<String, String> useractual = userFunctions.getUsuarioId(getApplicationContext());
		//JSONObject json = userFunctions.obtenerMesasUsuario((String)useractual.get("uid"));
		//JSONArray jArray;
		//try {
		//	jArray = json.getJSONArray("mesasUsuario");
		//	for(int i=0;i<jArray.length();i++){
		//		JSONObject json_data = jArray.getJSONObject(i);
		//		json_data.getInt("idMesa");
		//		json_data.getString("restaurante");
		//		json_data.getDouble("total");
		//	}
		//} catch (JSONException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		//se declara la lista asociada con la lista del layout
		l = (ListView) findViewById(R.id.lvUsuarios);
		//se crea el adapter para llenar los elemtnos de la lista con los datos de frutas
		adapter = new MesaViewAdapter(this, usrMesa);
		//se agrega los elementos a la lista
		l.setAdapter( adapter );
		//se habilita el evente OnCLick en cada elemto de la lista
		l.setOnItemClickListener(this);
		
		float t = 0;
		for( int i = 0; i < usrMesa.size(); i++ ){
			t+=usrMesa.get(i).getTotal();
		}
		total.setText(t+"");

		/**
		 * Metodo del evento OnClick que se asgina al boton terminar que regresa a Lista
		 * @return void
		 */
		cerrar.setOnClickListener(new  View.OnClickListener(){
			public void onClick(View view){
				Intent intent = new Intent(view.getContext(), MesaView.class);
				intent.putExtra("Total",totalS);
				intent.putExtra("Nombre",nombrePref);
				intent.putExtra("Picture",path);
				intent.putExtra("Position",position);
				intent.putExtra("IdUsr",idUsr);
				intent.putExtra("Paid",paid);
				startActivity(intent);
				finish();
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		Intent intent = new Intent(view.getContext(), Mesa.class);
		startActivity(intent);
	}

}
