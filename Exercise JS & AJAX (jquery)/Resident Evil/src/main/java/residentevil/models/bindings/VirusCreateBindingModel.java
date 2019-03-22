package residentevil.models.bindings;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VirusCreateBindingModel {

    private String name;
    private String description;
    private String sideEffects;
    private String creator;
    private boolean isDeadly;
    private boolean isCurable;
    private String mutation;
    private Integer turnoverRate;
    private Integer hoursUntilTurn;
    private String magnitude;
    private LocalDate releasedOn;
    private List<String> capitals;

    public VirusCreateBindingModel() {
        this.capitals = new ArrayList<>();
    }

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 10, message = "Name must be in range [3 - 10] symbols.")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Description cannot be null")
    @Size(min = 5, max = 100, message = "Description must be in range [5 - 100] symbols.")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Site Effect cannot be null")
    @NotEmpty(message = "Side Effect cannot be empty.")
    @Length(max = 50, message = "Side effects must be in range [0 - 50] symbols.")
    public String getSideEffects() {
        return this.sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    @NotNull(message = "Creator cannot be null")
    @NotEmpty(message = "Creator cannot be empty.")
    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isDeadly() {
        return this.isDeadly;
    }

    public void setDeadly(boolean deadly) {
        isDeadly = deadly;
    }

    public boolean isCurable() {
        return this.isCurable;
    }

    public void setCurable(boolean curable) {
        isCurable = curable;
    }

    @NotNull(message = "Mutation cannot be null")
    @NotEmpty(message = "Mutation cannot be empty.")
    public String getMutation() {
        return this.mutation;
    }

    public void setMutation(String mutation) {
        this.mutation = mutation;
    }

    @NotNull(message = "Rate cannot be null")
    @Min(value = 0, message = "Turnover rate min value must be 0.")
    @Max(value = 100, message = "Turnover rate max value must be 100.")
    public Integer getTurnoverRate() {
        return this.turnoverRate;
    }

    public void setTurnoverRate(Integer turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    @NotNull(message = "Hours cannot be null")
    @Min(value = 1, message = "Hours until turn mutation min value must be 1.")
    @Max(value = 12, message = "Hours until turn mutation max value must be 12.")
    public Integer getHoursUntilTurn() {
        return this.hoursUntilTurn;
    }

    public void setHoursUntilTurn(Integer hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    @NotNull(message = "Magnitude cannot be null")
    @NotEmpty(message = "Magnitude cannot be empty.")
    public String getMagnitude() {
        return this.magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    @NotNull(message = "Date cannot be null.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getReleasedOn() {
        return this.releasedOn;
    }

    public void setReleasedOn(LocalDate releasedOn) {
        this.releasedOn = releasedOn;
    }

    @NotNull(message = "Capitals cannot be null")
    @NotEmpty(message = "You must select capitals")
    public List<String> getCapitals() {
        return this.capitals;
    }

    public void setCapitals(List<String> capitals) {
        this.capitals = capitals;
    }
}
