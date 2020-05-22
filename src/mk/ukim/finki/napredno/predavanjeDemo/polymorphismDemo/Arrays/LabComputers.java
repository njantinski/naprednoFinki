package mk.ukim.finki.napredno.predavanjeDemo.polymorphismDemo.Arrays;


import java.util.Arrays;
import java.util.stream.Collectors;

public class LabComputers {
    public static void main(String[] args) {
        Computer[] labPC = {
                new Laptop("Asus", "Intel i5", "Randy","intelGPU"),
                new Laptop("HP", "Intel i7", "msi", "NVIDIA 1080"),
                new Laptop("ACER", "INTEL I5", "msi", "NVIDIA X"),
                new Desktop("INTEL I3", "msi", "samsung 19\"", "nvidia gtx 1080", "CoolerMaster"),
                new Desktop("AMD RYZEN", "randy", "LG 121\"", "amd 2gb", "randy"),
                new Desktop("INTEL I9", "msi", "samsung 21\"", "nvidia gtx 1680","randy")
        };

       // Arrays.stream(labPC).collect(Collectors.toList()).forEach(System.out::println);


        Object[] labDesktops = Arrays.stream(labPC)
                .filter(c -> c.getType().equals("Desktop"))
                .map(c-> new Desktop((Desktop)c))
                .collect(Collectors.toList()).toArray();
        Desktop[] desks = new Desktop[labDesktops.length];
       for(int i = 0; i < labDesktops.length; i++){
           desks[i] = (Desktop) labDesktops[i];
       }
        Arrays.stream(desks).forEach(System.out::println);
    }
}
