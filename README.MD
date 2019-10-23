# search-cars

Search cars by `vin`, all vehicle records are returned.<br>
If the odometer of some record decreases from the previous one, display field `ODOMETER_ROLLBACK`.

**Steps to follow to run:**
- `install lombok plugin in the IDE`
- `mvn clean package` 
- `mvn spring-boot run`
- `open http://localhost:8080/vin/VSSZZZ6JZ9R056308` search with a test vin


The project is ready to be deployed in Docker.<br>
Please change:
  - `docker.image.prefix` 
  - `docker.image.location` 

in pom.xml and run dockerfile plugin, it will generate an image file that can be deployed in Docker. Docker application should be running before running maven plugin dockerfile.    
    
  **To deploy in docker:**
  1. mvn dockerfile-build
  2. cd target/docker
  3. create search-cars repository on Docker first 
  4. docker push DOCKER_ACCOUNT/search-cars:latest (setup in pom.xml)

    
Example response:

```JSON
{
    records: [
        {
            vin: "VSSZZZ6JZ9R056308",
            date: "2017-01-02",
            data_provider_id: 10,
            odometer_reading: 10010,
            service_details: [
                "Oil changed",
                "Tires rotated"
            ]
        },
        {
            vin: "VSSZZZ6JZ9R056308",
            date: "2017-06-20",
            data_provider_id: 10,
            odometer_reading: 12100,
            service_details: [
              "Tires replaced"
            ]
        },
        {
            vin: "VSSZZZ6JZ9R056308",
            date: "2018-02-12",
            data_provider_id: 10,
            odometer_reading: 15100,
            service_details: [
              "Windshield replaced"
            ]
        },
        {
            vin: "VSSZZZ6JZ9R056308",
            date: "2018-04-01",
            data_provider_id: 10,
            odometer_reading: 5600,
            service_details: [
                "Air dam replaced",
                "Oil service"
            ],
            odometer_rollback: "Odometer Rollback"
        },
        {
            vin: "VSSZZZ6JZ9R056308",
            date: "2018-06-23",
            data_provider_id: 10,
            odometer_reading: 6400,
            service_details: [
                "Rear axle oil exchanged",
                "Engine oil pump repaired/replaced"
            ]
        }
    ]
}
