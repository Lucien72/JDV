package jdv;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import sockets.ChatClient;

import java.util.Scanner;


public class JeuDeLaVie{

    private static final int TAILLE_GRILLE = 10; // Taille de la grille (par exemple 10x10)
    private static ProfilUtilisateur p1;

    private boolean[][] grille;
    private int casesVivantes;

    public JeuDeLaVie(ProfilUtilisateur profil){
        grille = new boolean[TAILLE_GRILLE][TAILLE_GRILLE];
        casesVivantes = profil.getCellAlive();
        initialiserGrille(casesVivantes);
    }

    private void initialiserGrille(int casesVivantes){
        int compteur = 0;
        Random random = new Random();
        while(compteur < casesVivantes){
            int i = random.nextInt(TAILLE_GRILLE);
            int j = random.nextInt(TAILLE_GRILLE);
            if(!grille[i][j]){
                grille[i][j] = true;
                compteur++;
            }
        }
    }

    private void afficherGrille(){
        System.out.print("\033[H\033[2J");
        for(int i = 0; i < TAILLE_GRILLE; i++){
            for(int j = 0; j < TAILLE_GRILLE; j++){
                System.out.print(grille[i][j] ? "X" : ".");
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void jouerUneEtape(){
        boolean[][] nouvelleGrille = new boolean[TAILLE_GRILLE][TAILLE_GRILLE];

        // Appliquer les règles du jeu de la vie
        for(int i = 0; i < TAILLE_GRILLE; i++){
            for(int j = 0; j < TAILLE_GRILLE; j++){
                int nbVoisinesVivantes = compterVoisinesVivantes(i, j);
                if(grille[i][j]){
                    nouvelleGrille[i][j] = (nbVoisinesVivantes == 2 || nbVoisinesVivantes == 3);
                }
                else{
                    nouvelleGrille[i][j] = (nbVoisinesVivantes == 3);
                }
            }
        }

        grille = nouvelleGrille;
    }

    private int compterVoisinesVivantes(int x, int y){
        int nbVoisinesVivantes = 0;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                int voisinX = x + i;
                int voisinY = y + j;
                if((i != 0 || j != 0) && voisinX >= 0 && voisinY >= 0 && voisinX < TAILLE_GRILLE && voisinY < TAILLE_GRILLE){
                    if (grille[voisinX][voisinY]){
                        nbVoisinesVivantes++;
                    }
                }
            }
        }
        return nbVoisinesVivantes;
    }

    public static int menu(){
        int selection;
        Scanner input = new Scanner(System.in);

        /***************************************************/
        System.out.println("1 - Se connecter");
        System.out.println("2 - S'inscrire");
        System.out.println("Autre - Quitter");

        try {
            selection = input.nextInt();
            // input.close();
            return selection;
        } catch (Exception e) {
            // input.close();
            return 0;
        } 
    }

    public static void main(String[] args) throws InterruptedException{
    // ---------------  LANCEMENT DU JEU  ------------------
        ConnectionDB databaseConnector = new ConnectionDB();
        QueryExecutor queryExecutor = new QueryExecutor(databaseConnector);

        // Demande à l'utilisateur l'adresse IP du serveur
        String serverAddress = "127.0.0.1";

        int choice;


        choice = menu();
        Scanner console = new Scanner(System.in);
        switch(choice) {
            case 1:
                // Se connecter
                System.out.println("Entrez votre pseudo: ");
                String name = console.nextLine();
                boolean success = false;
                while(!success){
                    p1 = queryExecutor.getProfilUtilisateur(name);
                    // success = true;
                    if(p1 == null){
                        System.out.println("Saisie invalide: ");
                        name = console.nextLine();
                    }
                    else{
                        success = true;
                    }
                }
                break;
            
            case 2:
                // S'inscrire
                System.out.println("1");
                System.out.println("Entrez votre pseudo: ");
                String nomUtilisateur = console.nextLine();

                System.out.println("Entrez votre mdp: ");
                String password = console.nextLine();

                System.out.println("Entrez votre règle: ");
                int cellAlive = console.nextInt();

                ProfilUtilisateur newAcc = new ProfilUtilisateur(nomUtilisateur, password, cellAlive);
                queryExecutor.insertUtilisateur(newAcc);
                p1 = queryExecutor.getProfilUtilisateur(nomUtilisateur);
                break;

            default:
                System.exit(0);
                break;
        }

        // Crée une instance de ChatClient
        new ChatClient(serverAddress, p1.getNomUtilisateur());

        // console.close();
        JeuDeLaVie jeu = new JeuDeLaVie(p1);

        while(true){
            jeu.afficherGrille();
            jeu.jouerUneEtape();
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
