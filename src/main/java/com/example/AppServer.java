package com.example;
import java.io.*;
import java.net.*;


public class AppServer{
    
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaFinale = null;
    String risultato = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    boolean nonCorretto = true;

    public Socket attenti(){
        try{
            System.out.println("SERVER partito in esecuzione ...");

            server = new ServerSocket(6789);

            client = server.accept();

            server.close();

            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));

            outVersoClient = new DataOutputStream(client.getOutputStream());
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server!");
            System.exit(1);
        }
        return client;
    }

    public void comunica(){
        try{
            //INSERIMENTO PRIMO OPERANDO CON RISPOSTA DI INSERIMENTO CORRETTO
            System.out.println("Scrivi il primo operando. Attendo...");
            stringaRicevuta = inDalClient.readLine();
            float operando1 = 0;
            
            try {
                operando1 = Float.parseFloat(stringaRicevuta);
                System.out.println("Ricevuto l'operando dal cliente: " + stringaRicevuta);

                System.out.println("Invio conferma al client...");
                outVersoClient.writeBytes("Inserimento corretto" + '\n');

            } catch (Exception e) {
                outVersoClient.writeBytes("Non hai inserito un numero!" + '\n');
            }

            //INSERIMENTO SECONDO OPERANDO CON RISPOSTA DI INSERIMENTO CORRETTO
            System.out.println("Scrivi il secondo operando. Attendo...");
            stringaRicevuta = inDalClient.readLine();
            float operando2 = 0;

            try {
                operando2 = Float.parseFloat(stringaRicevuta);
                System.out.println("Ricevuto l'operando dal cliente: " + stringaRicevuta);

                System.out.println("Invio conferma al client...");
                outVersoClient.writeBytes("Inserimento corretto" + '\n');

            } catch (Exception e) {
                outVersoClient.writeBytes("Non hai inserito un numero!" + '\n');
            }

            

            do {

                //INSERIMENTO OPERATORE CON RISPOSTA RISULTATO
                System.out.println("Scrivi l'operatore. Attendo...");
                stringaRicevuta = inDalClient.readLine();
                String operatore = stringaRicevuta;
                System.out.println("Ricevuto l'operando dal cliente: " + stringaRicevuta);

                switch(operatore){
                case "+": 
                        risultato = String.valueOf(operando1 + operando2);
                        nonCorretto = false;
                        break;
                case "-": 
                        risultato = String.valueOf(operando1 - operando2);
                        nonCorretto = false;
                        break;
                case "*": 
                        risultato = String.valueOf(operando1 * operando2);
                        nonCorretto = false;
                        break;
                case "/":
                        nonCorretto = false; 
                        if(operando2 == 0){
                            risultato = "Impossibile dividere per 0";
                            break;
                        }
                        risultato = String.valueOf(operando1 / operando2);
                        break;
                case "x": 
                        nonCorretto = false;
                        risultato = String.valueOf(operando1 * operando2);
                        break;
                default: nonCorretto = true;
                         System.out.println("Invio conferma al client...");
                         outVersoClient.writeBytes("ERRORE" + '\n');
                         break;
                
            }
            } while (nonCorretto);
            
            System.out.println("Invio conferma al client...");
            outVersoClient.writeBytes("Inserimento corretto" + '\n');

            System.out.println("Invio risultato al client...");
            outVersoClient.writeBytes(String.valueOf(risultato) + '\n');

            System.out.println("SERVER: fine elaborazione!");
            client.close();
        }catch(Exception e){

        }
    }

    public static void main(String args[]){
        AppServer servente = new AppServer();
        servente.attenti();
        servente.comunica();
    }
}