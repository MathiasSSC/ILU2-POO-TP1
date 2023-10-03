package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	//classe interne Marche
	
	private static class Marche{
		private Etal[] etals;
		
		public Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for(int i = 0; i<nbEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal,Gaulois vendeur,String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur,produit,nbProduit);
		}
		
		public int trouverEtalLibre() {
			for(int i = 0; i<etals.length; i++) {
				if(!etals[i].isEtalOccupe()){
					return i;
				}
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtals = etals.length;
			Etal[] etalsAvecProduit;
			etalsAvecProduit = new Etal[nbEtals];
			int indiceAvecProd = 0;
			
			for(int i = 0; i<nbEtals; i++) {
				if(etals[i].isEtalOccupe()) {
					if(etals[i].contientProduit(produit)){
						etalsAvecProduit[indiceAvecProd] = etals[i];
						indiceAvecProd ++;
					}
				}
			}
			return etalsAvecProduit;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].getVendeur() == gaulois){
					return etals[i];
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide = 0;
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				}else {
					nbEtalVide ++;
				}
			}
			if(nbEtalVide > 0) {
				chaine.append("Il reste " + nbEtalVide + " etals non utilises dans le marche.\n");
			}
			return chaine.toString();
		}
		
	}
	
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (chef == null) {
			throw new VillageSansChefException("Le village ne peut pas exister sans chef");
		}
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int etal = marche.trouverEtalLibre();
		if(etal != -1) {
			chaine.append(vendeur.getNom() + " cherche un endroit pour vendre "+ nbProduit +" "+ produit +".\r\n"
					+ "Le vendeur " + vendeur.getNom() +" vend des "+ produit +" à l'étal n°"+ (etal+1) +".\r\n");
			marche.utiliserEtal(etal,vendeur,produit,nbProduit);
		}
		
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal [] etalsAvecProduit = marche.trouverEtals(produit);
		int i = 0;
		if(etalsAvecProduit[i] != null) {
			chaine.append("Les vendeurs qui proposent des fleurs sont :\r\n");
			while(etalsAvecProduit[i] != null) {
					chaine.append("- "+ etalsAvecProduit[i].getVendeur().getNom() +"\r\n");
					i++;
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = rechercherEtal(vendeur);
		return etal.libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village '" + nom + "' possède plusieurs étals :\r\n" + marche.afficherMarche());
		return chaine.toString();
	}


}