package mk.ukim.finki.napredno.ispitni;



    import java.util.*;
    import java.util.stream.Collectors;

public class NamesTest {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            scanner.nextLine();
            Names names = new Names();
            for (int i = 0; i < n; ++i) {
                String name = scanner.nextLine();
                names.addName(name);
            }
            n = scanner.nextInt();
            System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
            names.printN(n);
            System.out.println("===== FIND NAME =====");
            int len = scanner.nextInt();
            int index = scanner.nextInt();
            System.out.println(names.findName(len, index));
            scanner.close();

        }
    }

    // vashiot kod ovde

    class Name{
        private String name;
        private int count;
        private int uniqueLetters;

        public Name(String name) {
            this.name = name;
            this.count = 1;
            this.uniqueLetters = getUniqueLetters();
        }

        public static Name createNewName(String name) {
            return new Name(name);
        }

        public void incrementCount(){
            this.count++;
        }

        public String getName() {
            return name;
        }

        public int getCount() {
            return count;
        }

        private int getUniqueLetters(){
            Set<Character> uniqueLetters = new HashSet<>();
            for(Character c : name.toCharArray()){
                uniqueLetters.add(Character.toLowerCase(c));
            }
            return uniqueLetters.size();
        }

        public int getLetters(){
            return this.uniqueLetters;
        }

        public String toString(){
            return String.format("%s (%d) %d",this.name, this.count, this.uniqueLetters);
        }
    }

    class Names{
        private Map<String,Name> names;

        public Names() {
            names = new HashMap<>();
        }

        public void addName(String name){
            if(names.containsKey(name)){
                names.get(name).incrementCount();
            }
            else{
                names.put(name, Name.createNewName(name));
            }
        }

        public void printN(int n){
            names.values().stream().filter(f -> f.getCount() >= n).sorted(Comparator.comparing(Name::getName)).forEach(System.out::println);
        }

        public String findName(int len, int x){
            List<String> namesToRemove = names.values().stream().filter(v -> v.getName().length() >= len).map(n -> n.getName()).collect(Collectors.toList());
            namesToRemove.stream().forEach(n -> names.remove(n));
            List<Name> namesList = names.values().stream().sorted(Comparator.comparing(Name::getName)).collect(Collectors.toList());

            if(x > namesList.size()){
                x = x%namesList.size();
            }

            return namesList.get(x).getName();
        }

    }