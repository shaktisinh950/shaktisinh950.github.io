import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.*;
import com.mysql.cj.jdbc.Blob;

import java.time.*;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatter;

class BookMyShow {
    static HashMap<String, UserDetails> hm = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Class.forName("com.mysql.cj.jdbc.Driver");
        String dbURL = "jdbc:mysql://localhost:3306/Book My Show";
        String dbUser = "root";
        String dbPass = "";
        Connection con = DriverManager.getConnection(dbURL, dbUser, dbPass);
        if (con != null) {
            System.out.println("Connection Success...!!");
        }
        System.out.println("                              |----------------------------|");
        Thread.sleep(200);
        System.out.println("                              |                            |");
        Thread.sleep(200);
        System.out.println("                              |          WELCOME           |");
        Thread.sleep(200);
        System.out.println("                              |                            |");
        Thread.sleep(200);
        System.out.println("                              |----------------------------|");
        Thread.sleep(200);
        System.out.println();
        while (true) {
            System.out.println("What do You Want To Do....!!");
            System.out.println("1.Sign Up     <-->     2.Log In      <-->  3.Exit..!!");
            String choice1 = sc.next();

            switch (choice1) {
                case "1":
                    String f_name, m_name, l_name, username = null, password = null, m_num = null;
                    System.out.print("Enter First Name.     : ");
                    f_name = sc.next();
                    System.out.print("Enter Middle Name   : ");
                    m_name = sc.next();
                    System.out.print("Enter Last Name.     : ");
                    l_name = sc.next();
                    while (true) {
                        System.out.print("Enter Mobile Number(starts between 7-9): ");
                        String input = sc.next();
                        if (input.matches("[987]\\d{9}")) {
                            username = f_name + "_" + (int) (Math.random() * (999 - 111))
                                    + input.substring(6, 7);
                            m_num = input;
                            break;
                        } else {
                            System.out.println("  Enter Valid Number!!  ");
                        }
                    }
                    System.out.println("Your Suggested UserName is : " + username);
                    while (true) {
                        if (BookMyShow.hm.containsKey(username)) {
                            System.out.println("Enter username of your Choice : ");
                            username = sc.nextLine();
                        } else {
                            break;
                        }
                    }
                    while (true) {
                        System.out.println("SET PASSWORD(Minimum 8 words) : ");
                        password = sc.next();
                        if (password.length() >= 8) {
                            break;
                        } else {
                            System.out.println("Enter Valid Password");
                        }
                    }
                    UserDetails user = new UserDetails();
                    user.setData(f_name, m_name, l_name, username, password, m_num);
                    hm.put(username, user);
                    File f1 = new File("Users");
                    f1.mkdir();
                    File f = new File("Users/" + username + ".txt");
                    f.createNewFile();
                    FileOutputStream fos = new FileOutputStream(f);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(user);
                    FileInputStream fis1 = new FileInputStream(f);
                    String insertQuery = "INSERT INTO user_data VALUES (?,?)";
                    PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
                    preparedStatement.setString(1, username);
                    preparedStatement.setBinaryStream(2, fis1);
                    preparedStatement.execute();
                    break;
                case "2":
                    File f2 = new File("Users");
                    f2.mkdir();
                    System.out.println("Enter your username :");
                    String useCheck = sc.next();
                    Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    String selectQuery1 = "SELECT Password FROM user_data WHERE Username = '" + useCheck + "'";
                    ResultSet rs = st.executeQuery(selectQuery1);
                    if (rs.next()) {
                        rs.beforeFirst();
                        byte[] b5;
                        File f6 = new File("Users/" + useCheck + "copy.txt");
                        f6.createNewFile();
                        FileOutputStream fos2 = new FileOutputStream(f6);
                        while (rs.next()) {
                            Blob b4 = (Blob) rs.getBlob(1);
                            b5 = b4.getBytes(1, (int) b4.length());
                            fos2.write(b5);
                        }
                        FileInputStream fis2 = new FileInputStream(f6);
                        ObjectInputStream ois2 = new ObjectInputStream(fis2);
                        UserDetails user2 = (UserDetails) ois2.readObject();
                        // Get the current date and time
                        LocalDateTime currentTime = LocalDateTime.now();

                        // Define a date-time format
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd " + "20:12:20");

                        // Format the current time as a string
                        String formattedTime = currentTime.format(formatter);

                        // Display the formatted time
                        System.out.println("Current time: " + formattedTime);
                        hm.put(useCheck, user2);
                        user2.Customerservice();
                        ois2.close();
                        fos2.close();
                    } else {
                        System.out.println("Your username is not present");
                    }
                    break;
                case "3":
                    System.out.println("Thank you....");
                    System.exit(0);
                    break;
            }
        }
    }
}

