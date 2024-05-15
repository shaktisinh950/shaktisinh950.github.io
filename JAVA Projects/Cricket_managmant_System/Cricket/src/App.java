import java.io.*;
import java.sql.*;
import java.util.*;

public class App {
    public static void main(String[] args) throws SQLException, IOException {
        Scanner sc = new Scanner(System.in);
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cricket", "root", "");
        PreparedStatement pst = con.prepareStatement("select * from player_details;");
        ResultSet rs = pst.executeQuery();
        String teamsName[] = { "CSK", "RCB", "MI", "KKR", "KXIP", "RR", "GT", "SRH", "LSG", "DC" };
        ArrayList<Team> computers = new ArrayList<>();
        System.out.println("Select Team : ");
        for (int i = 0; i < teamsName.length; i++) {
            System.out.println((i + 1) + ". " + teamsName[i]);
        }
        int choice = sc.nextInt();
        sc.nextLine();
        String p_team = teamsName[choice - 1];
        Team player = new Team(p_team);

        for (int i = 0, j = 0; i < teamsName.length; i++) {
            if (i != choice - 1) {
                Team t = new Team(teamsName[i]);
                computers.add(t);
            }
        }

        while (rs.next()) {
            Player p = new Player(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getInt(4), rs.getInt(5),
                    rs.getInt(6), rs.getInt(7), rs.getInt(8));
            ArrayList<Team> biddingTeam = new ArrayList<>();
            biddingTeam.add(player);
            for (Team t : computers) {
                t.setMax_price(p.bating_avg);
                biddingTeam.add(t);
            }

            double price = 0;
            System.out.println();
            System.out.println("Player Details : ");
            System.out.println(p.toString());
            System.out.println("Start ");
            double maxBidd = 100;
            String maxBiddTeam = null;
            while (biddingTeam.size() != 1) {
                System.out.println("MAX Bidd : " + maxBidd);
                System.out.println();
                double lastBidd = maxBidd;
                ArrayList<Team> deleteTeam = new ArrayList<>();
                for (Team t : biddingTeam) {
                    if (t.equals(player)) {
                        System.out.println("Enter Your Price : ");
                        price = sc.nextDouble();
                        sc.nextLine();
                        if (maxBidd < price && price != 0) {
                            maxBidd = price;
                            maxBiddTeam = t.team_name;
                        }
                        lastBidd = maxBidd;
                    }

                    else {
                        price = t.getComputerBidding(lastBidd);
                        if (price > maxBidd && price != 0) {
                            maxBidd = price;
                            maxBiddTeam = t.team_name;
                        }
                        lastBidd = maxBidd;
                    }

                    if (price == 0) {
                        System.out.println(t.team_name + " Exit");
                        deleteTeam.add(t);
                    }
                    // System.out.println(maxBiddTeam + " " + maxBidd);
                    // System.out.println();
                }
                System.out.println(maxBiddTeam + " " + maxBidd);
                biddingTeam.removeAll(deleteTeam);
                Collections.shuffle(biddingTeam);
            }
            maxBiddTeam = biddingTeam.get(0).team_name;
            // System.out.println(maxBiddTeam + " " + maxBidd);
            p.price = maxBidd;
            if (maxBiddTeam.equalsIgnoreCase(player.team_name)) {
                player.addPlayer(p);
            } else {
                for (Team t : computers) {
                    if (t.team_name.equalsIgnoreCase(maxBiddTeam)) {
                        t.addPlayer(p);
                    }
                }
            }

        }
        player.displayTeam();
        for (Team t : computers) {
            t.displayTeam();
        }

    }
}

class Player {
    int p_id;
    String player_name;
    String p_type;
    int matches;
    int runs;
    int played_bowl;
    int wickets;
    int bowl;
    double bating_avg;
    double bowl_str;
    double price;

    public Player(int p_id, String player_name, String p_type, int matches, int runs, int played_bowl, int wickets,
            int bowl) {
        this.p_id = p_id;
        this.player_name = player_name;
        this.p_type = p_type;
        this.matches = matches;
        this.runs = runs;
        this.played_bowl = played_bowl;
        this.wickets = wickets;
        this.bowl = bowl;
        this.bating_avg = runs / matches;
        if (wickets == 0) {
            this.bowl_str = 0;
        } else {
            this.bowl_str = bowl / wickets;
        }
    }

    @Override
    public String toString() {
        return "Player p_id=" + p_id + "  " + ", player_name=" + player_name + "  " + ", p_type=" + p_type +
                "\nmatches=" + matches + "  " + ", runs=" + runs + "  " + ", played_bowl=" + played_bowl +
                "\nwickets=" + wickets + "  " + ", bowl=" + bowl +
                "\nbating_avg=" + bating_avg + "  " + ", bowl_str=" + bowl_str +
                "\nPrice : " + price;
    }

}

class Team {
    String team_name;
    int playerCount;
    ArrayList<Player> player = new ArrayList<>();

    public Team(String team_name) {
        this.team_name = team_name;
    }

    void addPlayer(Player p) throws IOException {
        System.out.println(team_name + " Added");
        if (playerCount <= 15) {
            player.add(p);
            playerCount++;
            File f = new File(team_name + ".txt");
            f.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            bw.write(p.toString());
            bw.newLine();
            bw.flush();
            bw.close();
        } else {
            System.out.println("Team Is Full!!!");
        }
    }

    void displayTeam() {
        System.out.println("Team " + team_name);
        for (Player p : player) {
            System.out.println(p.toString());
            System.out.println();
        }
        try {
            writeTeamDetails();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void writeTeamDetails() throws IOException {
        File f = new File(team_name + ".txt");
        f.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));
        bw.write("Team Name : " + team_name);
        bw.newLine();
        bw.write("Player : ");
        bw.newLine();
        for (Player p : player) {
            bw.write(p.toString());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    double max_price;

    double getComputerBidding(double bidd) {
        if (bidd >= max_price) {
            return 0;
        }
        double newBidd = bidd + Math.random() * bidd * 0.1;
        return newBidd;
    }

    public void setMax_price(double depant) {
        max_price = Math.random() * depant * 100;
    }

}
