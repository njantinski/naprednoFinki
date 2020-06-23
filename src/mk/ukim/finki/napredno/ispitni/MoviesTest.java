package mk.ukim.finki.napredno.ispitni;
import java.util.*;
import java.util.stream.IntStream;

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

class Movie{
    private String title;
    private int[] ratings;
    private double average;
    private double coef;
    private int numRatings;

    public Movie(String title, int[] ratings) {
        this.title = title;
        this.ratings = Arrays.copyOf(ratings,ratings.length);
        average = getAverage(ratings);
        numRatings = ratings.length;
    }

    public static Movie createNewMovie(String title, int[] ratings){
        return new Movie(title, ratings);
    }
    public String toString(){
        return String.format("%s (%.2f) of %d ratings",title,average,ratings.length);
    }

    private double getAverage(int [] ratings){
        DoubleSummaryStatistics doubleSummaryStatistics = new DoubleSummaryStatistics();
        Arrays.stream(ratings).forEach(doubleSummaryStatistics::accept);
        return doubleSummaryStatistics.getAverage();
    }

    public double getRating(){
        return average;
    }

    public double getCoef() {
        return coef;
    }

    public void setCoef(int maxRatings) {
        this.coef = (average * ratings.length ) / maxRatings;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public String getTitle() {
        return title;
    }
}

class MoviesList {
    List<Movie> movieList;
    List<Movie> byRating;
    List<Movie> byCoef;

    private static final Comparator<Movie> compareByRating = Comparator.comparing(Movie::getRating).reversed().thenComparing(Movie::getTitle);


    public MoviesList(){
        movieList = new ArrayList<>();
        byRating = new ArrayList<>();
        byCoef = new ArrayList<>();
    }

    public void addMovie(String title, int[] ratings){
        movieList.add(Movie.createNewMovie(title,ratings));
        byRating.add(Movie.createNewMovie(title,ratings));
        byCoef.add(Movie.createNewMovie(title,ratings));
    }

    public List<Movie> top10ByAvgRating(){
            byRating.sort(compareByRating);
            if(byRating.size() < 10){
                return byRating;
            }
            return byRating.subList(0,10);
    }


    //просечен ретјтинг на филмот x вкупно број на рејтинзи на филмот / максимален број на рејтинзи (од сите филмови во листата)
    public List<Movie> top10ByRatingCoef(){
       int maxRatings = byCoef.stream().mapToInt(movie -> movie.getNumRatings()).sum();
       byCoef.stream().forEach(movie -> movie.setCoef(maxRatings));
       byCoef.sort(Comparator.comparing(Movie::getCoef).reversed().thenComparing(Movie::getNumRatings).thenComparing(Movie::getTitle));
       if(byCoef.size() < 10)
           return byCoef;
       else{
           return byCoef.subList(0,10);
       }
    }
}