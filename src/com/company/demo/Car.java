package com.company.demo;

import com.company.utils.Parsable;

import java.time.LocalDate;
import java.util.*;

public final class Car implements Parsable<Car> {

    // bendri duomenys visiems automobiliams (visai klasei)
    private static final int minYear = 2000;
    private static final int currentYear = LocalDate.now().getYear();
    private static final double minPrice = 100.0;
    private static final double maxPrice = 333000.0;
    private static final String idCode = "TA";   //  ***** nauja
    private static int serNr = 100;               //  ***** nauja

    private final String carRegNr;

    private String make = "";
    private String model = "";
    private int year = -1;
    private int mileage = -1;
    private double price = -1.0;

    public Car() {
        carRegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr
    }

    public Car(String make, String model, int year, int mileage, double price) {
        carRegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
        validate();
    }

    public Car(String dataString) {
        carRegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr
        this.parse(dataString);
        validate();
    }

    public Car(Builder builder) {
        carRegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr
        this.make = builder.make;
        this.model = builder.model;
        this.year = builder.year;
        this.mileage = builder.mileage;
        this.price = builder.price;
        validate();
    }

    public Car create(String dataString) {
        return new Car(dataString);
    }

    private void validate() {
        String errorType = "";
        if (year < minYear || year > currentYear) {
            errorType = "Netinkami gamybos metai, turi būti ["
                    + minYear + ":" + currentYear + "]";
        }
        if (price < minPrice || price > maxPrice) {
            errorType += " Kaina už leistinų ribų [" + minPrice
                    + ":" + maxPrice + "]";
        }

        if (!errorType.isEmpty()) {
            System.out.println("Automobilis yra blogai sugeneruotas: " + errorType + ". " + this);
        }
    }

    @Override
    public void parse(String dataString) {
        try {   // duomenys, atskirti tarpais
            Scanner scanner = new Scanner(dataString);
            make = scanner.next();
            model = scanner.next();
            year = scanner.nextInt();
            setMileage(scanner.nextInt());
            setPrice(scanner.nextDouble());
        } catch (InputMismatchException e) {
            System.out.println("Blogas duomenų formatas -> " + dataString);
        } catch (NoSuchElementException e) {
            System.out.println("Trūksta duomenų -> " + dataString);
        }
    }

    @Override
    public String toString() {  // papildyta su carRegNr
        return getCarRegNr() + "=" + make + "_" + model + ":" + year + " " + getMileage() + " " + String.format("%4.1f", price);
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCarRegNr() {  //** nauja.
        return carRegNr;
    }

    @Override
    public int compareTo(Car car) {
        return getCarRegNr().compareTo(car.getCarRegNr());
    }

    public static Comparator<Car> byMake = (Car c1, Car c2) -> c1.make.compareTo(c2.make); // pradžioje pagal markes, o po to pagal modelius

    public static Comparator<Car> byPrice = (Car c1, Car c2) -> {
        // didėjanti tvarka, pradedant nuo mažiausios
        if (c1.price < c2.price) {
            return -1;
        }
        if (c1.price > c2.price) {
            return +1;
        }
        return 0;
    };

    public static Comparator<Car> byYearPrice = (Car c1, Car c2) -> {
        // metai mažėjančia tvarka, esant vienodiems lyginama kaina
        if (c1.year > c2.year) {
            return +1;
        }
        if (c1.year < c2.year) {
            return -1;
        }
        if (c1.price > c2.price) {
            return +1;
        }
        if (c1.price < c2.price) {
            return -1;
        }
        return 0;
    };

    // Car klases objektų gamintojas (builder'is)
    public static class Builder {

        private final static Random RANDOM = new Random(1949);  // Atsitiktinių generatorius
        private final static String[][] MODELS = { // galimų automobilių markių ir jų modelių masyvas
                {"Mazda", "3", "6", "CX-3", "MX-5"},
                {"Ford", "Fiesta", "Kuga", "Focus", "Galaxy", "Mondeo"},
                {"VW", "Golf", "Jetta", "Passat", "Tiguan"},
                {"Honda", "HR-V", "CR-V", "Civic", "Jazz"},
                {"Renault", "Clio", "Megane", "Twingo", "Scenic"},
                {"Peugeot", "208", "308", "508", "Partner"},
                {"Audi", "A3", "A4", "A6", "A8", "Q3", "Q5"}
        };

        private String make = "";
        private String model = "";
        private int year = -1;
        private int mileage = -1;
        private double price = -1.0;

        public Car build() {
            return new Car(this);
        }

        public Car buildRandom() {
            int ma = RANDOM.nextInt(MODELS.length);        // markės indeksas  0..
            int mo = RANDOM.nextInt(MODELS[ma].length - 1) + 1;// modelio indeksas 1..
            return new Car(MODELS[ma][0],
                    MODELS[ma][mo],
                    2000 + RANDOM.nextInt(22),// metai tarp 2000 ir 2021
                    6000 + RANDOM.nextInt(222000),// rida tarp 6000 ir 228000
                    800 + RANDOM.nextDouble() * 88000);// kaina tarp 800 ir 88800
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder make(String make) {
            this.make = make;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder mileage(int mileage) {
            this.mileage = mileage;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }
    }
}
