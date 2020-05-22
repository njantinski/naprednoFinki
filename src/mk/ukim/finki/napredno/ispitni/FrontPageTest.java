package mk.ukim.finki.napredno.ispitni;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}


// Vasiot kod ovde
class CategoryNotFoundException extends Exception{
    String category;

    public CategoryNotFoundException(String category){
       this.category = category;
    }

    @Override
    public String getMessage(){
        return String.format("Category %s was not found",category) ;
    }
}

class ConvertDate{
    public static LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
        return Instant.ofEpochMilli( dateToConvert.getTime() )
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();
    }
}

class Category implements Comparable<Category>{
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public Category(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int compareTo(Category o) {
        return name.compareToIgnoreCase(o.getName());
    }
}

interface Teaser{
     String getTeaser();
}
abstract class NewsItem implements Teaser{
    private String title;
    private Date date;
    private Category category;

    public NewsItem(String title, Date date, Category category) {
        this.title = title;
        this.date = date;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }
    public static int getDaysOld(Date date){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = ConvertDate.convertToLocalDateViaInstant(date);

        return (int)ChronoUnit.MINUTES.between(localDateTime,now);
    }

}

class TextNewsItem extends NewsItem implements Teaser{

    private String textNews;

    public TextNewsItem(String title, Date date, Category category, String textNews) {
        super(title, date, category);
        this.textNews = textNews;
    }

    public String getTextNews() {
        return textNews;
    }

    @Override
    public String getTeaser() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTitle()).append("\n");
        int daysOld = NewsItem.getDaysOld(getDate());
        sb.append(daysOld).append("\n");
        String stringToAppend;
        if(textNews.length() > 80){
            stringToAppend = textNews.substring(0,80);
        }
        else{
            stringToAppend = textNews.substring(0,textNews.length());
        }

        sb.append(stringToAppend).append("\n");
        return sb.toString();
    }
}


class MediaNewsItem extends NewsItem implements Teaser {
    private String url;
    private int numVisits;


    public MediaNewsItem(String title, Date date, Category category, String url, int numVisits) {
        super(title, date, category);
        this.url = url;
        this.numVisits = numVisits;
    }

    public String getUrl() {
        return url;
    }

    public int getNumVisits() {
        return numVisits;
    }

    @Override
    public String getTeaser() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTitle()).append("\n");
        int daysOld = NewsItem.getDaysOld(getDate());
        sb.append(daysOld).append("\n");

        sb.append(url).append("\n").append(numVisits).append("\n");
        return sb.toString();
    }
}

class FrontPage{
    private Category[] categories;
    private List<NewsItem> newsItems;

    public FrontPage(Category[] categories) {
        this.categories = categories;
        newsItems = new ArrayList<>();
    }

    public void addNewsItem(NewsItem newsItem){
        this.newsItems.add(newsItem);
    }

    public List<NewsItem> listByCategory(Category category) {
        List<NewsItem> listToReturn =  newsItems.stream()
                .filter(news -> news.getCategory().equals(category)).collect(Collectors.toList());
        return listToReturn;
    }

    public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
        if(Arrays.stream(categories).filter(c -> category.equals(c.getName())).findAny().orElse(null) == null)
            throw new CategoryNotFoundException(category);

        List<NewsItem> listToReturn = newsItems.stream()
                .filter(item -> item.getCategory().getName().equals(category))
                .collect(Collectors.toList());


        return listToReturn;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        newsItems.forEach(item -> sb.append(item.getTeaser()));
        return sb.toString();
    }
}
