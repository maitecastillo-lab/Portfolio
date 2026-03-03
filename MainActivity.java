package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    String suma = "+";
    String resta = "-";
    String multiplicacion = "*";
    String division = "/";
    String porcentaje = "%";

    String operacionActual = "";

    double num1 = 0;
    double num2 = 0;


    TextView tvTemp, tvResult;

    double decimal = 0;
    double resultado =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvTemp = findViewById(R.id.tvTemp);
        tvTemp.setText("");
        tvResult = findViewById(R.id.tvResult);
    }


    //aqui va a preguntar en cual de los botones que esta
    // las operaciones va a seleccionar, haciendo una funcion
    /* public void cambioOperador(View b) {
        Calcular();
        Button boton = (Button) b;
        String textoBoton = boton.getText().toString().trim();

        if(textoBoton.equals("÷") || textoBoton.equals("X")) {
            if(textoBoton.equals("X")){
                operacionActual ="*";
            }else{
                operacionActual = "/";
            }
        }else {
            operacionActual = textoBoton;
        }
        tvResult.setText(String.valueOf(num1) + operacionActual);
        tvTemp.setText("");

    } */
    public void cambioOperador(View b) {
        String textoTemp = tvTemp.getText().toString().trim();

        // Si hay una operación pendiente y el usuario ha escrito otro número
        if (!operacionActual.isEmpty() && !textoTemp.isEmpty()) {
            Calcular(); // ejecuta la operación anterior
        } else if (!textoTemp.isEmpty()) {
            num1 = Double.parseDouble(textoTemp); // guarda el primer número
        }

        Button boton = (Button) b;
        String textoBoton = boton.getText().toString().trim();

        // Traduce el símbolo visual a operador real
        if (textoBoton.equals("÷") || textoBoton.equals("X")) {
            operacionActual = textoBoton.equals("X") ? "*" : "/";
        } else {
            operacionActual = textoBoton;
        }

        // Muestra el número y el operador en tvResult
        DecimalFormat formatoDecimal = new DecimalFormat("#.##");
        String textoNum1 = (num1 % 1 == 0) ? String.valueOf((int) num1) : formatoDecimal.format(num1);
        tvResult.setText(textoNum1 + operacionActual);

        // Limpia el campo temporal para que el usuario escriba el siguiente número
        tvTemp.setText("");
    }

    public void Calcular(){

        if(!Double.isNaN(num1)){
            num2 = Double.parseDouble(tvTemp.getText().toString());
            tvTemp.setText("");

            switch(operacionActual){
                case "+":
                    resultado = num1 + num2;
                    break;
                case "-":
                    resultado = num1 - num2;
                    break;
                case "*":
                    resultado = num1 * num2;
                    break;
                case "/":
                    if(num2 !=0){
                        resultado = num1 / num2;
                    }else{
                        tvResult.setText("Error división inválida");
                        return;
                    }
                    break;
                case "%":
                    resultado = num1 % num2;
                    break;
                default:
                    tvResult.setText("Operación inválida");
            }
            DecimalFormat formatoDecimal = new DecimalFormat("#.##");
            String textoResultado = (resultado % 1 == 0) ? String.valueOf((int) resultado) : formatoDecimal.format(resultado);
            tvResult.setText(textoResultado);

        }else{
            num1 = Double.parseDouble(tvTemp.getText().toString());
        }
        num1 = resultado;
        operacionActual = "";
    }

    public void seleccionarNumero (View b){
        Button boton = (Button) b;
        //Aca estamos diciendo que lo que hay en el campo temporal (tvtemp)
        //le va a concatenar el texto que tenga el boton.

        String actual = tvTemp.getText().toString();
        String nuevo = boton.getText().toString();

        if (actual.equals("0")) {
            tvTemp.setText(nuevo); // reemplaza el 0 por el nuevo número
        } else {
            tvTemp.setText(actual + nuevo); // concatena normalmente
        }

    }

    public void borrar(View b) {

        Button boton = (Button) b;
        String textoBoton = boton.getText().toString().trim();


        if (textoBoton.equals("C")) {
            String actual = tvTemp.getText().toString();
            if (!actual.isEmpty()) {
                // Elimina el último carácter del campo temporal (tvTemp)
                tvTemp.setText(actual.substring(0, actual.length() - 1));
            }
        } else if (textoBoton.equals("CA")) {
            // Reinicia la calculadora: borra campos y resetea variables
            tvTemp.setText("");
            tvResult.setText("");
            num1 = 0;
            num2 = 0;
            resultado = 0;
            operacionActual = "";
        }
    }

    public void igual (View b){
        Calcular();
        DecimalFormat formatoDecimal = new DecimalFormat("#.##");
        String textoResultado = (resultado % 1 == 0) ? String.valueOf((int) resultado) : formatoDecimal.format(resultado);
        tvResult.setText(textoResultado);

    }

    public void agregarDecimal(View view) {
        String textoActual = tvTemp.getText().toString();

        // Si está vacío o el último carácter es un operador, empieza con "0."
        if (textoActual.isEmpty() || esUltimoCaracterOperador(textoActual)) {
            tvTemp.setText(textoActual + "0.");
            return;
        }

        // Obtener el último número para verificar si ya tiene punto
        String ultimoNumero = obtenerUltimoNumero(textoActual);
        if (!ultimoNumero.contains(".")) {
            tvTemp.setText(textoActual + ".");
        }
    }

    private boolean esUltimoCaracterOperador(String texto) {
        if (texto.isEmpty()) return false;
        char ultimo = texto.charAt(texto.length() - 1);
        return ultimo == '+' || ultimo == '-' || ultimo == '*' || ultimo == '/';
    }

    private String obtenerUltimoNumero(String texto) {
        String[] partes = texto.split("[+\\-*/]");
        return partes[partes.length - 1];
    }


}