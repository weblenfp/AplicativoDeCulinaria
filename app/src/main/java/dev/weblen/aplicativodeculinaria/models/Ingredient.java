package dev.weblen.aplicativodeculinaria.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "quantity",
        "measure",
        "ingredient"
})
public class Ingredient implements Parcelable {

    public final static Parcelable.Creator<Ingredient> CREATOR              = new Creator<Ingredient>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return (new Ingredient[size]);
        }

    };
    @JsonProperty("quantity")
    private             float                          quantity;
    @JsonProperty("measure")
    private             String                         measure;
    @JsonProperty("ingredient")
    private             String                         ingredient;
    @JsonIgnore
    private             Map<String, Object>            additionalProperties = new HashMap<String, Object>();

    protected Ingredient(Parcel in) {
        this.quantity = ((float) in.readValue((float.class.getClassLoader())));
        this.measure = ((String) in.readValue((String.class.getClassLoader())));
        this.ingredient = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object>) in.readValue((Map.class.getClassLoader())));
    }

    public Ingredient() {
    }

    @JsonProperty("quantity")
    public float getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("measure")
    public String getMeasure() {
        return measure;
    }

    @JsonProperty("measure")
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @JsonProperty("ingredient")
    public String getIngredient() {
        return ingredient;
    }

    @JsonProperty("ingredient")
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(quantity);
        dest.writeValue(measure);
        dest.writeValue(ingredient);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return 0;
    }

}