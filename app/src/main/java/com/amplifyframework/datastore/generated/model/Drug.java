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
  public static final QueryField DATA = field("Drug", "data");
  public static final QueryField NUM_OF_TIMES = field("Drug", "NumOfTimes");
  public static final QueryField SPECIFIC_TIME = field("Drug", "SpecificTime");
  public static final QueryField USER_ID = field("Drug", "userId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String data;
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
  
  public String getData() {
      return data;
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
  
  private Drug(String id, String name, String data, String NumOfTimes, String SpecificTime, String userId) {
    this.id = id;
    this.name = name;
    this.data = data;
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
              ObjectsCompat.equals(getData(), drug.getData()) &&
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
      .append(getData())
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
      .append("data=" + String.valueOf(getData()) + ", ")
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      data,
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
    BuildStep data(String data);
    BuildStep numOfTimes(String numOfTimes);
    BuildStep specificTime(String specificTime);
  }
  

  public static class Builder implements NameStep, UserIdStep, BuildStep {
    private String id;
    private String name;
    private String userId;
    private String data;
    private String NumOfTimes;
    private String SpecificTime;
    @Override
     public Drug build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Drug(
          id,
          name,
          data,
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
     public BuildStep data(String data) {
        this.data = data;
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
    private CopyOfBuilder(String id, String name, String data, String numOfTimes, String specificTime, String userId) {
      super.id(id);
      super.name(name)
        .userId(userId)
        .data(data)
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
     public CopyOfBuilder data(String data) {
      return (CopyOfBuilder) super.data(data);
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
