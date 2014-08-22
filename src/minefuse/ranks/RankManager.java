package minefuse.ranks;


public class RankManager {


	public enum ArenaRanks {
		IRONDIVISION1("Iron 1", 0), //KDR 0.50 or below
		IRONDIVISION2("Iron 2", 0), //KDR 0.51 - 1.00
		IRONDIVISION3("Iron 3", 0), //KDR 1.01 - 1.50
		GOLDDIVISION1("Gold 1", 0), //KDR 1.51 - 2.00
		GOLDDIVISION2("Gold 2", 0), //KDR 2.01 - 2.50
		GOLDDIVISION3("Gold 3", 0), //KDR 2.51 - 3.00
		DIAMONDDIVISION1("Diamond 1", 0), //KDR 3.01 - 3.99
		DIAMONDDIVISION2("Diamond 2", 0), //KDR 4.00 an up
		DIAMONDDIVISION3("Diamond 3", 0), //Not Allowed
		MASTERSDIVISION1("Masters 1", 0), //Not Allowed
		MASTERSDIVISION2("Masters 2", 0), //Not Allowed
		GRANDMASTERS("GrandMasters", 0); //Not Allowed

		private final String rank;
		private final int kdrReq;

		ArenaRanks(String currRank, int killdeathReq) {
			rank = currRank;
			kdrReq = killdeathReq;
		}

		public String getRank() {
			return rank;
		}

		public int getKDRreq() {
			return kdrReq;

		}
	}

}