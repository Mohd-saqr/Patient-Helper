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

/** This is an auto generated class representing the Post type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Posts")
public final class Post implements Model {
  public static final QueryField ID = field("Post", "id");
  public static final QueryField BODY = field("Post", "body");
  public static final QueryField USERNAME = field("Post", "username");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String body;
  private final @ModelField(targetType="String") String username;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getBody() {
      return body;
  }
  
  public String getUsername() {
      return username;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Post(String id, String body, String username) {
    this.id = id;
    this.body = body;
    this.username = username;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Post post = (Post) obj;
      return ObjectsCompat.equals(getId(), post.getId()) &&
              ObjectsCompat.equals(getBody(), post.getBody()) &&
              ObjectsCompat.equals(getUsername(), post.getUsername()) &&
              ObjectsCompat.equals(getCreatedAt(), post.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), post.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getBody())
      .append(getUsername())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Post {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BodyStep builder() {
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
  public static Post justId(String id) {
    return new Post(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      body,
      username);
  }
  public interface BodyStep {
    BuildStep body(String body);
  }
  

  public interface BuildStep {
    Post build();
    BuildStep id(String id);
    BuildStep username(String username);
  }
  

  public static class Builder implements BodyStep, BuildStep {
    private String id;
    private String body;
    private String username;
    @Override
     public Post build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Post(
          id,
          body,
          username);
    }
    
    @Override
     public BuildStep body(String body) {
        Objects.requireNonNull(body);
        this.body = body;
        return this;
    }
    
    @Override
     public BuildStep username(String username) {
        this.username = username;
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
    private CopyOfBuilder(String id, String body, String username) {
      super.id(id);
      super.body(body)
        .username(username);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
  }
  
}
