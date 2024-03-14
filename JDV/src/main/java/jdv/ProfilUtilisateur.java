package jdv;

class ProfilUtilisateur{
    private String nomUtilisateur;
    private String password;
    private int cellAlive;

    public ProfilUtilisateur(String name, String password, int cellAlive){

        System.out.println("Entrez votre pseudo: ");
        this.nomUtilisateur = name;

        System.out.println("Entrez votre mdp: ");
        this.password = password;

        System.out.println("Entrez votre règle: ");
        this.cellAlive = cellAlive;
    }



    // Getters et Setters
    public String getNomUtilisateur(){
        return nomUtilisateur;
    }

    public String getPassword(){
        return password;
    }

    public int getCellAlive(){
        return cellAlive;
    }
}