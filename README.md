# JDV
Projet Fil Rouge Dev Avancé : Jeu de la vie

Vous êtes la maitrise d’ouvrage et on vous demande aujourd’hui de réaliser une application pour le client (MOE) – le professeur.
Cette application qu’on veut réaliser va pouvoir permettre de se connecter à un profil utilisateur afin d’avoir accès à des données personnelles et de récupérer les règles spécifiques au jeu de la vie que l’on veut implémenter.
On veut créer un jeu de la vie, qui se joue à 0 joueur et qui prend en paramètres les nombres de cases vivantes et mortes défini dans une base de données.
Pour en savoir plus sur le jeu de la vie :
https://fr.wikipedia.org/wiki/Jeu_de_la_vie
Le nom de l’utilisateur sera codifié de manière à représenter ces règles type : 23A  ( 23 Alives)
Une cellule possède huit voisines, qui sont les cellules adjacentes horizontalement, verticalement et diagonalement.
une cellule morte possédant exactement trois cellules voisines vivantes devient vivante (elle naît) ;
une cellule vivante possédant deux ou trois cellules voisines vivantes le reste, sinon elle meurt.


Vous allez devoir réaliser un document de spécification technique reprenant les fonctionnalités et spécificité de l’application puis proposer un plan de réalisation (date de livraison, liste des tâches nécessaires au rendu + découpage). Dans ce contexte, vous mettrez en place toutes les bonnes pratiques nécessaires à la future distribution et maintenabilité de votre application à savoir :
Un repos Github pour mettre votre projet et le rendre visible ainsi que sa documentation fonctionnelle et technique
(incrémentation au fur et à mesure de l’avancée de la classe)

Justifier de vos choix techniques
Donner un planning de développement
Mettre en place des tests unitaires
Les logins de connexion ne doivent pas apparaître en clair (voir Salt si besoin)
