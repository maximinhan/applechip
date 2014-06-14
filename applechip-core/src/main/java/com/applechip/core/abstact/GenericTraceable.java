package com.applechip.core.abstact;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.applechip.core.constant.ColumnSizeConstant;
import com.applechip.core.entity.User;

@SuppressWarnings("serial")
@MappedSuperclass
// @EntityListeners({ AuditingEntityListener.class })
public abstract class GenericTraceable<PK extends Serializable> extends GenericTimestamp<PK> {

  @Column(name = "created_by", insertable = true, updatable = false, length = ColumnSizeConstant.UUID)
  // @CreatedBy
  private String createdBy;

  @Column(name = "updated_by", insertable = true, updatable = true, length = ColumnSizeConstant.UUID)
  // @LastModifiedBy
  private String updatedBy;

  @PreUpdate
  public void setUpdatedBy() {
    this.updatedBy = getCurrentUserId();
  }

  @PrePersist
  public void setCreatedBy() {
    String currentUserId = getCurrentUserId();
    this.createdBy = currentUserId;
    this.updatedBy = currentUserId;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  private String getCurrentUserId() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    User currentUser = null;
    if (ctx.getAuthentication() != null) {
      Authentication auth = ctx.getAuthentication();
      AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
      boolean signupUser = resolver.isAnonymous(auth);
      if (!signupUser) {
        if (auth.getPrincipal() instanceof UserDetails) {
          currentUser = (User) auth.getPrincipal();
        } else if (auth.getDetails() instanceof UserDetails) {
          currentUser = (User) auth.getDetails();
        }
      }
    }
    return currentUser == null ? null : currentUser.getId();
  }
}
