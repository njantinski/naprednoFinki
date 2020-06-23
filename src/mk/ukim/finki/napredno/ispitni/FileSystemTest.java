/*
package mk.ukim.finki.napredno.ispitni;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;




public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}

// Your code here

class File{
    private String name;
    private Integer size;
    private LocalDateTime localDateTime;

    public File(String name, Integer size, LocalDateTime localDateTime) {
        this.name = name;
        this.size = size;
        this.localDateTime = localDateTime;
    }

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
    public int getYear(){
        return localDateTime.getYear();
    }
    public String toString(){
        return String.format("%-10s %5dB %s",name,size,localDateTime.toString());
    }
}


class FileSystem{
    Map<Character, Set<File>> folders;

    private static final Comparator<File> comparator = Comparator.comparing(File::getYear).thenComparing(File::getName).thenComparing(File::getSize);

    public FileSystem() {
        folders = new TreeMap<>();

    }

    public void addFile(char folder, String name, int size, LocalDateTime createdAt){
        File fileToPut = new File(name,size,createdAt);
        folders.computeIfAbsent(folder, key ->{
            Set<File> filesInFolder = new TreeSet<>(comparator);
            filesInFolder.add(fileToPut);
            return filesInFolder;
        });
        folders.get(folder).add(fileToPut);
    }

    public List<File> findAllHiddenFilesWithSizeLessThen(int size){
        return folders.values().stream().flatMap(x -> x.stream().filter(y -> y.getName().startsWith(".")&&y.getSize() < size)).collect(Collectors.toList());
    }

    public int totalSizeOfFilesFromFolders(List<Character> folders){
        return folders.stream().mapToInt(f -> this.folders.get(f).stream().mapToInt(fol -> fol.getSize()).sum()).sum();
    }
    public Map<Integer, Set<File>> byYear(){
      return folders.values().stream().flatMap(Collection::stream)
              .collect(Collectors
                      .groupingBy(File::getYear,Collectors
                              .toCollection(() -> new TreeSet<>( Comparator.comparing(File::getYear).thenComparing(File::getName).thenComparing(File::getSize)))));
    }

    public Map<String, Long> sizeByMonthAndDay(){
        return null;
    }
}
*/
