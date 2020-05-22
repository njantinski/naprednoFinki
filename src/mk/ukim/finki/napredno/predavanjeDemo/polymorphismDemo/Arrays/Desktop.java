package mk.ukim.finki.napredno.predavanjeDemo.polymorphismDemo.Arrays;

public class Desktop implements Computer {
    private String processor;
    private String motherboard;
    private String monitor;
    private String gpu;
    private String compCase;
    private String type;


    public Desktop(String processor, String motherboard, String monitor, String gpu, String compCase) {
        this.processor = processor;
        this.motherboard = motherboard;
        this.monitor = monitor;
        this.gpu = gpu;
        this.compCase = compCase;
        this.type = "Desktop";
    }

    public Desktop(Desktop c) {
        this(c.getProcessor(),c.getMotherboard(),c.getMonitor(),c.getGpu(),c.getCompCase());
    }

    @Override
    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    @Override
    public String getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(String motherboard) {
        this.motherboard = motherboard;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getCompCase() {
        return compCase;
    }

    public void setCompCase(String compCase) {
        this.compCase = compCase;
    }

    public String getType(){
        return this.type;
    }

    @Override
    public String toString() {
        return "Desktop{" +
                "processor='" + processor + '\'' +
                ", motherboard='" + motherboard + '\'' +
                ", monitor='" + monitor + '\'' +
                ", gpu='" + gpu + '\'' +
                ", compCase='" + compCase + '\'' +
                '}';
    }
}
