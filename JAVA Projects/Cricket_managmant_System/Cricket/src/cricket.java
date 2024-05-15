// import java.sql.*;
// import java.util.ArrayList;
// import java.util.Scanner;

// public class cricket {
// public static void main(String[] args) throws SQLException {
// Scanner sc = new Scanner(System.in);
// Connection con =
// DriverManager.getConnection("jdbc:mysql://localhost:3306/cricket", "root",
// "");
// PreparedStatement pst = con.prepareStatement("select * from
// player_details;");
// ResultSet rs = pst.executeQuery();
// String teamsName[] = { "CSK", "RCB", "MI", "KKR", "KXIP", "RR", "GT", "SRH",
// "LSG", "DC" };
// Computer computers[] = new Computer[7];
// System.out.println("Select Team : ");
// for (int i = 0; i < teamsName.length; i++) {
// System.out.println((i + 1) + teamsName[i]);
// }
// int choice = sc.nextInt();
// sc.nextLine();
// Team player = new Team(teamsName[choice - 1]);
// for (int i = 0, j = 0; i < teamsName.length; i++) {
// if (i != choice - 1 && j < 7) {
// computers[j] = new Computer(teamsName[i]);
// j++;
// }
// }
// while (rs.next()) {
// Player p = new Player(rs.getInt(1), rs.getString(2), rs.getString(3),
// rs.getInt(4), rs.getInt(5),
// rs.getInt(6), rs.getInt(7), rs.getInt(8));
// for (Computer c : computers) {
// c.setMax_price(p.bating_avg);
// }
// double price;
// System.out.println("Player Details : ");
// System.out.println(p.toString());
// System.out.println("Start ");
// double c_price = 10;
// double f_price = 10;
// double maxBidd = 10;
// boolean b2 = false;
// while (true) {
// int index = 0;

// double b[] = new double[7];
// System.out.println("MAX Bidd : " + maxBidd);
// if (f_price != 0) {
// System.out.println("Enter Your Price : ");
// f_price = sc.nextDouble();
// sc.nextLine();
// }
// if (f_price != 0)
// maxBidd = f_price;

// if (f_price == 0 && b2) {
// computers[index].addPlayer(p);
// System.out.println(computers[index].team_name + " " + maxBidd);
// break;
// }
// for (int i = 0; i < 6; i++) {
// b[i] = computers[i].getComputerBidding(maxBidd);
// // System.out.println(computers[i].team_name + " " + b[i]);
// }
// index = maxBidd(b);
// b2 = true;
// double comMaxBidd = b[index];
// if (comMaxBidd != 0) {
// System.out.println(computers[index].team_name + " " + comMaxBidd);
// }

// if (comMaxBidd == 0 && f_price != 0 && player.playerCount <= 15) {
// player.addPlayer(p);
// System.out.println(player.team_name + " " + f_price);
// break;
// } else {
// if (comMaxBidd == 0) {
// System.out.println("UnSold");
// }
// maxBidd = comMaxBidd;
// }

// }
// }

// player.displayTeam();
// System.out.println();
// // computer.displayTeam();
// }

// static int maxBidd(double[] bid) {
// int max = 0;
// for (int i = 0; i < bid.length; i++) {
// if (bid[i] > bid[max]) {
// max = i;
// }
// }
// return max;
// }
// }

// class Computer extends Team {
// public Computer(String team_name) {
// super(team_name);
// // TODO Auto-generated constructor stub
// }

// double max_price;

// double getComputerBidding(double bidd) {
// if (bidd >= max_price) {
// return 0;
// }
// double newBidd = bidd + Math.random() * bidd;
// return newBidd;
// }

// public void setMax_price(double depant) {
// max_price = Math.random() * depant * 10;
// }
// }

// class Player {
// int p_id;
// String player_name;
// String p_type;
// int matches;
// int runs;
// int played_bowl;
// int wickets;
// int bowl;
// double bating_avg;
// double bowl_str;
// double price;

// public Player(int p_id, String player_name, String p_type, int matches, int
// runs, int played_bowl, int wickets,
// int bowl) {
// this.p_id = p_id;
// this.player_name = player_name;
// this.p_type = p_type;
// this.matches = matches;
// this.runs = runs;
// this.played_bowl = played_bowl;
// this.wickets = wickets;
// this.bowl = bowl;
// this.bating_avg = runs / matches;
// this.bowl_str = bowl / wickets;
// }

// @Override
// public String toString() {
// return "Player p_id=" + p_id + ", player_name=" + player_name + ", p_type=" +
// p_type +
// "\nmatches=" + matches + ", runs=" + runs + ", played_bowl=" + played_bowl +
// "\nwickets=" + wickets + ", bowl=" + bowl +
// "\nbating_avg=" + bating_avg + ", bowl_str=" + bowl_str +
// "\nPrice : " + price;
// }

// }

// class Team {
// String team_name;
// int playerCount;
// ArrayList<Player> player = new ArrayList<>();

// public Team(String team_name) {
// this.team_name = team_name;
// }

// void addPlayer(Player p) {
// if (playerCount <= 15) {
// player.add(p);
// playerCount++;
// } else {
// System.out.println("Team Is Full!!!");
// }
// }

// void displayTeam() {
// System.out.println("Team " + team_name);
// for (Player p : player) {
// System.out.println(p.toString());
// System.out.println();
// }
// }
// }
