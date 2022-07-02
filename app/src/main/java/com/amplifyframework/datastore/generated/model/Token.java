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

/** This is an auto generated class representing the Token type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Tokens")
public final class Token implements Model {
  public static final QueryField ID = field("Token", "id");
  public static final QueryField TOKEN_ID = field("Token", "tokenId");
  public static final QueryField USER_ID = field("Token", "userId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String tokenId;
  private final @ModelField(targetType="String", isRequired = true) String userId;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTokenId() {
      return tokenId;
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
  
  private Token(String id, String tokenId, String userId) {
    this.id = id;
    this.tokenId = tokenId;
    this.userId = userId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Token token = (Token) obj;
      return ObjectsCompat.equals(getId(), token.getId()) &&
              ObjectsCompat.equals(getTokenId(), token.getTokenId()) &&
              ObjectsCompat.equals(getUserId(), token.getUserId()) &&
              ObjectsCompat.equals(getCreatedAt(), token.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), token.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTokenId())
      .append(getUserId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Token {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("tokenId=" + String.valueOf(getTokenId()) + ", ")
      .append("userId=" + String.valueOf(getUserId()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TokenIdStep builder() {
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
  public static Token justId(String id) {
    return new Token(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      tokenId,
      userId);
  }
  public interface TokenIdStep {
    UserIdStep tokenId(String tokenId);
  }
  

  public interface UserIdStep {
    BuildStep userId(String userId);
  }
  

  public interface BuildStep {
    Token build();
    BuildStep id(String id);
  }
  

  public static class Builder implements TokenIdStep, UserIdStep, BuildStep {
    private String id;
    private String tokenId;
    private String userId;
    @Override
     public Token build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Token(
          id,
          tokenId,
          userId);
    }
    
    @Override
     public UserIdStep tokenId(String tokenId) {
        Objects.requireNonNull(tokenId);
        this.tokenId = tokenId;
        return this;
    }
    
    @Override
     public BuildStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userId = userId;
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
    private CopyOfBuilder(String id, String tokenId, String userId) {
      super.id(id);
      super.tokenId(tokenId)
        .userId(userId);
    }
    
    @Override
     public CopyOfBuilder tokenId(String tokenId) {
      return (CopyOfBuilder) super.tokenId(tokenId);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
  }
  
}
