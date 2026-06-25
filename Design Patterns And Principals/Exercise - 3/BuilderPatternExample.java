

public class BuilderPatternExample {
    static class Computer {
        
        private String CPU;
        private String GPU;
        private String RAM;

        private Computer(Builder builder) {
            this.CPU = builder.CPU;
            this.GPU = builder.GPU;
            this.RAM = builder.RAM;
        }
        public void display() {
            System.out.println("CPU: " + CPU);
            System.out.println("GPU: " + GPU);
            System.out.println("RAM: " + RAM);
        }
        public static class Builder {
            private String CPU;
            private String GPU;
            private String RAM;

            public Builder setCPU(String CPU) {
                this.CPU = CPU;
                return this;
            }

            public Builder setGPU(String GPU) {
                this.GPU = GPU;
                return this;
            }

            public Builder setRAM(String RAM) {
                this.RAM = RAM;
                return this;
            }

            public Computer build() {
                return new Computer(this);
            }
        }
    }

    public static void main(String[] args) {
        Computer gamingPC = new Computer.Builder()
                .setCPU("Intel i9")
                .setGPU("NVIDIA RTX 3080")
                .setRAM("32GB")
                .build();
        
        Computer officePC = new Computer.Builder()
                .setCPU("Intel i5")
                .setGPU("Integrated Graphics")
                .setRAM("16GB")
                .build();

        System.out.println("Gaming PC Configuration:");
        gamingPC.display();

        System.out.println("\nOffice PC Configuration:");
        officePC.display();
    }

    
}