class UserDetails implements Serializable {
    String f_name, m_name, l_name, username, password, m_num;
    String Area[] = new String[] { "VastraPur", "South Bopal", "Iskcon" };
    double Latitude[] = new double[] { 22.992330, 22.988221, 22.937010 };
    double Longitude[] = new double[] { 72.393865, 72.570676, 72.534284 };
    Booking book = new Booking();

    void Customerservice() throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter PassWord : ");
            String CheckPassword = sc.next();
            if (CheckPassword.equals(password)) {
                break;
            } else {
                System.out.println("Enter Valid Password..");
            }
        }

        // System.out.println("Select your current location(Area)");
        // System.out.println(" 1.\"VastraPur\", 2.\"South Bopal\", 3.\"Iskcon\"");
        // System.out.print("Enter Choice Number :");
        // int choice1 = sc.nextInt();
        // book.suggest_booking(Latitude[choice1 - 1], Longitude[choice1 - 1]);
        System.out.println();

        Theatres th = new Theatres();
        while (true) {
            System.out.println("1. View all movies 2. View all theatres 3. Exit");
            System.out.println("Enter your choice : ");
            int ch = sc.nextInt();
            switch (ch) {
                case 1:
                    th.viewAllMovies();
                    break;
                case 2:
                    th.viewAllTheatres();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Enter valid choice");
            }
        }
    }

    void setData(String f_name, String m_name, String l_name, String username, String password,
            String m_num) {
        this.f_name = f_name;
        this.m_name = m_name;
        this.l_name = l_name;
        this.username = username;
        this.password = password;
        this.m_num = m_num;
    }
}

class Theatres implements Serializable {
    String m_name[];
    String name;
    int theatre_id;
    double rating;
    double latitude, longtitude;
    String type;
    HashMap<String, Shows> shows = new HashMap<>();
    HashMap<String, Theatres> theatres = new HashMap<>();
    Shows show = new Shows();
    int distance;
    // HashMap<String, Shows> hmshows = new HashMap<>();

    public Theatres(String name, String type, double rating, double latitude, double longtitude,
            HashMap<String, Shows> shows, int theatre_id) {
        this.name = name;
        this.rating = rating;
        this.type = type;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.shows = shows;
        this.theatre_id = theatre_id;
    }

