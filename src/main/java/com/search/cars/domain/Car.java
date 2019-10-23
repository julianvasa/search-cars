package com.search.cars.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.IOException;
import java.util.List;

// Constructors, Getters, Setters created by lombok
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Car implements Cloneable, Comparable {
    private String vin;
    private String date;
    private Integer data_provider_id;
    private Integer odometer_reading;
    private List<String> service_details;

    // If event is false/null do not display in the JSON
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonSerialize(using=StringBooleanSerializer.class)
    @JsonDeserialize(using=StringBooleanDeserializer.class)
    private Boolean odometer_rollback;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();
        Car car = new Car();
        car.setVin(this.vin);
        car.setDate(this.date);
        car.setData_provider_id(this.data_provider_id);
        car.setOdometer_reading(this.odometer_reading);
        car.setService_details(this.service_details);
        return car;
    }

    @Override
    public int compareTo(Object carToCompare) {
        return CharSequence.compare(this.vin, ((Car) carToCompare).getVin()) &
            CharSequence.compare(this.vin, ((Car) carToCompare).getVin()) &
            Integer.compare(this.data_provider_id, ((Car) carToCompare).getData_provider_id()) &
            Integer.compare(this.odometer_reading, ((Car) carToCompare).getOdometer_reading()) &
            (Integer.compare(this.service_details.size(),((Car) carToCompare).getService_details().size()))
        ;
    }

    @Override
    public boolean equals(Object carToCompare) {
        // If the object is compared with itself then return true
        if (carToCompare == this) {
            return true;
        }

        /* Check if carToCompare is an instance of Product or not
          "null instanceof [type]" also returns false */
        if (!(carToCompare instanceof Car)) {
            return false;
        }

        // typecast carToCompare to Complex so that we can compare data members
        Car car = (Car) carToCompare;

        // Compare the data members and return accordingly
        return CharSequence.compare(this.vin, ((Car) carToCompare).getVin()) == 0
            && CharSequence.compare(this.vin, ((Car) carToCompare).getVin()) == 0
            && this.data_provider_id == ((Car) carToCompare).getData_provider_id()
            && this.service_details.size() == ((Car) carToCompare).getService_details().size();
    }
}

class StringBooleanSerializer extends JsonSerializer<Boolean> {

    // Custom Serializer, if odometer_rollback = false return null else return "Odometer Rollback"
    @Override
    public void serialize(Boolean bool, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(bool ? "Odometer Rollback" : null);
    }
}

class StringBooleanDeserializer extends JsonDeserializer<Boolean> {

    // Custom Deserializer, if odometer_rollback = "Odometer Rollback" return true else return false
    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return jsonParser.getValueAsString().equals("Odometer Rollback");
    }
}
