package mk.ukim.finki.napredno.predavanjeDemo.polymorphismDemo.Arrays;


public class Laptop implements Computer {
    private String model;
    private String processor;
    private String motherboard;
    private String gpu;
    private String type;

    public Laptop(String model, String processor, String motherboard, String gpu) {
        this.model = model;
        this.processor = processor;
        this.motherboard = motherboard;
        this.gpu = gpu;
        this.type = "Laptop";

    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public void setMotherboard(String motherboard) {
        this.motherboard = motherboard;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getModel() {
        return model;
    }

    @Override
    public String getProcessor() {
        return processor;
    }

    @Override
    public String getMotherboard() {
        return motherboard;
    }

    public String getGpu() {
        return gpu;
    }

    public String getType(){
        return this.type;
    }
    @Override
    public String toString() {
        return "Laptop{" +
                "model='" + model + '\'' +
                ", processor='" + processor + '\'' +
                ", motherboard='" + motherboard + '\'' +
                ", gpu='" + gpu + '\'' +
                '}';
    }
}
