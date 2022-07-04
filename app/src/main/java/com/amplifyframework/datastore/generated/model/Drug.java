package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Drug type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Drugs")
public final class Drug implements Model {
  public static final QueryField ID = field("Drug", "id");
  public static final QueryField NAME = field("Drug", "name");
  public static final QueryField START_DATE = field("Drug", "start_date");
  public static final QueryField END_DATE = field("Drug", "end_date");
  public static final QueryField NUM_OF_TIMES = field("Drug", "NumOfTimes");
  public static final QueryField SPECIFIC_TIME = field("Drug", "SpecificTime");
  public static final QueryField USER_ID = field("Drug", "userId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String start_date;
  private final @ModelField(targetType="String") String end_date;
  private final @ModelField(targetType="String") String NumOfTimes;
  private final @ModelField(targetType="String") String SpecificTime;
  private final @ModelField(targetType="String", isRequired = true) String userId;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getStartDate() {
      return start_date;
  }
  
  public String getEndDate() {
      return end_date;
  }
  
  public String getNumOfTimes() {
      return NumOfTimes;
  }
  
  public String getSpecificTime() {
      return SpecificTime;
  }
  
  public String getUserId() {
      return userId;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Drug(String id, String name, String start_date, String end_date, String NumOfTimes, String SpecificTime, String userId) {
    this.id = id;
    this.name = name;
    this.start_date = start_date;
    this.end_date = end_date;
    this.NumOfTimes = NumOfTimes;
    this.SpecificTime = SpecificTime;
    this.userId = userId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Drug drug = (Drug) obj;
      return ObjectsCompat.equals(getId(), drug.getId()) &&
              ObjectsCompat.equals(getName(), drug.getName()) &&
              ObjectsCompat.equals(getStartDate(), drug.getStartDate()) &&
              ObjectsCompat.equals(getEndDate(), drug.getEndDate()) &&
              ObjectsCompat.equals(getNumOfTimes(), drug.getNumOfTimes()) &&
              ObjectsCompat.equals(getSpecificTime(), drug.getSpecificTime()) &&
              ObjectsCompat.equals(getUserId(), drug.getUserId()) &&
              ObjectsCompat.equals(getCreatedAt(), drug.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), drug.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getStartDate())
      .append(getEndDate())
      .append(getNumOfTimes())
      .append(getSpecificTime())
      .append(getUserId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Drug {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("start_date=" + String.valueOf(getStartDate()) + ", ")
      .append("end_date=" + String.valueOf(getEndDate()) + ", ")
      .append("NumOfTimes=" + String.valueOf(getNumOfTimes()) + ", ")
      .append("SpecificTime=" + String.valueOf(getSpecificTime()) + ", ")
      .append("userId=" + String.valueOf(getUserId()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Drug justId(String id) {
    return new Drug(
      id,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      start_date,
      end_date,
      NumOfTimes,
      SpecificTime,
      userId);
  }
  public interface NameStep {
    UserIdStep name(String name);
  }
  

  public interface UserIdStep {
    BuildStep userId(String userId);
  }
  

  public interface BuildStep {
    Drug build();
    BuildStep id(String id);
    BuildStep startDate(String startDate);
    BuildStep endDate(String endDate);
    BuildStep numOfTimes(String numOfTimes);
    BuildStep specificTime(String specificTime);
  }
  

  public static class Builder implements NameStep, UserIdStep, BuildStep {
    private String id;
    private String name;
    private String userId;
    private String start_date;
    private String end_date;
    private String NumOfTimes;
    private String SpecificTime;
    @Override
     public Drug build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Drug(
          id,
          name,
          start_date,
          end_date,
          NumOfTimes,
          SpecificTime,
          userId);
    }
    
    @Override
     public UserIdStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userId = userId;
        return this;
    }
    
    @Override
     public BuildStep startDate(String startDate) {
        this.start_date = startDate;
        return this;
    }
    
    @Override
     public BuildStep endDate(String endDate) {
        this.end_date = endDate;
        return this;
    }
    
    @Override
     public BuildStep numOfTimes(String numOfTimes) {
        this.NumOfTimes = numOfTimes;
        return this;
    }
    
    @Override
     public BuildStep specificTime(String specificTime) {
        this.SpecificTime = specificTime;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String startDate, String endDate, String numOfTimes, String specificTime, String userId) {
      super.id(id);
      super.name(name)
        .userId(userId)
        .startDate(startDate)
        .endDate(endDate)
        .numOfTimes(numOfTimes)
        .specificTime(specificTime);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder startDate(String startDate) {
      return (CopyOfBuilder) super.startDate(startDate);
    }
    
    @Override
     public CopyOfBuilder endDate(String endDate) {
      return (CopyOfBuilder) super.endDate(endDate);
    }
    
    @Override
     public CopyOfBuilder numOfTimes(String numOfTimes) {
      return (CopyOfBuilder) super.numOfTimes(numOfTimes);
    }
    
    @Override
     public CopyOfBuilder specificTime(String specificTime) {
      return (CopyOfBuilder) super.specificTime(specificTime);
    }
  }
  
}