    public Theatres() {
        this.getshows();
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    void details_show() {

    }

    void viewAllMovies() throws Exception {
        getshows();
        ArrayList<Shows> showarr = new ArrayList<>();
        for (Entry<String, Shows> entry : shows.entrySet()) {
            showarr.add(entry.getValue());
        }
        Collections.sort(showarr, Comparator.comparing(Shows::getRating).reversed());
        int i = 1;
        for (Shows show : showarr) {
            System.out.println("Movie Name: " + i + ". " + show.name);
            i++;
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your movie choice to see timing in particular theatres : ");
        int ch = sc.nextInt();
        Shows sh = showarr.get(ch - 1);
        String movie = sh.name;
        Booking book = new Booking();
        book.showWithTime(movie);
        book.bookingg();
        Theatres th = new Theatres();

        // int frequency = 0, check = 0;
        // HashMap<String, Theatres> th = HotelDetails();
        // for (Entry<String, Theatres> entry : th.entrySet()) {
        // Theatres sh = entry.getValue();
        // System.out.println(entry.getKey() + " " + sh.type);
        // HashMap<String, Shows> sh = th.shows;
        // Shows val = entry.getValue();
        // System.out.println("Movie Name : " + val.name);
        // for (int k = 0; k < check; k++) {
        // System.out.println(val.times6[k]);
        // }
        // }
    }

    void viewAllTheatres() throws Exception {
        HashMap<String, Theatres> th = HotelDetails();
        for (Entry<String, Theatres> entry : th.entrySet()) {
            Theatres sh = entry.getValue();
            System.out.println(entry.getKey() + " " + sh.type);
        }
    }

    void getshows() {
        m_name = new String[] { "Jawan", "The Avengers", "Black Widow", "Avengers: Endgame",
                "Spider-Man: No Way Home", "Captain Marvel" };
        double rating[] = new double[] { 4.2, 4.4, 4.3, 4.8, 4.6, 4.1 };
        // String ticket_type[] = new String[] { "Classic", "Prime", "Recliner" };
        int k = 1;
        for (int i = 0; i < 6; i++) {
            shows.put(m_name[i],
                    new Shows(m_name[i], rating[i], k));
            k++;
        }
    }

    HashMap<String, Theatres> HotelDetails() throws Exception {
        this.getshows();
        theatres.put("PVR Acropolis", new Theatres("PVR Acropolis", "IMAX", 4.6, 23.018559, 72.314900,
                movie_timing(1), 1));
        theatres.put("Wide Angle", new Theatres("Wide Angle", "IMAX", 4.0, 22.957244, 72.583722,
                movie_timing(1), 2));
        theatres.put("Cinepolis", new Theatres("Cinepolis", "Multiplex", 4.2, 22.878505, 72.325887,
                movie_timing(2), 3));
        theatres.put("City Gold Satellite", new Theatres("City Gold Satellite", "Multiplex", 4.1, 22.965463, 72.507161,
                movie_timing(2), 4));
        theatres.put("Mukta A2",
                new Theatres("Mukta A2", "Single Screen", 4.4, 22.982216, 72.394895,
                        movie_timing(3),
                        5));
        theatres.put("Inox Himaliya Mall",
                new Theatres("Inox Himaliya Mall", "Single Screen", 3.9, 22.863004, 72.506131,
                        movie_timing(3),
                        6));

        return theatres;
    }

    HashMap<String, Shows> movie_timing(int type) {
        int frequency = 0;
        if (type == 1) {
            frequency = 6;
        } else if (type == 2) {
            frequency = 3;
        } else if (type == 3) {
            frequency = 1;
        }
        int check = 1;
        Shows s = new Shows();
        for (Map.Entry<String, Shows> entry : shows.entrySet()) {
            s = entry.getValue();
            s.setTimes(frequency, check);
            check++;
        }
        return shows;
    }
}

class Shows implements Serializable {
    String name, theatre_name;
    Double rating;
    int id;

    public Double getRating() {
        return rating;
    }

    LocalTime[] times6 = new LocalTime[6];
    LocalTime[] times3 = new LocalTime[3];
    LocalTime[] times1 = new LocalTime[1];

    // Populate the array with time values

    public void setTimes(int frequency, int check) {
        if (frequency == 6) {
            times6[0] = LocalTime.of(8, 30); // 08:30 AM
            times6[1] = LocalTime.of(12, 15); // 12:15 PM
            times6[2] = LocalTime.of(15, 45); // 03:45 PM
            times6[3] = LocalTime.of(18, 00); // 06:00 PM
            times6[4] = LocalTime.of(20, 30); // 08:30 PM
            times6[5] = LocalTime.of(22, 45);
        } else if (frequency == 3) {
            if (check % 2 == 0) {
                times3[0] = LocalTime.of(8, 30); // 08:30 AM
                times3[1] = LocalTime.of(15, 45); // 12:15 PM
                times3[2] = LocalTime.of(20, 30); // 03:45 PM
            } else {
                times3[0] = LocalTime.of(12, 15); // 08:30 AM
                times3[1] = LocalTime.of(18, 00); // 12:15 PM
                times3[2] = LocalTime.of(22, 45); // 03:45 PM
            }
        } else if (frequency == 1) {
            if (check == 1)
                times1[0] = LocalTime.of(8, 30);
            else if (check == 2)
                times1[0] = LocalTime.of(12, 15);
            else if (check == 3)
                times1[0] = LocalTime.of(15, 45);
            else if (check == 4)
                times1[0] = LocalTime.of(18, 00);
            else if (check == 5)
                times1[0] = LocalTime.of(20, 30);
            else if (check == 6)
                times1[0] = LocalTime.of(22, 45);
        }
    }

    public Shows(String name, Double rating, int id) {
        this.name = name;
        this.rating = rating;
        this.id = id;
    }

    public Shows() {

    }
}

class Booking implements Serializable {
    static final int MAX_SEATS = 100;
    static final double Recliner_Price = 550;
    static final double Prime_Price = 400;
    static final double Classic_Price = 300;

    // Variables
    static int numPassengers = MAX_SEATS;
    static int numSeatsAvailable = MAX_SEATS;
    static boolean[] seatMap = new boolean[MAX_SEATS];
    static int seatNumber;
    static int count = 0;
    public static final double RADIUS_OF_EARTH_KM = 6371.0;
    Theatres theatre = new Theatres();
    Shows show = new Shows();
    HashMap<String, Theatres> theatres = new HashMap<>();
    double Latitude[] = new double[] { 22.992330, 22.988221, 22.937010 };
    double Longitude[] = new double[] { 72.393865, 72.570676, 72.534284 };

    private static void viewAvailableSeats() {
        System.out.println("Number of available seats: " + numSeatsAvailable);
        System.out.println("Seat Map:");
        System.out.println(" ");

        int k = 1;
        for (int i = 0; i < 25; i++) {
            if (i == 10) {
                System.out.println("Prime");
            } else if (i == 0) {
                System.out.println("Recliner");
            } else if (i == 20) {
                System.out.println("Classic");
            }
            for (int j = 1; j < 5; j++) {
                if (seatMap[k - 1] == true) {
                    System.out.print("X ");
                } else {
                    if (j == 3) {
                        System.out.print("    " + k + " ");
                    } else
                        System.out.print(k + " ");
                }
                k++;
            }
            System.out.println();
        }
        System.out.println("");
    }

    static void bookSeat() {
        Booking customer = new Booking();
        Scanner scanner = new Scanner(System.in);
        if (numSeatsAvailable == 0) {
            System.out.println("Sorry, there are no seats available.");
            return;
        }
        System.out.println("Please choose a seat class:");
        System.out.println("1. Recliner");
        System.out.println("2. Prime");
        System.out.println("3. Classic");
        String classChoice = scanner.nextLine();
        double price;
        switch (classChoice) {
            case "1":
                price = Recliner_Price;
                break;
            case "2":
                price = Prime_Price;
                break;
            case "3":
                price = Classic_Price;
                break;
            default:
                System.out.println("Invalid choice. Booking cancelled.");
                return;
        }

        if (classChoice.equals("1")) {

            viewAvailableSeats();

            System.out.println("Please choose a seat number (1-" + "40" + "):");
            seatNumber = scanner.nextInt();
            do {
                if (seatNumber < 1 || seatNumber > 40) {
                    System.out.println("Invalid seat number. Please choose again.");
                    seatNumber = scanner.nextInt();
                } else if (seatMap[seatNumber - 1]) {
                    System.out.println("Sorry, that seat is already booked. Please choose another seat.");
                    seatNumber = scanner.nextInt();
                } else {
                    seatMap[seatNumber - 1] = true;
                    numPassengers++;
                    numSeatsAvailable--;
                    System.out.println();
                    // passenger.PassengerDetails();
                    // passenger.displayTicket();
                    System.out.println("Price : " + price + "             Seat number : " + seatNumber);

                    break;
                }

            } while (true);
            System.out.println("  ");
            System.out.println("");
            customer.viewAvailableSeats();

        } else if (classChoice.equals("2")) {

            System.out.println("Please choose a seat number (41-" + "80" + "):");
            viewAvailableSeats();
            do {
                System.out.println("Please choose a seat number (41-" + "80" + "):");
                seatNumber = scanner.nextInt();
                if (seatNumber < 40 || seatNumber > 80) {
                    System.out.println("Invalid seat number. Please choose again.");
                } else if (seatMap[seatNumber - 1]) {
                    System.out.println("Sorry, that seat is already booked. Please choose another seat.");
                } else {
                    seatMap[seatNumber - 1] = true;
                    numPassengers++;
                    numSeatsAvailable--;
                    System.out.println();
                    // passenger.PassengerDetails();
                    // passenger.displayTicket();
                    System.out.println("Price : " + price + "             Seat number : " + seatNumber);
                    break;
                }

            } while (true);
            customer.viewAvailableSeats();

        } else if (classChoice.equals("3")) {

            System.out.println("Please choose a seat number (81-" + "100" + "):");
            viewAvailableSeats();

            do {
                seatNumber = scanner.nextInt();
                if (seatNumber < 80 || seatNumber > 101) {
                    System.out.println("Invalid seat number. Please choose again.");
                } else if (seatMap[seatNumber - 1]) {
                    System.out.println("Sorry, that seat is already booked. Please choose another seat.");
                } else {
                    seatMap[seatNumber - 1] = true;
                    numPassengers++;
                    numSeatsAvailable--;
                    System.out.println();
                    // passenger.PassengerDetails();
                    // passenger.displayTicket();
                    System.out.println("Price : " + price + "             Seat number : " + seatNumber);
                    break;
                }

            } while (true);
            scanner.nextLine();
            System.out.println(" ");
            customer.viewAvailableSeats();

        }

    }

    private static void cancelBooking() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your seat number (1-" + MAX_SEATS + "):");
        int seatnumber = scanner.nextInt();
        if (seatnumber < 1 || seatnumber > MAX_SEATS || !seatMap[seatnumber - 1]) {
            System.out.println("Invalid seat number. Cancellation failed.");
            scanner.nextLine();
            return;
        }
        seatMap[seatnumber - 1] = false;
        numPassengers--;
        numSeatsAvailable++;
        System.out.println("Seat " + seatnumber + " cancelled.");
        scanner.nextLine();
    }

    private static void viewBookingStatus() {
        System.out.println("Number of passengers: " + numPassengers);
        System.out.println("Number of available seats: " + numSeatsAvailable);
        System.out.println(" ");
    }

    void bookingg() {
        System.out.println("HELLO");
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Please choose an option:");
            System.out.println("1. Book a seat");
            System.out.println("2. Cancel a booking");
            System.out.println("3. Seat Map");
            System.out.println("4. View Available Seats");
            System.out.println("5. Exit");
            System.out.println("Enter Your Choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter the number of ticket to be book");
                    int n = sc.nextInt();
                    for (int i = 1; i <= n; i++) {
                        bookSeat();
                    }
                    break;
                case 2:
                    cancelBooking();
                    break;
                case 3:
                    viewAvailableSeats();
                    break;
                case 4:
                    viewBookingStatus();
                    break;
                case 5:
                    System.out.println("Thank you for using the BOOK MY SHOW!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    break;
            }
        } while (true);
    }

