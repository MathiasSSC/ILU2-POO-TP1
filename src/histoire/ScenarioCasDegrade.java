package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Village village = new Village("le village des irréductibles", 10, 5);
		village.afficherVillageois();
		Etal etal = new Etal();
		Gaulois gaulois = new Gaulois("pana",5);
		//etal.libererEtal();
		//etal.acheterProduit(10, null);
		//etal.occuperEtal(gaulois, "fleur",4);
		/*int quantite = 0;
		if(quantite < 1) {
			throw new IllegalArgumentException("la quantité est inférieur à 0");
		}
		etal.acheterProduit(quantite, gaulois);
		*/
		int quantite2 = 2;
		if(!etal.isEtalOccupe()) {
			throw new IllegalArgumentException("Etal vide !!");
		}
		etal.acheterProduit(quantite2, gaulois);
		
		System.out.println("Fin du test");
		}

}
