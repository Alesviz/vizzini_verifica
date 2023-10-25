package com.example;
import java.io.*;
import java.net.*;

/**
 * Hello world!
 *
 */
public class AppClient 
{

    String nomeServer = "localHost";
    int portaServer = 6789;
    Socket mioSocket;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    String risposta = null;
    boolean errore = false;

    public Socket connetti(){
        System.out.println("C5LIENT partito in esecuzione...");
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in));

            mioSocket = new Socket(nomeServer, portaServer);

            outVersoServer = new DataOutputStream(mioSocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Host sconosciuto! \n");
        }
        catch(Exception e){
                System.out.println(e.getMessage());
                System.out.println("Errore durante la connessione! \n");
                System.exit(1);
        }
        return mioSocket;
    }

    public void comunica(){
        try {

            //COMUNICA PRIMO OPERANDO
            System.out.println("Inserisci il primo operando! \n");
            stringaUtente = tastiera.readLine();

            System.out.println("Invio operando al server e attendo... \n");
            outVersoServer.writeBytes(stringaUtente + '\n');

            //RISPOSTA DI CONFERMA
            stringaRicevutaDalServer = inDalServer.readLine();
            System.out.println("Risposta dal server: " + stringaRicevutaDalServer + "\n");

            //COMUNICA SECONDO OPERANDO
            System.out.println("Inserisci il secondo operando! \n");
            stringaUtente = tastiera.readLine();

            System.out.println("Invio operando al server e attendo... \n");
            outVersoServer.writeBytes(stringaUtente + '\n');

            //RISPOSTA DI CONFERMA
            stringaRicevutaDalServer = inDalServer.readLine();
            System.out.println("Risposta dal server: " + stringaRicevutaDalServer + "\n");

            //COMUNICA OPERATORE
            do{
                errore = false;
                System.out.println("Inserisci operatore! \n");
                stringaUtente = tastiera.readLine();

                System.out.println("Invio operando al server e attendo... \n");
                outVersoServer.writeBytes(stringaUtente + '\n');
                
                //RISPOSTA DI CONFERMA
                risposta = inDalServer.readLine();
                System.out.println("Risposta dal server: " + risposta + "\n");

                if(risposta.equals("ERRORE")){
                    System.out.println("ERRORE");
                    errore = true;
                }
            } while(errore);


            //RISPOSTA RISULTATO
            stringaRicevutaDalServer = inDalServer.readLine();
            System.out.println("RISULTATO: " + stringaRicevutaDalServer + "\n");

            System.out.println("CLIENT: termina elaborazione e chiude connessione");
            mioSocket.close();
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server!");
            System.exit(1);
        }
    }

    public static void main( String[] args ){
        AppClient cliente = new AppClient();
        cliente.connetti();
        cliente.comunica();
    }
}
