package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.HasMany;

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
  public static final QueryField CREATE_AT = field("Post", "create_at");
  public static final QueryField CREATE_BY = field("Post", "create_by");
  public static final QueryField USER_ID = field("Post", "user_id");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String body;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime create_at;
  private final @ModelField(targetType="String", isRequired = true) String create_by;
  private final @ModelField(targetType="Int") Integer user_id;
  private final @ModelField(targetType="Comment") @HasMany(associatedWith = "post", type = Comment.class) List<Comment> comments = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getBody() {
      return body;
  }
  
  public Temporal.DateTime getCreateAt() {
      return create_at;
  }
  
  public String getCreateBy() {
      return create_by;
  }
  
  public Integer getUserId() {
      return user_id;
  }
  
  public List<Comment> getComments() {
      return comments;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Post(String id, String body, Temporal.DateTime create_at, String create_by, Integer user_id) {
    this.id = id;
    this.body = body;
    this.create_at = create_at;
    this.create_by = create_by;
    this.user_id = user_id;
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
              ObjectsCompat.equals(getCreateAt(), post.getCreateAt()) &&
              ObjectsCompat.equals(getCreateBy(), post.getCreateBy()) &&
              ObjectsCompat.equals(getUserId(), post.getUserId()) &&
              ObjectsCompat.equals(getCreatedAt(), post.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), post.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getBody())
      .append(getCreateAt())
      .append(getCreateBy())
      .append(getUserId())
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
      .append("create_at=" + String.valueOf(getCreateAt()) + ", ")
      .append("create_by=" + String.valueOf(getCreateBy()) + ", ")
      .append("user_id=" + String.valueOf(getUserId()) + ", ")
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
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      body,
      create_at,
      create_by,
      user_id);
  }
  public interface BodyStep {
    CreateAtStep body(String body);
  }
  

  public interface CreateAtStep {
    CreateByStep createAt(Temporal.DateTime createAt);
  }
  

  public interface CreateByStep {
    BuildStep createBy(String createBy);
  }
  

  public interface BuildStep {
    Post build();
    BuildStep id(String id);
    BuildStep userId(Integer userId);
  }
  

  public static class Builder implements BodyStep, CreateAtStep, CreateByStep, BuildStep {
    private String id;
    private String body;
    private Temporal.DateTime create_at;
    private String create_by;
    private Integer user_id;
    @Override
     public Post build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Post(
          id,
          body,
          create_at,
          create_by,
          user_id);
    }
    
    @Override
     public CreateAtStep body(String body) {
        Objects.requireNonNull(body);
        this.body = body;
        return this;
    }
    
    @Override
     public CreateByStep createAt(Temporal.DateTime createAt) {
        Objects.requireNonNull(createAt);
        this.create_at = createAt;
        return this;
    }
    
    @Override
     public BuildStep createBy(String createBy) {
        Objects.requireNonNull(createBy);
        this.create_by = createBy;
        return this;
    }
    
    @Override
     public BuildStep userId(Integer userId) {
        this.user_id = userId;
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
    private CopyOfBuilder(String id, String body, Temporal.DateTime createAt, String createBy, Integer userId) {
      super.id(id);
      super.body(body)
        .createAt(createAt)
        .createBy(createBy)
        .userId(userId);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder createAt(Temporal.DateTime createAt) {
      return (CopyOfBuilder) super.createAt(createAt);
    }
    
    @Override
     public CopyOfBuilder createBy(String createBy) {
      return (CopyOfBuilder) super.createBy(createBy);
    }
    
    @Override
     public CopyOfBuilder userId(Integer userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
  }
  
}
