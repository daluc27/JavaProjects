
/*
Seninle ekleme butonu ekleyeceğiz sana önceki kodumu atıcam ondan buna transfer gibi bişi yapcaz
 */
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.io.*;
import java.util.Scanner;


public class InktMap2GUI {

    private HashMap<String, String> inkten = new HashMap<>();


    public void ladenUitBestand(HashMap<String, String> inkten){

        try {

            File bestand = new File("Inkten.txt");

            Scanner bestandScanner = new Scanner(bestand);

            while (bestandScanner.hasNextLine()) {

                String lijn = bestandScanner.nextLine();
                if (lijn.trim().isEmpty()) continue;

                String[] apart = lijn.split(",");

                if (apart.length >= 2) {
                    inkten.put(apart[0].trim(), apart[1].trim());
                } else {
                    System.out.println("verkeerde lijn overgeslagen " + lijn);
                }
            }


            bestandScanner.close();
            System.out.println("Het bestand is succesvol geupload.");
        }

        catch(FileNotFoundException e) {
            System.out.println("Het bestand in not gevonden.");


        }



    }

    public void opslaanInBestand(){

        try(PrintWriter writer = new PrintWriter(new FileWriter("Inkten.txt", false))) {

            for (String key : inkten.keySet()) {
                writer.println(key + "," + inkten.get(key));

            }
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fout bij opslaan" + e.getMessage());
        }

    }





    public InktMap2GUI(){

        ladenUitBestand(inkten);

         JFrame frame = new JFrame("Schut Inktmap");
         frame.setSize(600,400);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));


         JLabel label = new JLabel("Inkt");
         JTextField textField = new JTextField(15);
         JButton butonZoek = new JButton("Zoek");
         JButton butonToevoeren = new JButton("Inkt Invoeren");

         frame.add(label);
         frame.add(textField);
         frame.add(butonZoek);
         frame.add(butonToevoeren);


        butonZoek.addActionListener(e -> {
             String zoektInkt = textField.getText().trim();

             if (inkten.containsKey(zoektInkt)){

                 String lokatie = inkten.get(zoektInkt);
                 JOptionPane.showMessageDialog(frame, zoektInkt + " is staat bij " + lokatie);
             }
             else{
                 JOptionPane.showMessageDialog(frame, zoektInkt + " is niet gevonden");
             }


         });

        butonToevoeren.addActionListener(e -> {
            String inktNaam = textField.getText().trim();

            if(inktNaam.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Voer een inkt nummer of naam in ");
                return;
            }

            if (inkten.containsKey(inktNaam)){
                int antword = JOptionPane.showConfirmDialog(frame, inktNaam + " bestaat al op " + inkten.get(inktNaam) + " Locatie wijzijgen"
                , "Let Op!!" , JOptionPane.YES_NO_OPTION);

                if(antword!= JOptionPane.YES_OPTION) return;
            }


            String nieuwLocatie = JOptionPane.showInputDialog(frame, "Voer de locatie in voor " + inktNaam + ": ");

            if(nieuwLocatie != null && !nieuwLocatie.trim().isEmpty()){

                nieuwLocatie = nieuwLocatie.toUpperCase();

               inkten.put(inktNaam,nieuwLocatie);

               opslaanInBestand();

               JOptionPane.showMessageDialog(frame, inktNaam + " is toegevoegd aan ---------> " + nieuwLocatie);
               textField.setText("");
            }

        });

         frame.setVisible(true);



     }

     public static void main(String[] args){

         new InktMap2GUI();

     }


}