    void showWithTime(String name) throws Exception {
        theatres = theatre.HotelDetails();
        Theatres th;
        for (Map.Entry<String, Theatres> th1 : theatres.entrySet()) {
            th = th1.getValue();
            int check;
            if (th.theatre_id == 1 || th.theatre_id == 2) {
                check = 6;
                System.out.println("Theatre : " + th.name);
                HashMap<String, Shows> sh = th.shows;
                for (Map.Entry<String, Shows> entry : sh.entrySet()) {
                    Shows val = entry.getValue();
                    if (val.name.equals(name)) {
                        System.out.println("Movie Name : " + val.name);
                        for (int k = 0; k < check; k++) {
                            System.out.println("Show " + (k + 1) + " : " + val.times6[k]);
                        }
                    }
                }
            } else if (th.theatre_id == 3 || th.theatre_id == 4) {
                check = 3;
                System.out.println("Theatre : " + th.name);
                HashMap<String, Shows> sh = th.shows;
                for (Map.Entry<String, Shows> entry : sh.entrySet()) {
                    Shows val = entry.getValue();
                    if (val.name.equals(name)) {
                        System.out.println("Movie Name : " + val.name);
                        for (int k = 0; k < check; k++) {
                            System.out.println("Show " + (k + 1) + " : " + val.times3[k]);
                        }
                    }
                }
            } else {
                check = 1;
                System.out.println("Theatre : " + th.name);
                HashMap<String, Shows> sh = th.shows;
                for (Map.Entry<String, Shows> entry : sh.entrySet()) {
                    Shows val = entry.getValue();
                    if (val.name.equals(name)) {
                        System.out.println("Movie Name : " + val.name);
                        for (int k = 0; k < check; k++) {
                            System.out.println("Show " + (k + 1) + " : " + val.times1[k]);
                        }
                    }
                }
            }
        }
    }
}
// gest_booking(double lat, double longi) throws Exception {
// theatres = theatre.HotelDetails();
// yList<Theatres> hotelarray = new ArrayList<>();
// (Map.Entry<String, Theatres> entry : theatres.entrySet()) {
// Theatres value = entry.getValue();
// at1 = value.latitude;
// on1 = value.longtitude;
// ance = (int) calculateHaversineDistance(lat1, lon1, lat, longi);
// tDistance(distance);
//
//

