
package mk.ukim.finki.napredno.ispitni;

import java.util.*;
import java.util.stream.Collectors;

public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}

// vashiot kod ovde


class Airport{
    private String name;
    private String country;
    private String code;
    private int passengers;

    public Airport(String name, String country, String code, int passengers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passengers = passengers;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCode() {
        return code;
    }

    public int getPassengers() {
        return passengers;
    }

    public static Airport createAirport(String name, String country, String code, int passengers){
        return new Airport(name,country,code,passengers);
    }

}

class Flight{
    private String from;
    private String to;
    private int time;
    private int duration;

    public Flight(String from, String to, int time, int duration) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.duration = duration;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    public static Flight createNewFlight(String from, String to, int time, int duration){
        return new Flight(from, to, time,duration);
    }

    public String getDurationInString(){
        int minutes = duration%60;
        int hours = duration/60;
        if(hours < 24) {
            return String.format("%2dh%02dm", hours, minutes);
        }
        else{
            int days = hours/24;
            hours = hours%24;
            return String.format("+%dd%2dh%02dm", days,hours, minutes);
            }
        }

    public String getStartTime(){
        int minutes = time%60;
        int hours = time/60;
        return String.format("%02d:%02d",hours,minutes);
    }

    public String getEndTime(){
        int newTime = time + duration;
        int minutes = newTime%60;
        int hours = newTime/60;
        if(hours < 24){
            return String.format("%02d:%02d",hours,minutes);
        }
        else{
            hours = hours%24;
            return String.format("%02d:%02d +1d",hours,minutes);
        }
    }
    public String toString(){
        return String.format("%s-%s %s-%s%s",getFrom(),getTo(),getStartTime(),getEndTime(),getDurationInString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return time == flight.time &&
                duration == flight.duration &&
                from.equals(flight.from) &&
                to.equals(flight.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, time, duration);
    }
}

class Airports{
    private Map<String, Set<Flight>> flightsByAirport;
    private Map<String,Set<Flight>> flightsFrom;
    private Map<String,Airport> airports;
    private Map<String, Set<Flight>> flightsToWithCode;
    private Set<Flight> allFlights;

    private Comparator<Flight> compareByTime = Comparator.comparing(Flight::getTime);

    public Airports(){
        flightsFrom = new HashMap<>();
        airports = new HashMap<>();
        flightsByAirport = new HashMap<>();
        flightsToWithCode= new HashMap<>();
        allFlights = new HashSet<>();
    }
    public void addAirport(String name, String country, String code, int passengers){
        Airport newAirport = Airport.createAirport(name,country,code,passengers);
        Set<Flight> flights = new HashSet<>();
        airports.put(newAirport.getCode(),newAirport);
        flightsByAirport.put(newAirport.getCode(), flights);
    }

    public void addFlights(String from, String to, int time, int duration) {
        Flight newFlight = Flight.createNewFlight(from, to, time, duration);

        Set<Flight> flightByAirportsSet = flightsByAirport.computeIfAbsent(from, value -> new HashSet<>());
        flightByAirportsSet.add(newFlight);

        Set<Flight> flightsFromSet = flightsFrom.computeIfAbsent(from, value -> new HashSet<>());
        flightsFromSet.add(newFlight);

        flightByAirportsSet = flightsByAirport.computeIfAbsent(to, value -> new HashSet<>());
        flightByAirportsSet.add(newFlight);

        flightByAirportsSet = flightsToWithCode.computeIfAbsent(newFlight.getTo(),value -> new HashSet<>());
        flightByAirportsSet.add(newFlight);

        allFlights.add(newFlight);

    }


    public void showFlightsFromAirport(String code){
        Set<Flight> fromAirport = new TreeSet<>(Comparator.comparing(Flight::getTo)
                .thenComparing(Comparator.comparing(Flight::getStartTime)));
        Set<Flight> toAirport = new TreeSet<>(Comparator.comparing(Flight::getTime));

        fromAirport.addAll(flightsFrom.get(code));
        toAirport.addAll(flightsToWithCode.get(code));

        print(code,fromAirport,toAirport);
    }

    private void print(String code, Set<Flight> byAirport, Set<Flight> toAirport) {
        System.out.println(airports.get(code).getName() + " (" + code +")");
        System.out.println(airports.get(code).getCountry());
        System.out.println(airports.get(code).getPassengers());
        int counter = 1;
        for(Flight f : byAirport){
            System.out.println(String.format("%d. %s",counter,f.toString()));
            counter++;
        }
    }

    public void showDirectFlightsFromTo(String from, String to){
            Set<Flight> directFlights = allFlights.stream()
                    .filter(f -> f.getFrom().equals(from)).filter(f -> f.getTo().equals(to))
                    .collect(Collectors.toCollection(() -> new TreeSet<>(compareByTime)));
            if(directFlights.size() == 0){
                System.out.println(String.format("No flights from %s to %s",from,to));
            }
            else {
                directFlights.stream().forEach(System.out::println);

            }
    }

    public void showDirectFlightsTo(String to){
        Set<Flight> toSet = new TreeSet<>(compareByTime);
        toSet.addAll(flightsToWithCode.get(to));
        toSet.stream().forEach(System.out::println);
    }
}
















