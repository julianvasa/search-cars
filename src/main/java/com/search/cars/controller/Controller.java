package com.search.cars.controller;

import com.search.cars.domain.Car;
import com.search.cars.domain.Records;
import com.search.cars.exception.VehicleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@RestController
public class Controller {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(path = "/vin/{vin}", produces = "application/json")
    public Records getVehicleHistory(@PathVariable("vin") String vin) throws VehicleNotFoundException {
        try {
            /* Get records from the rest api data soure */
            Records records = restTemplate.getForObject(
                "https://s3-eu-west-1.amazonaws.com/coding-challenge.carfax.eu/" + vin, Records.class);
            List<Car> cars = Objects.requireNonNull(records).getCars();
            Integer lastOdometerReading = 0;

            /* Loop through all the cars to see if odometer reading is increasing,
             * If at some point it decreases set Odometer Rollback to true and break
             */
            for (Car car : cars) {
                if (car.getOdometer_reading() > lastOdometerReading) lastOdometerReading = car.getOdometer_reading();
                else {
                    car.setOdometer_rollback(true);
                    break;
                }
            }
            /* Set the new list of cards to the records object */
            records.setCars(cars);
            /* Auto serialized to JSON the records object */
            return records;
        } catch (Exception error) {
            error.printStackTrace();
            throw new VehicleNotFoundException(vin);
        }
    }
}
