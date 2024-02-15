package jdv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class JeuDeLaVie {

    private static final int TAILLE_GRILLE = 10; // Taille de la grille (par exemple 10x10)
    private static final double PROBA_CELLULE_VIVANTE = 0.2; // Probabilité qu'une cellule soit vivante

    private boolean[][] grille;
    private int casesVivantes;

    public JeuDeLaVie(ProfilUtilisateur profil) {
        grille = new boolean[TAILLE_GRILLE][TAILLE_GRILLE];
        casesVivantes = profil.getCellAlive();
        initialiserGrille(casesVivantes);
    }

    private void initialiserGrille(int casesVivantes) {
        int compteur = 0;
        Random random = new Random();
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            for (int j = 0; j < TAILLE_GRILLE; j++) {
                if(casesVivantes > compteur){
                    grille[i][j] = random.nextDouble() < PROBA_CELLULE_VIVANTE;
                    if(grille[i][j])compteur++;
                }
            }
        }
        if(compteur != casesVivantes){
            initialiserGrille(casesVivantes);
        }
    }

    private void afficherGrille() {
        System.out.print("\033[H\033[2J");
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            for (int j = 0; j < TAILLE_GRILLE; j++) {
                System.out.print(grille[i][j] ? "X" : ".");
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void jouerUneEtape() {
        boolean[][] nouvelleGrille = new boolean[TAILLE_GRILLE][TAILLE_GRILLE];

        // Appliquer les règles du jeu de la vie
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            for (int j = 0; j < TAILLE_GRILLE; j++) {
                int nbVoisinesVivantes = compterVoisinesVivantes(i, j);
                if (grille[i][j]) {
                    nouvelleGrille[i][j] = (nbVoisinesVivantes == 2 || nbVoisinesVivantes == 3);
                } else {
                    nouvelleGrille[i][j] = (nbVoisinesVivantes == 3);
                }
            }
        }

        grille = nouvelleGrille;
    }

    private int compterVoisinesVivantes(int x, int y) {
        int nbVoisinesVivantes = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int voisinX = x + i;
                int voisinY = y + j;
                if ((i != 0 || j != 0) && voisinX >= 0 && voisinY >= 0 && voisinX < TAILLE_GRILLE && voisinY < TAILLE_GRILLE) {
                    if (grille[voisinX][voisinY]) {
                        nbVoisinesVivantes++;
                    }
                }
            }
        }
        return nbVoisinesVivantes;
    }

    public static void main(String[] args) throws InterruptedException{

        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3307/JDVBDD", "Lucien", "Lucien")) {
        // create a Statement
        try (Statement stmt = conn.createStatement()) {
        //execute query
        try (ResultSet rs = stmt.executeQuery("SELECT 'Hello World!'")) {
          //position result to first
          rs.first();
          System.out.println(rs.getString(1)); //result is "Hello World!"
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }


    // ---------------  LANCEMENT DU JEU  ------------------
        ConnectionDB databaseConnector = new ConnectionDB();
        QueryExecutor queryExecutor = new QueryExecutor(databaseConnector);

        ProfilUtilisateur p1 = queryExecutor.getProfilUtilisateur("Lucien");
        JeuDeLaVie jeu = new JeuDeLaVie(p1);
        while(true){
            jeu.afficherGrille();
            jeu.jouerUneEtape();
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
