package com.exemple.carstore;

import com.exemple.carstore.model.*;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static ArrayList<Car> cars = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();
    static ArrayList<Employee> employees = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        initData();
        String carInfo = String.format("Загальна кількість проданих машин %d  на суму %f",
                getCountOfSoldCars(), getAllPriceOfSoldCars());
        System.out.println(carInfo);

        System.out.println("--------------------------------------------------------------");

        for (Employee employee : employees){
            System.out.println(employee.getName() + " продав(ла)" + getProfitByEmployee(employee.getId()).toString());
        }
        System.out.println("--------------------------------------------------------------");
        for (Customer customer : customers){
            System.out.println(customer.getName() + " купив(ла)" + getProfitByCustomer(customer.getId()).toString());
        }

        System.out.println("--------------------------------------------------------------");

        ArrayList<CarAddition> soldCarCount = getCountSoldByModel();
        HashMap<CarModel, Double> soldCarPrice = getPriceByOfSoldCarByModel();

        String soldCarStr = "По моделі: %s продано %d машин загальною вартістю %f ";

        for(CarAddition carAddition : soldCarCount){
            double price = soldCarPrice.get(carAddition.getModel());
            System.out.println(
                    String.format(
                            soldCarStr,
                            carAddition.getModel().name(),
                            carAddition.getCount(),
                            price
                    )
            );
        }
    }

    // кількість проданих машин по моделях ( РОБОТА З ArrayList )
    public static ArrayList<CarAddition> getCountSoldByModel(){
        ArrayList<CarAddition> result = new ArrayList<>();

        int countSportCar = 0, countSedan = 0, countUniversal = 0;
        for (Order order : orders){
            countSportCar += getCountByModel(order, CarModel.SportCar);
            countSedan += getCountByModel(order, CarModel.Sedan);
            countUniversal += getCountByModel(order, CarModel.Universal);
        }
        result.add(new CarAddition(CarModel.SportCar, countSportCar));
        result.add(new CarAddition(CarModel.Sedan, countSedan));
        result.add(new CarAddition(CarModel.SportCar, countUniversal));

        return result;
    }

    /**
     * Кількість машини в одному замовленні по моделі
     * @param order замовлення
     * @param model модель
     * @return кількість машин
     */
    public static int getCountByModel(Order order, CarModel model){
        int count = 0;
        for (long carId : order.getCars()) {
            Car car = getCarById(carId);
            if(car != null )
                count ++;
        }
        return count;
    }

    // Вартість проданих машин по моделях ( РОБОТА З HashMap )
    public static HashMap<CarModel, Double> getPriceByOfSoldCarByModel() {
        HashMap<CarModel, Double> result = new HashMap<>();
        double priceSportCar = 0, priceSedan = 0, priceUniversal = 0;
        for (Order order : orders){
            priceSportCar += getPriceByModel(order, CarModel.SportCar);
            priceSedan += getPriceByModel(order, CarModel.Sedan);
            priceUniversal += getPriceByModel(order, CarModel.Universal);
        }
        result.put(CarModel.SportCar, priceSportCar);
        result.put(CarModel.Sedan, priceSedan);
        result.put(CarModel.Universal, priceUniversal);

        return result;
    }

    /**
     * Вартість машини в одному замовленні по моделі
     * @param order замовлення
     * @param model модель
     * @return вартість машини
     */
    public static double getPriceByModel(Order order, CarModel model){
        double price = 0;
        for (long carId : order.getCars()) {
            Car car = getCarById(carId);
            if(car != null /*&& car.getModel() == model */)
                price += car.getPrice();
        }
        return price;
    }

    /**
     * Отримати загальну кількість і суму проданих машин
     * @param employeeId Унікальний номер продавця
     * @return загальну кількість і суму вказаного продавця
     */
    public static Profit getProfitByEmployee(long employeeId){
        int count = 0;
        double price = 0;
        for(Order order : orders){
            if(order.getEmployeeId() == employeeId){
                price += getPriceOfSoldCarsInOrder(order);
                count += order.getCars().length;
            }
        }
        return new Profit(count, price);
    }


    /**
     * Отримати загальну кількість і суму куплених машин
     * @param customerId Унікальний номер покупця
     * @return загальну кількість і суму вказаного покупця
     */
    public static ProfitCustomer getProfitByCustomer(long customerId){
        int count = 0;
        double price = 0;
        for(Order order : orders){
            if(order.getEmployeeId() == customerId){
                price += getPriceOfSoldCarsInOrder(order);
                count += order.getCars().length;
            }
        }
        return new ProfitCustomer(count, price);
    }

    // Загальна вартість ВСІХ замовлень
    public static double getAllPriceOfSoldCars(){
        double price = 0;
        for(Order order : orders){
            price += getPriceOfSoldCarsInOrder(order);
        }
        return price;
    }


    /**
     * Вартість ОДНОГО замовлення
     * @param order замовлення
     * @return загальна вартість всіх проданих машин в замовленні
     */
    public static double getPriceOfSoldCarsInOrder(Order order){
        double price = 0;
        for (long carId : order.getCars()){
            Car car = getCarById(carId);
            if(car != null)
                price += car.getPrice();
        }
        return price;
    }

    // загальна кількість проданих машин
    public static int getCountOfSoldCars(){
        int count = 0;

        for(Order order : orders){
            count += order.getCars().length;
        }
        return count;
    }

    /**
     * Пошук машини по її унікальному номеру
     * @param id унікальний номер машини
     * @return знайдена машина
     */
    public static Car getCarById(long id){
        Car current = null;

        for (Car car : cars){
            if(car.getId() == id){
                current = car;
            }
        }
            return current;
    }



    public static void initData(){
        // ПРОДАВЦІ
        employees.add(new Employee(1, "Mark", 27));
        employees.add(new Employee(2, "Bob", 32));
        employees.add(new Employee(3, "Den", 33));

        // ПОКУПЦІ
        customers.add(new Customer(1, "Yurii", 21));
        customers.add(new Customer(2, "Alex", 45));
        customers.add(new Customer(3, "Olya", 24));
        customers.add(new Customer(4, "Taras", 19));

        // МАШИНИ
        cars.add(new Car(1, "BMW", 26000, CarModel.SportCar));
        cars.add(new Car(2, "BMW x7", 50000, CarModel.SportCar));
        cars.add(new Car(3, "Volkswagen", 15000, CarModel.Universal));
        cars.add(new Car(4, "Volkswagen", 20000, CarModel.Sedan));
        cars.add(new Car(5, "Porsche", 29000, CarModel.SportCar));
        cars.add(new Car(6, "Mercedes", 32500, CarModel.Sedan));
        cars.add(new Car(7, "KIA", 26000, CarModel.Universal));

        // ПОКУПКИ
        orders.add(new Order(1, 1, 2, new long[]{3,2,1}));
        orders.add(new Order(2, 1, 1, new long[]{7,2}));
        orders.add(new Order(3, 3, 2, new long[]{1,2}));
        orders.add(new Order(4, 2, 3, new long[]{6}));
    }
}
