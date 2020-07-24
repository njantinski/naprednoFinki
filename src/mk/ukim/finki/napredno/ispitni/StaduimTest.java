package mk.ukim.finki.napredno.ispitni;

import java.util.*;

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}

class SeatNotAllowedException extends Exception{
    SeatNotAllowedException(){
        super();
    }
}

class SeatTakenException extends Exception{
    SeatTakenException(){
        super();
    }
}




class Seat{
    private int number;
    private int type;
    private boolean available;

    public Seat(int number, int type) {
        this.number = number;
        this.type = type;
        this.available = true;
    }

    public int getNumber() {
        return number;
    }

    public int getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void reserveSeat(int type){
        this.available = false;
        this.type = type;
    }


    public static Seat generateNewSeat(int number, int type){
        return new Seat(number, type);
    }
}


class Sector{
    private String code;
    private int seatsNum;
    private List<Seat> seats;

    public Sector(String code, int seatsNum) {
        this.code = code;
        this.seatsNum = seatsNum;
        seats = new ArrayList<>(seatsNum);
        fillSeats();
    }

    private void fillSeats() {
        for(int i = 0; i < seatsNum; i++){
            seats.add(Seat.generateNewSeat(i,0));
        }
    }

    public String toString(){
        return String.format("%s\t%s\t%.1f%%",getCode(),generateSeatsStatistics(),getPercentFree());
    }

    private double getPercentFree() {
        return 100.00 - getFreeSeats() / (double)seatsNum * 100;
    }

    private String generateSeatsStatistics(){
        int freeSeats = getFreeSeats();
        return freeSeats + "/" + seatsNum;
    }

    public int getFreeSeats(){
        return (int) seats.stream().filter(Seat::isAvailable).count();
    }

    public String getCode() {
        return this.code;
    }

    public void buySeat(int seatNum, int type) throws SeatTakenException, SeatNotAllowedException {
        if(!seats.get(seatNum - 1).isAvailable())
            throw new SeatTakenException();
        if(!checkType(type))
            throw new SeatNotAllowedException();

        seats.get(seatNum - 1).reserveSeat(type);
    }

    private boolean checkType(int type) {
        if(type == 0){
            return true;
        }
        int similarSeats = (int) seats.stream().filter(s -> s.getType() == 0 || s.getType() == type).count();
        if(similarSeats - seatsNum == 0)
            return true;
        return false;
    }

    public static Sector generateSector(String name, int seatsNum){
        return new Sector(name,seatsNum);
    }
}

class Stadium{
    private String name;
    private Map<String,Sector> sectors;

    public Stadium(String name){
        this.name = name;
        sectors = new HashMap<>();
    }

    public void createSectors(String[] sectorNames, int[] sizes){
        for(int i = 0; i < sectorNames.length; i++){
            sectors.put(sectorNames[i], Sector.generateSector(sectorNames[i],sizes[i]));
        }
    }

    public void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        Sector s = sectors.get(sectorName);
        s.buySeat(seat,type);
    }

    public void showSectors(){
        Set<Sector> sectorsForPrint = new TreeSet<>(Comparator.comparing(Sector::getFreeSeats).reversed().thenComparing(Sector::getCode));
        sectorsForPrint.addAll(sectors.values());
        sectorsForPrint.stream().forEach(System.out::println);
    }
}