// ator<Theatres> itr = hotelarray.iterator();
// i = 0;
// check;
// em.out.println("Closest Theatres Are : ");
// e (itr.hasNext() && i < 2) {
// Theatres th = itr.next();
// heatre_id == 1 || th.theatre_id == 2) {
// k = 6;
// rintln("Theatre " + (i + 1) + " : " + th.name);
// ng, Shows> sh = th.shows;
// ry<String, Shows> entry : sh.entrySet()) {
// l = entry.getValue();
// ln("Movie Name : " + val.name);
// k < check; k++) {
//
//
//

// k = 3;
// rintln("Theatre " + (i + 1) + " : " + th.name);
// ng, Shows> sh = th.shows;
// ry<String, Shows> entry : sh.entrySet()) {
// l = entry.getValue();
// ln("Movie Name : " + val.name);
// k < check; k++) {
//
//
//
//

// rintln("Theatre " + (i + 1) + " : " + th.name);
// ng, Shows> sh = th.shows;
// ry<String, Shows> entry : sh.entrySet()) {
// l = entry.getValue();
// ln("Movie Name : " + val.name);
// k < check; k++) {
//
//
//
//
//
//

//
//
//
//
//

// double dlon = lon2Rad - lon1Rad;

// ouble distance = RADIUS_OF_EARTH_KM * c;
// rn distance;
//
