package dad.adivinapp;

import java.util.Random;

import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdivinApp extends Application{
    
    private TextField entradaNumero;
    private Label limitesLabel;
    private Button comprobarButton;
    private VBox root;

    private int numeroAleatorio, numeroIntentos = 0;

    private Alert alerta;

    @Override
    public void start(Stage primaryStage) throws Exception {

        entradaNumero = new TextField();
        entradaNumero.setPromptText("Introduzca un número");

        comprobarButton = new Button("Comprobar");
        comprobarButton.setOnAction(this::onComprobarAction); // Llamamos a la función encargada del procesamiento del numero introducido
        comprobarButton.setDefaultButton(true);
        comprobarButton.setTooltip(new Tooltip("Comprobar si el número es correcto o no"));

        limitesLabel = new Label();
        limitesLabel.setText("Introduce un número entre 1 y 100");

        // Añadimos todo lo necesario desde el constructor, ahorrando un par de lineas de código
        root = new VBox(5, limitesLabel, entradaNumero, comprobarButton);
        root.setAlignment(Pos.CENTER); // Centra el contenido en el centro de la ventana
        root.setFillWidth(false);

        Scene scene = new Scene(root, 320, 200);
        primaryStage.setTitle("AdivinApp");
        primaryStage.setScene(scene);
        primaryStage.show();

        crearAleatorio();

    }
    
    private void onComprobarAction(Event event) {

        /* 
         * Creamos la función para poder comprobar si el número introducido es igual que el que genera el programa.
         * Para ello usamos un bloque Try Catch para poder tratar la posible excepción que nos pueda dar al intentar parsear texto.
         * Para cada uno de los casos creamos la alerta correspondiente y configuramos el texto que queremos que se visualice.
         */

        try {

            int numero = Integer.parseInt(entradaNumero.getText());
            if(numero == numeroAleatorio) {
                alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("AdivinApp");
                alerta.setHeaderText("¡Has ganado!");
                alerta.setContentText("Solo has necesitado " + numeroIntentos + " intentos. \nVuelve a jugar y hazlo mejor");
                alerta.showAndWait();
                crearAleatorio();
                numeroIntentos = 0;
            }  else if (numero < 1 || numero > 100) {
                alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("AdivinApp");
                alerta.setHeaderText("Error");
                alerta.setContentText("El número introducido no es válido");
                alerta.showAndWait();
            } else {
                alerta = new Alert(AlertType.WARNING);
                alerta.setTitle("AdivinApp");
                alerta.setHeaderText("¡Has fallado!");
                if(numero < numeroAleatorio)
                    alerta.setContentText("El número a adivinar es mayor que " + numero + "\nVuelve a intentarlo");
                else
                    alerta.setContentText("El número a adivinar es menor que " + numero + "\nVuelve a intentarlo");
                alerta.showAndWait();
                numeroIntentos++;
            }

        } catch (NumberFormatException nfe) {

            alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("AdivinApp");
            alerta.setHeaderText("Error");
            alerta.setContentText("El número introducido no es válido");
        
        } catch (Exception e) {
            System.err.println("ERROR - Ha ocurrido un error inesperado");
        }

        entradaNumero.requestFocus();
    }

    private void crearAleatorio() {
        Random rand = new Random();
        numeroAleatorio = rand.nextInt(99) + 1;
    }

    public static void main( String[] args ) {
        launch(args);
    }

    
}
