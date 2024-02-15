package jdv;

class ProfilUtilisateur {
    private int id;
    private String nomUtilisateur;
    private String password;
    private int cellAlive;

    public ProfilUtilisateur(int id, String nomUtilisateur, String password, int cellAlive) {
        this.id = id;
        this.nomUtilisateur = nomUtilisateur;
        this.password = password;
        this.cellAlive = cellAlive;
    }



    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public String getPassword() {
        return password;
    }

    public int getCellAlive(){
        return cellAlive;
    }
}