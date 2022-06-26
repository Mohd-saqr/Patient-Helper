package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasOne;
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

/** This is an auto generated class representing the Comment type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Comments")
public final class Comment implements Model {
  public static final QueryField ID = field("Comment", "id");
  public static final QueryField BODY = field("Comment", "body");
  public static final QueryField CREATE_BY = field("Comment", "create_by");
  public static final QueryField COMMENT_POST_ID = field("Comment", "commentPostId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String body;
  private final @ModelField(targetType="String", isRequired = true) String create_by;
  private final @ModelField(targetType="Post") @HasOne(associatedWith = "id", type = Post.class) Post post = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String commentPostId;
  public String getId() {
      return id;
  }
  
  public String getBody() {
      return body;
  }
  
  public String getCreateBy() {
      return create_by;
  }
  
  public Post getPost() {
      return post;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getCommentPostId() {
      return commentPostId;
  }
  
  private Comment(String id, String body, String create_by, String commentPostId) {
    this.id = id;
    this.body = body;
    this.create_by = create_by;
    this.commentPostId = commentPostId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Comment comment = (Comment) obj;
      return ObjectsCompat.equals(getId(), comment.getId()) &&
              ObjectsCompat.equals(getBody(), comment.getBody()) &&
              ObjectsCompat.equals(getCreateBy(), comment.getCreateBy()) &&
              ObjectsCompat.equals(getCreatedAt(), comment.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), comment.getUpdatedAt()) &&
              ObjectsCompat.equals(getCommentPostId(), comment.getCommentPostId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getBody())
      .append(getCreateBy())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getCommentPostId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Comment {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("create_by=" + String.valueOf(getCreateBy()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("commentPostId=" + String.valueOf(getCommentPostId()))
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
  public static Comment justId(String id) {
    return new Comment(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      body,
      create_by,
      commentPostId);
  }
  public interface BodyStep {
    CreateByStep body(String body);
  }
  

  public interface CreateByStep {
    BuildStep createBy(String createBy);
  }
  

  public interface BuildStep {
    Comment build();
    BuildStep id(String id);
    BuildStep commentPostId(String commentPostId);
  }
  

  public static class Builder implements BodyStep, CreateByStep, BuildStep {
    private String id;
    private String body;
    private String create_by;
    private String commentPostId;
    @Override
     public Comment build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Comment(
          id,
          body,
          create_by,
          commentPostId);
    }
    
    @Override
     public CreateByStep body(String body) {
        Objects.requireNonNull(body);
        this.body = body;
        return this;
    }
    
    @Override
     public BuildStep createBy(String createBy) {
        Objects.requireNonNull(createBy);
        this.create_by = createBy;
        return this;
    }
    
    @Override
     public BuildStep commentPostId(String commentPostId) {
        this.commentPostId = commentPostId;
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
    private CopyOfBuilder(String id, String body, String createBy, String commentPostId) {
      super.id(id);
      super.body(body)
        .createBy(createBy)
        .commentPostId(commentPostId);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder createBy(String createBy) {
      return (CopyOfBuilder) super.createBy(createBy);
    }
    
    @Override
     public CopyOfBuilder commentPostId(String commentPostId) {
      return (CopyOfBuilder) super.commentPostId(commentPostId);
    }
  }
  
}
